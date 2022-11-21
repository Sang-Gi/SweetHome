package com.ssafy.sweethome.board.model.mapper;

import com.ssafy.sweethome.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    List<Board> selectArticleList() throws SQLException;

    int insertArticle(Board board) throws SQLException;

    int updateArticle(Board board) throws SQLException;

    Board selectArticle(int articleNo) throws SQLException;

    int deleteArticle(int articleNo) throws SQLException;

    void updateHit(int articleNo) throws SQLException;

    List<Board> selectArticleListByCondition(Map<String, String> map) throws SQLException;
}
