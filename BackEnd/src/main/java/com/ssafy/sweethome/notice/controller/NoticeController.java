package com.ssafy.sweethome.notice.controller;

import com.ssafy.sweethome.notice.model.Notice;
import com.ssafy.sweethome.notice.model.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
@Api("Notice Controller API")
@Slf4j
public class NoticeController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @ApiOperation(value = "공지사항 목록 조회", notes = "공지사항 목록을 조회한다.", response = Map.class)
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> searchAllArticles() {
        log.debug("Get article(notice) list");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Notice> noticeList = noticeService.getArticleList();
            if (noticeList.isEmpty()) {
                log.debug("The article(notice) does not exist.");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Acquisition of article(notice) list success");
                resultMap.put("noticeList", noticeList);
                status = HttpStatus.OK;
            }
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.debug("Failed to fetch articles(notice): {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "공지사항 작성", notes = "공지사항을 작성한다.", response = String.class)
    @PostMapping("/write")
    public ResponseEntity<Map<String, String>> writeArticle(
            @RequestBody @ApiParam(value = "작성할 공지사항의 제목과 내용", required = true) Notice notice) {
        log.debug("Write article(notice)");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (noticeService.writeArticle(notice)) {
                log.debug("Article(notice) creation success");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to write article(notice)");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to write article(notice): {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "공지사항 글 수정", notes = "공지사항 글을 수정한다.", response = String.class)
    @PutMapping("/modify")
    public ResponseEntity<Map<String, String>> modifyArticle(
            @RequestBody @ApiParam(value = "수정할 공지사항 글의 번호와 제목, 내용", required = true) Notice notice) {
        log.debug("Modify article(notice)");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (noticeService.modifyArticle(notice)) {
                log.debug("Article(notice) edit successful");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to edit article(notice)");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to edit article(notice): {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "공지사항 글 조회", notes = "공지사항 글을 조회한다.", response = Map.class)
    @GetMapping("/search/{article_no}")
    public ResponseEntity<Map<String, Object>> findArticle(
            @PathVariable("articleNo") @ApiParam(value = "조회할 공지사항 글 번호", required = true) String articleNo) {
        log.debug("Get article(notice)");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            Notice notice = noticeService.getArticle(Integer.parseInt(articleNo));
            if (notice == null) {
                log.debug("The article(notice) does not exist.");
                status = HttpStatus.NO_CONTENT;
            } else {
                noticeService.updateHit(Integer.parseInt(articleNo));
                log.debug("Acquisition of article(notice) success");
                resultMap.put("notice", notice);
                status = HttpStatus.OK;
            }
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.debug("Failed to fetch article(notice): {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "공지사항 글 삭제", notes = "공지사항 글을 삭제한다.", response = String.class)
    @DeleteMapping("/search/{article_no}")
    public ResponseEntity<Map<String, String>> deleteArticle(
            @PathVariable("articleNo") @ApiParam(value = "제거할 공지사항 글 번호", required = true) String articleNo) {
        log.debug("Delete article(notice)");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (noticeService.deleteArticle(Integer.parseInt(articleNo))) {
                log.debug("Successfully deleted article(notice)");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to delete article(notice)");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to delete article(notice): {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "공지사항 글 목록 조건 검색", notes = "공지사항 글 목록을 조건에 따라 조회한다.", response = Map.class)
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchArticlesByCondition(
            @RequestBody @ApiParam(value = "공지사항 글을 검색할 조건", required = true) Map<String, String> map) {
        log.debug("Get article(notice) list by condition");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Notice> noticeList = noticeService.getArticleListByCondition(map);
            if (noticeList.isEmpty()) {
                log.debug("The article(notice) does not exist.");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Acquisition of article(notice) list success");
                resultMap.put("noticeList", noticeList);
                status = HttpStatus.OK;
            }
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.debug("Failed to fetch articles(notice): {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
