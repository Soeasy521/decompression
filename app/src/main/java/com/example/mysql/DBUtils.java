package com.example.mysql;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

//数据库工具类：连接数据库用、获取数据库数据用
public class DBUtils {
    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动
    private static String url = "jdbc:mysql://192.168.1.77:3306/depression";
    private static String user = "deUser";// 用户名
    private static String password = "depression321";// 密码

    private static Connection getConn() {
        Connection connection = null;

        try {
            Class.forName(driver);// 动态加载类
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            Log.i("DBUtils", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean IsExist(String name) {
        Connection connection = getConn();
        try {
            String sql = "select userId from users where userName = '"  + name +"'";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if (rs != null) {
                        connection.close();
                        ps.close();
                        return true;
                    } else {
                        Log.i("DBUtils", "结果为空");
                        return false;
                    }
                } else {
                    Log.i("DBUtils", "sql");
                    return false;
                }
            } else {
                Log.i("DBUtils", "连接失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DBUtils", "异常：" + e.getMessage());
            return false;
        }
    }

    public static boolean InsertUserInfo(String name, String pwd) {
        Connection connection = getConn();
        try {
            String sql = "insert into users(userName,passWord) values('" + name + "','" + pwd + "')";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    if (rs > 0) {
                        connection.close();
                        ps.close();
                        return true;
                    } else {
                        Log.i("DBUtils", "结果为空");
                        return false;
                    }
                } else {
                    Log.i("DBUtils", "sql");
                    return false;
                }
            } else {
                Log.i("DBUtils", "连接失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DBUtils", "异常：" + e.getMessage());
            return false;
        }
    }

}