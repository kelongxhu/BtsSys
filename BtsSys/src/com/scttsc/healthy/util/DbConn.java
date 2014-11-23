package com.scttsc.healthy.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: Administrator
 * Date: 13-9-4
 * Time: 下午2:12
 */
public class DbConn {
    static Logger logger = Logger.getLogger(DbConn.class);
    private Connection conn;
    private Statement stmt;
    private String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=dbname";
    private String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    private String uid = "sa";
    private String pwd = "password";


    private int type;//数据连接类型


    public DbConn(String url, String driver, String uid, String pwd) {
        this.url = url;
        this.driver = driver;
        this.uid = uid;
        this.pwd = pwd;
    }

    public DbConn(String driver, String url, String uid, String pwd, int type) {
        this.driver = driver;
        this.url = url;
        this.uid = uid;
        this.pwd = pwd;
        this.type = type;
    }

    public Connection getConnection() {
        try {
            Class.forName(driver);
            logger.info("driver is ok");
            if (conn == null || conn.isClosed())
                conn = DriverManager.getConnection(url, uid, pwd);
                logger.info("Connect to DBMS is OK!"+conn);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("获取数据库连接异常," + ex.getMessage());
        }
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getType() {
        return type;
    }


    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUid() {
        return uid;
    }

    public String getPwd() {
        return pwd;
    }
}
