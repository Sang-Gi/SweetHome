package com.ssafy.sweethome.board.model.service;

import com.ssafy.sweethome.board.model.Board;
import com.ssafy.sweethome.board.model.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Board> getArticleList() throws Exception {
        return boardMapper.selectArticleList();
    }

    @Override
    public boolean writeArticle(Board board) throws Exception {
        return boardMapper.insertArticle(board) == 1;
    }

    @Override
    public boolean modifyArticle(Board board) throws Exception {
        return boardMapper.updateArticle(board) == 1;
    }

    @Override
    public Board getArticle(int articleNo) throws Exception {
        return boardMapper.selectArticle(articleNo);
    }

    @Override
    public boolean deleteArticle(int articleNo) throws Exception {
        return boardMapper.deleteArticle(articleNo) == 1;
    }

    @Override
    public void updateHit(int articleNo) throws SQLException {
        boardMapper.updateHit(articleNo);
    }

    @Override
    public List<Board> getArticleListByCondition(Map<String, String> map) throws Exception {
        return boardMapper.selectArticleListByCondition(map);
    }
}
