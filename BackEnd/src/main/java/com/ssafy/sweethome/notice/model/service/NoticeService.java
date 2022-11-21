package com.ssafy.sweethome.notice.model.service;

import com.ssafy.sweethome.notice.model.Notice;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface NoticeService {
    List<Notice> getArticleList() throws Exception;

    boolean writeArticle(Notice notice) throws Exception;

    boolean modifyArticle(Notice notice) throws Exception;

    Notice getArticle(int articleNo) throws Exception;

    boolean deleteArticle(int articleNo) throws Exception;

    void updateHit(int articleNo) throws SQLException;

    List<Notice> getArticleListByCondition(Map<String, String> map) throws Exception;
}
