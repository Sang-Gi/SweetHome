package com.ssafy.sweethome.comment.model.mapper;

import com.ssafy.sweethome.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentList(int articleNo) throws SQLException;

    int insertComment(Comment comment) throws SQLException;

    int updateComment(Comment comment) throws SQLException;

    int deleteComment(int commentId) throws SQLException;
}
