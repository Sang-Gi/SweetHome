package com.ssafy.sweethome.comment.model.service;

import com.ssafy.sweethome.comment.model.Comment;
import com.ssafy.sweethome.comment.model.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> getCommentList(int articleNo) throws Exception {
        return commentMapper.selectCommentList(articleNo);
    }

    @Override
    public boolean writeComment(Comment comment) throws Exception {
        return commentMapper.insertComment(comment) == 1;
    }

    @Override
    public boolean modifyComment(Comment comment) throws Exception {
        return commentMapper.updateComment(comment) == 1;
    }

    @Override
    public boolean deleteComment(int commentId) throws Exception {
        return commentMapper.deleteComment(commentId) == 1;
    }
}
