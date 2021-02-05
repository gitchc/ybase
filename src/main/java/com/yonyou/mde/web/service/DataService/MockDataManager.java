package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.util.RandomUtil;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.utils.DBUtil;
import com.yonyou.mde.web.utils.MuiltCross;
import com.yonyou.mde.web.utils.SnowID;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Log4j2
@Component
public class MockDataManager {
    @Value("${spring.datasource.driver-class-name:0}")
    private String driver;
    @Resource
    private MemberService memberService;

    public void dropTable(String tableName) throws Exception {
        String queryTableSql = "";
        if (DBUtil.isMysql(driver)) {
            queryTableSql = "show tables like '" + tableName + "'";
        } else if (DBUtil.isPg(driver)) {
            queryTableSql = "select relname from pg_class where relname = lower('" + tableName + "')";
        } else if (DBUtil.isOracle(driver)) {
            queryTableSql = "select table_name  from user_tables where table_name =upper('" + tableName + "')";
        }
        List<String> tablecount = memberService.query(queryTableSql);
        if (tablecount.size() > 0) {//判断表是否存在,不存在则创建表
            log.warn(tableName + "表已经存在,Drop掉");
            memberService.execute("drop table " + tableName);
        }
    }

    //创造表
    public void buildFactTable(String tableName, List<Dimension> dims) throws Exception {
        dropTable(tableName);//如果表存在删除表
        createTable(tableName, dims);//新建表
    }


    //创造数据
    public void MockDataFinal(String tableName, List<String> dims, Map<String, List<String>> memberMaps, int mocksize, boolean isRandom) throws SQLException {
        memberService.execute("truncate " + tableName);
        int batchSize = 10000;//10w一提交
        List<String[]> members = new ArrayList<>();
        if (mocksize > 10000000) {
            int smalsize = (int) Math.pow(mocksize, 1D / (dims.size() - 2));
            for (String dim : dims) {
                List<String> value = memberMaps.get(dim);
                int last = value.size() > smalsize ? smalsize : value.size();
                value = value.subList(0, last);
                String[] slices = value.toArray(new String[value.size()]);
                members.add(slices);
            }
        } else {
            for (String dim : dims) {
                List<String> value = memberMaps.get(dim);
                String[] strings = value.toArray(new String[value.size()]);
                members.add(strings);
            }
        }
        MuiltCross cross = new MuiltCross(members);
        int i = 0;
        int max = 0;
        StringBuilder sqls = new StringBuilder();
        while (cross.hasNext()) {
            i++;
            max++;
            String[] elems = (String[]) cross.next();
            if (sqls.length() == 0) {
                sqls.append("insert into ")
                        .append(tableName)
                        .append(" (id,")
                        .append(StringUtils.join(dims, ","))
                        .append(",value) values (");
            } else {
                sqls.append(",(");
            }
            sqls.append(SnowID.nextID())
                    .append(",")
                    .append("'")
                    .append(StringUtils.join(elems, "','"))
                    .append("'")
                    .append(",");
            if (isRandom) {
                sqls.append(RandomUtil.randomDouble(0, 10000));
            } else {
                sqls.append(1);
            }
            sqls.append(")");
            if (mocksize > 0 && max >= mocksize) {
                memberService.execute(sqls.toString());
                log.info("{}已提交:{}提交数据", tableName, max);
                return;
            }
            if (i == batchSize) {
                memberService.execute(sqls.toString());
                log.info("{}已提交:{}提交数据", tableName, max);
                sqls = new StringBuilder();
                i = 0;
            }
        }
        if (sqls.length() > 0) {
            memberService.execute(sqls.toString());
            log.info("{}已提交:{}提交数据", tableName, max);
        }
    }


    private void createTable(String tableName, List<Dimension> dims) throws SQLException {
        StringBuilder createsql = new StringBuilder("create table " + tableName + " (");
        createsql.append("id bigint primary key not null,\n");
        for (Dimension dim : dims) {
            createsql.append(dim.getCode() + " varchar(100),\n");
        }
        createsql.append("value decimal(19,6),txtvalue varchar(1000),isdeleted int default 0\n");
        createsql.append(")");
        memberService.execute(createsql.toString());
    }
}
