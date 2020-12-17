package com.yonyou.mde.web.utils;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.google.common.base.Stopwatch;
import com.yonyou.mde.web.model.Member;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Log4j2
public class MockDataUtils {
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
    public static void MockData(DataSource dataSource, String tableName, Map<String, String[]> memebrs, int mocksize) {
        try {
            Db db = Db.use(dataSource);
            db.execute("truncate " + tableName);
            List<String> dims = new ArrayList<>();
            List<String[]> members = new ArrayList<>();
            for (Map.Entry<String, String[]> entry : memebrs.entrySet()) {
                dims.add(entry.getKey());
                members.add(entry.getValue());
            }
            MuiltCross cross = new MuiltCross(members);
            int i = 0;
            int max = 0;
            Stopwatch stopwatch = Stopwatch.createStarted();
            StringBuilder sqls = new StringBuilder();
            while (cross.hasNext()) {
                i++;
                max++;
                String[] elems = (String[]) cross.next();
                if (sqls.length() == 0) {
                    sqls.append("insert into " + tableName + " (" + StringUtils.join(elems, ",") + ") values (");
                } else {
                    sqls.append(",(");
                }
                sqls.append(SnowID.nextID());//id
                sqls.append(",");
                sqls.append("'" + StringUtils.join(elems, "','") + "'");
                sqls.append(",");
                sqls.append(1);
                sqls.append(")");
                if (mocksize > 0 && max >= mocksize) {
                    db.execute(sqls.toString());
                    log.info("已提交:{}提交数据", max);
                    sqls.setLength(0);
                    break;
                }
                if (i == batchsize) {
                    db.execute(sqls.toString());
                    log.info("已提交:{}提交数据", max);
                    sqls.setLength(0);
                    i = 0;
                }
            }
            if (sqls.length() > 0) {
                db.execute(sqls.toString());
                log.info("已提交:{}提交数据", max);
            }
            log.info("耗时:{}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getCreateTabseSql(String tableName,  List<Member> dims) {
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
