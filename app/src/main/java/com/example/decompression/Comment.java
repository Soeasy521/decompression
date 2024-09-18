package com.example.decompression;


public class Comment {
    private int userId;
    private int commentId;
    private int articleId;
    private String commentContent;
    private String commentDate;

    public Comment(int userId,int commentId,int articleId, String commentContent, String commentDate) {
        this.userId = userId;
        this.commentId = commentId;
        this.articleId = articleId;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    public Comment(int userId, String commentContent, String commentDate) {
    }

    public int getUserId() {
        return userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentDate() {
        return commentDate;
    }
}