package com.yonyou.mde.web.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Log4j2
@Component
public class MockDataUtils {
    @Resource
    private MemberService service;
    private static final int batchsize = 10000;//10w一提交

    //创造表
    public static void createTable(DataSource dataSource, String tableName, List<Member> dims) throws Exception {
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
        if (tablecount.size() == 0) {//判断表是否存在,不存在则创建表
            String createSql = getCreateTabseSql(tableName, dims);
            db.execute(createSql);
        } else {
            log.warn(tableName + "表已经存在,Drop掉");
            db.execute("drop table " + tableName);
        }
    }

    //创造数据
    private void MockDataFinal(String tableName, List<String> dims, Map<String, List<String>> memberMaps, int mocksize, boolean isRandom) {
        service.execute("truncate " + tableName);
        if (dims.size() <= 2) {
            throw new ScriptException("使用造数函数,维度数量不能少于3个!");
        }
        String firstCode = dims.get(0);
        List<String> firstList = memberMaps.get(firstCode);
        String SecondeName = dims.get(1);
        List<String> secnode = memberMaps.get(SecondeName);
        String thirdName = dims.get(2);
        List<String> thirds = memberMaps.get(thirdName);
        int max = 0;
        for (String th : thirds) {
            memberMaps.put(thirdName, Arrays.asList(th));
            for (String f : firstList) {
                memberMaps.put(firstCode, Arrays.asList(f));
                for (String se : secnode) {
                    memberMaps.put(SecondeName, Arrays.asList(se));
                    List<String[]> members = new ArrayList<>();
                    for (Map.Entry<String, List<String>> entry : memberMaps.entrySet()) {
                        List<String> value = entry.getValue();
                        String[] strings = value.toArray(new String[value.size()]);
                        members.add(strings);
                    }
                    MuiltCross cross = new MuiltCross(members);
                    int i = 0;
                    int minmax = 0;
                    StringBuilder sqls = new StringBuilder();
                    while (cross.hasNext()) {
                        i++;
                        max++;
                        minmax++;
                        String[] elems = (String[]) cross.next();
                        if (sqls.length() == 0) {
                            sqls.append("insert into " + tableName + " (id," + StringUtils.join(dims, ",") + ",value) values (");
                        } else {
                            sqls.append(",(");
                        }
                        sqls.append(SnowID.nextID());//id
                        sqls.append(",");
                        sqls.append("'" + StringUtils.join(elems, "','") + "'");
                        sqls.append(",");
                        if (isRandom) {
                            sqls.append(RandomUtil.randomDouble(0, 10000));
                        } else {
                            sqls.append(1);
                        }
                        sqls.append(")");
                        if (mocksize > 0 && max >= mocksize) {
                            service.execute(sqls.toString());
                            log.info("已提交:{}提交数据", max);
                            sqls.setLength(0);
                            return;
                        }
                        if (i == batchsize) {
                            service.execute(sqls.toString());
                            log.info("已提交:{}提交数据", max);
                            sqls.setLength(0);
                            i = 0;
                        }
                        if (minmax == 1000000) {
                            log.info("切换笛卡尔积组合,{},{},{}", f, se, th);
                            cross = null;
                            break;
                        }
                    }
                    if (sqls.length() > 0) {
                        service.execute(sqls.toString());
                        log.info("已提交:{}提交数据", max);
                    }
                }
            }
        }
    }

    public void MockData(String tableName, List<String> dims, Map<String, List<String>> memebrs, int mocksize) {
        MockDataFinal(tableName, dims, memebrs, mocksize, false);
    }

    public void MockRandomData(String tableName, List<String> dims, Map<String, List<String>> memebrs, int mocksize) {
        MockDataFinal(tableName, dims, memebrs, mocksize, true);
    }

    private static String getCreateTabseSql(String tableName, List<Member> dims) {
        StringBuilder createsql = new StringBuilder("create table " + tableName + " (");
        createsql.append("id bigint primary key not null,\n");

        for (Member dim : dims) {
            createsql.append(dim.getCode() + " varchar(100),\n");
        }
        createsql.append("value decimal(19,6)\n");
        createsql.append(")");
        return createsql.toString();
    }
}
