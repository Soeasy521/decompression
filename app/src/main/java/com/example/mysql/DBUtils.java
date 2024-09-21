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
    private static String url = "jdbc:mysql://172.23.105.110:3306/decompression";
    private static String user = "root"; // 用户名
    private static String password = "lxy262626"; // 密码

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







    public static List<Article> getArticles(Context context) {
        List<Article> articles = new ArrayList<>();
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + TABLE_ARTICLES)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    int articleId = rs.getInt("articleId");
                    int userId = rs.getInt("userId");
                    String title = rs.getString("articleTitle");
                    String content = rs.getString("articleContent");
                    String date = rs.getString("articleDate");
                    int likes = rs.getInt("articleLike");
                    int comments = rs.getInt("articleComment");

                    Log.d(TAG, "ArticleId: " + articleId + ", Title: " + title + ", Date: " + date);
                    articles.add(new Article(articleId, userId, title, content, date, likes, comments));
                    count++;
                }
                Log.d(TAG, "共获取文章数: " + count);
            }
        } catch (SQLException e) {
            Log.e(TAG, "获取文章列表出错", e);
        }
        return articles;
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
                     "INSERT INTO " + TABLE_ARTICLES + " (userId, articleTitle, articleContent, articleDate, articleComment, articleLike) VALUES (?, ?, ?, ?, 0, 0)")) {

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

    public static User getUser(Context context, int userId) {
        User user = null;
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + TABLE_USERS + " WHERE userId = ?")) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("userId"));
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("passWord"));
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "获取用户信息出错", e);
        }
        return user;
    }

    public static String getContent(Context context, int articleId) {
        String content = null;
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("SELECT articleContent FROM " + TABLE_ARTICLES + " WHERE articleId = ?")) {
            pstmt.setInt(1, articleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    content = rs.getString("articleContent");
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "获取文章内容出错", e);
        }
        return content;
    }

    public static String getDate(Context context, int articleId) {
        String date = null;
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement("SELECT articleDate FROM " + TABLE_ARTICLES + " WHERE articleId = ?")) {
            pstmt.setInt(1, articleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    date = rs.getString("articleDate");
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "获取文章日期出错", e);
        }
        return date;
    }

    private static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static class Article {
        private int articleId;
        private int userId;
        private String articleTitle;
        private String articleContent;
        private String articleDate;
        private int articleLike;
        private int articleComment;

        public Article(int articleId, int userId, String articleTitle, String articleContent, String articleDate, int articleLike, int articleComment) {
            this.articleId = articleId;
            this.userId = userId;
            this.articleTitle = articleTitle;
            this.articleContent = articleContent;
            this.articleDate = articleDate;
            this.articleLike = articleLike;
            this.articleComment = articleComment;
        }

        public int getArticleId() {
            return articleId;
        }

        public int getUserId() {
            return userId;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public String getArticleContent() {
            return articleContent;
        }

        public String getArticleDate() {
            return articleDate;
        }

        public int getArticleLike() {
            return articleLike;
        }

        public int getArticleComment() {
            return articleComment;
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

    public static class User {
        private int userId;
        private String userName;
        private String password;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}