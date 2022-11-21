package com.ssafy.sweethome.board.model.service;

import com.ssafy.sweethome.board.model.Board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Board> getArticleList() throws Exception;

    boolean writeArticle(Board board) throws Exception;

    boolean modifyArticle(Board board) throws Exception;

    Board getArticle(int articleNo) throws Exception;

    boolean deleteArticle(int articleNo) throws Exception;

    void updateHit(int articleNo) throws SQLException;

    List<Board> getArticleListByCondition(Map<String, String> map) throws Exception;
}
