package com.yonyou.mde.web.utils;


import java.sql.SQLException;

public class DBUtil {
    public static boolean isMysql(String driver) throws SQLException {
        return driver.toLowerCase().contains("mysql");
    }

    public static boolean isPg(String driver) throws SQLException {
        return driver.toLowerCase().contains("postgresql");
    }

    public static boolean isOracle(String driver) throws SQLException {
        return driver.toLowerCase().contains("oracle");
    }
}
