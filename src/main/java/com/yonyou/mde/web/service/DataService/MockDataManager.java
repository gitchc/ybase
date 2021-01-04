package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.utils.DBUtil;
import com.yonyou.mde.web.utils.MuiltCross;
import com.yonyou.mde.web.utils.SnowID;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Log4j2
@Component
public class MockDataManager {
    @Resource
    private MemberService service;
    private final int batchsize = 10000;//10w一提交

    //创造表
    public void createTable(DataSource dataSource, String tableName, List<Member> dims) throws Exception {

        Db db = Db.use(dataSource);
        String querytable = "";
        if (DBUtil.isMysql(dataSource)) {
            querytable = "show tables like ?";
        } else if (DBUtil.isPg(dataSource)) {
            querytable = "select * from pg_class where relname = lower(?)";
        } else if (DBUtil.isOracle(dataSource)) {
            querytable = "select * from user_tables where table_name =upper(?)";
        }
        List<Entity> tablecount = db.query(querytable, tableName);
        if (tablecount.size() > 0) {//判断表是否存在,不存在则创建表
            log.warn(tableName + "表已经存在,Drop掉");
            db.execute("drop table " + tableName);
        }
        String createSql = getCreateTabseSql(tableName, dims);
        db.execute(createSql);
    }


    //创造数据
    private void MockDataFinal(String tableName, List<String> dims, Map<String, List<String>> memberMaps, int mocksize, boolean isRandom) {
        service.executeSql("truncate " + tableName);
        List<String[]> members = new ArrayList<>();
        if (mocksize > 10000000) {
            int smalsize = (int) Math.pow(mocksize, 1D / (dims.size() - 2));
            for (String dim : dims) {
                List<String> value = memberMaps.get(dim);
                int last = value.size() > smalsize ? smalsize : value.size();

                value = value.subList(0, last);

                String[] strings = value.toArray(new String[value.size()]);
                members.add(strings);
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
                sqls.append("insert into ").append(tableName).append(" (id,").append(StringUtils.join(dims, ",")).append(",value) values (");
            } else {
                sqls.append(",(");
            }
            sqls.append(SnowID.nextID());//id
            sqls.append(",");
            sqls.append("'").append(StringUtils.join(elems, "','")).append("'");
            sqls.append(",");
            if (isRandom) {
                sqls.append(RandomUtil.randomDouble(0, 10000));
            } else {
                sqls.append(1);
            }
            sqls.append(")");
            if (mocksize > 0 && max >= mocksize) {
                service.executeSql(sqls.toString());
                log.info("{}已提交:{}提交数据", tableName, max);
                sqls = new StringBuilder();
                return;
            }
            if (i == batchsize) {
                service.executeSql(sqls.toString());
                log.info("{}已提交:{}提交数据", tableName, max);
                sqls = new StringBuilder();
                i = 0;
            }
        }
        if (sqls.length() > 0) {
            service.executeSql(sqls.toString());
            log.info("{}已提交:{}提交数据", tableName, max);
        }
    }

    public void MockData(String tableName, List<String> dims, Map<String, List<String>> memebrs, int mocksize) {
        MockDataFinal(tableName, dims, memebrs, mocksize, false);
    }

    public void MockRandomData(String tableName, List<String> dims, Map<String, List<String>> memebrs, int mocksize) {
        MockDataFinal(tableName, dims, memebrs, mocksize, true);
    }

    private String getCreateTabseSql(String tableName, List<Member> dims) {
        StringBuilder createsql = new StringBuilder("create table " + tableName + " (");
        createsql.append("id bigint primary key not null,\n");

        for (Member dim : dims) {
            createsql.append(dim.getCode() + " varchar(100),\n");
        }
        createsql.append("value decimal(19,6),txtvalue varchar(1000)\n");
        createsql.append(")");
        return createsql.toString();
    }
}
