package com.ssafy.sweethome.comment.model.service;

import com.ssafy.sweethome.comment.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentList(int articleNo) throws Exception;

    boolean writeComment(Comment comment) throws Exception;

    boolean modifyComment(Comment comment) throws Exception;

    boolean deleteComment(int commentId) throws Exception;
}
