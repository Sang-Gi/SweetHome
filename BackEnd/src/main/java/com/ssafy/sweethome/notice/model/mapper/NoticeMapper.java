package com.ssafy.sweethome.notice.model.mapper;

import com.ssafy.sweethome.notice.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {
    List<Notice> selectArticleList() throws SQLException;

    int insertArticle(Notice notice) throws SQLException;

    int updateArticle(Notice notice) throws SQLException;

    Notice selectArticle(int articleNo) throws SQLException;

    int deleteArticle(int articleNo) throws SQLException;

    void updateHit(int articleNo) throws SQLException;

    List<Notice> selectArticleListByCondition(Map<String, String> map) throws SQLException;
}
