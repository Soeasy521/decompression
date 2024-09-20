package com.example.mysql;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DBUtils {
    private static final String TAG = "DBUtils";
    private static String driver = "com.mysql.jdbc.Driver"; // MySQL驱动
    private static String url = "jdbc:mysql://172.23.135.221:3306/decompression";
    private static String user = "root"; // 用户名
    private static String password = "liujiao1012"; // 密码

    private static final String TABLE_ARTICLES = "article";
    private static final String TABLE_COMMENTS = "comment";
    private static final String TABLE_USERS = "users";

    private static volatile DBUtils instance;

    private DBUtils() {}

    public static synchronized DBUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (DBUtils.class) {
                if (instance == null) {
                    instance = new DBUtils();
                }
            }
        }
        return instance;
    }

    private static Connection getConn() {
        Connection connection = null;

        try {
            Class.forName(driver); // 动态加载类
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            Log.i(TAG, Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean IsExist(String name) {
        Connection connection = getConn();
        try {
            String sql = "SELECT userId FROM " + TABLE_USERS + " WHERE userName = ?";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    connection.close();
                    ps.close();
                    return true;
                } else {
                    Log.i(TAG, "结果为空");
                    return false;
                }
            } else {
                Log.i(TAG, "连接失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常：" + e.getMessage());
            return false;
        }
    }

    public static boolean InsertUserInfo(String name, String pwd) {
        Connection connection = getConn();
        try {
            String sql = "INSERT INTO " + TABLE_USERS + " (userName, passWord) VALUES (?, ?)";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, pwd);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    connection.close();
                    ps.close();
                    return true;
                } else {
                    Log.i(TAG, "结果为空");
                    return false;
                }
            } else {
                Log.i(TAG, "连接失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常：" + e.getMessage());
            return false;
        }
    }

    public static class Comment {
        private int userId;
        private String commentContent;
        private String commentDate;

        public Comment(int userId, String commentContent, String commentDate) {
            this.userId = userId;
            this.commentContent = commentContent;
            this.commentDate = commentDate;
        }

        public int getUserId() {
            return userId;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public String getCommentDate() {
            return commentDate;
        }
    }

    public static boolean likeArticle(Context context, int articleId) {
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE " + TABLE_ARTICLES + " SET articleLike = articleLike + 1 WHERE articleId = ?")) {
            pstmt.setInt(1, articleId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Log.e(TAG, "点赞文章出错", e);
            return false;
        }
    }

    public static boolean addComment(Context context, int userId, int articleId, String content) {
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + TABLE_COMMENTS + " (userId, articleId, commentContent, commentDate) VALUES (?, ?, ?, NOW())")) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, articleId);
            pstmt.setString(3, content);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Log.e(TAG, "添加评论出错", e);
            return false;
        }
    }

    public static List<Comment> getComments(Context context, int articleId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("SELECT userId, commentContent, commentDate FROM " + TABLE_COMMENTS + " WHERE articleId = ?")) {
            pstmt.setInt(1, articleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    int userId = rs.getInt("userId");
                    String content = rs.getString("commentContent");
                    String date = rs.getString("commentDate");

                    Log.d(TAG, "UserId: " + userId + ", Content: " + content + ", Date: " + date);
                    comments.add(new Comment(userId, content, date));
                    count++;
                }
                Log.d(TAG, "共获取评论数: " + count);
            }
        } catch (SQLException e) {
            Log.e(TAG, "获取评论出错", e);
        }
        return comments;
    }

    public static boolean publishArticle(Context context, int userId, String title, String content) {
        String date = getCurrentDate(); // 获取当前日期
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO " + TABLE_ARTICLES + " (userId, articleTitle, articleContent, articleDate) VALUES (?, ?, ?, ?)")) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setString(4, date);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Log.e(TAG, "发布文章出错", e);
            return false;
        }
    }

    private static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}