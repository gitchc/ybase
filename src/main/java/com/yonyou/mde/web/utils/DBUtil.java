package com.yonyou.mde.web.utils;


import javax.sql.DataSource;
import java.sql.SQLException;

public class DBUtil {
    public static boolean isMysql(DataSource dataSourceInfo) throws SQLException {
        return "mysql".equalsIgnoreCase(dataSourceInfo.getConnection().getMetaData().getDatabaseProductName());
    }

    public static boolean isPg(DataSource dataSourceInfo) throws SQLException {
        return "postgresql".equalsIgnoreCase(dataSourceInfo.getConnection()
                .getMetaData().getDatabaseProductName());
    }

    public static boolean isOracle(DataSource dataSourceInfo) throws SQLException {
        return "oracle".equalsIgnoreCase(dataSourceInfo.getConnection()
                .getMetaData().getDatabaseProductName());
    }
}
