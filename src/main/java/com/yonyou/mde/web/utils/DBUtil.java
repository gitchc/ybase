package com.yonyou.mde.web.utils;


import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DBUtil {
    public static boolean isMysql(DataSource dataSourceInfo) throws SQLException {
        return ((DruidDataSource) dataSourceInfo).getDriverClassName().contains("mysql");
    }

    public static boolean isPg(DataSource dataSourceInfo) throws SQLException {
        return ((DruidDataSource) dataSourceInfo).getDriverClassName().contains("postgresql");
    }

    public static boolean isOracle(DataSource dataSourceInfo) throws SQLException {
        return ((DruidDataSource) dataSourceInfo).getDriverClassName().contains("oracle");
    }
}
