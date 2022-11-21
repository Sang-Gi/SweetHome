package com.ssafy.sweethome.notice.model.service;

import com.ssafy.sweethome.notice.model.Notice;
import com.ssafy.sweethome.notice.model.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public List<Notice> getArticleList() throws Exception {
        return noticeMapper.selectArticleList();
    }

    @Override
    public boolean writeArticle(Notice notice) throws Exception {
        return noticeMapper.insertArticle(notice) == 1;
    }

    @Override
    public boolean modifyArticle(Notice notice) throws Exception {
        return noticeMapper.updateArticle(notice) == 1;
    }

    @Override
    public Notice getArticle(int articleNo) throws Exception {
        return noticeMapper.selectArticle(articleNo);
    }

    @Override
    public boolean deleteArticle(int articleNo) throws Exception {
        return noticeMapper.deleteArticle(articleNo) == 1;
    }

    @Override
    public void updateHit(int articleNo) throws SQLException {
        noticeMapper.updateHit(articleNo);
    }

    @Override
    public List<Notice> getArticleListByCondition(Map<String, String> map) throws Exception {
        return noticeMapper.selectArticleListByCondition(map);
    }
}
