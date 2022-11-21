package com.ssafy.sweethome.board.controller;

import com.ssafy.sweethome.board.model.Board;
import com.ssafy.sweethome.board.model.service.BoardService;
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
@RequestMapping("/board")
@Api("Board Controller API")
@Slf4j
public class BoardController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 조회한다.", response = Map.class)
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> searchAllArticles() {
        log.debug("Get article list");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Board> boardList = boardService.getArticleList();
            if (boardList.isEmpty()) {
                log.debug("The article does not exist.");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Acquisition of article list success");
                resultMap.put("boardList", boardList);
                status = HttpStatus.OK;
            }
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.debug("Failed to fetch articles: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.", response = String.class)
    @PostMapping("/write")
    public ResponseEntity<Map<String, String>> writeArticle(
            @RequestBody @ApiParam(value = "작성할 게시글의 제목과 내용", required = true) Board board) {
        log.debug("Write article");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (boardService.writeArticle(board)) {
                log.debug("Article creation success");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to write article");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to write article: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.", response = String.class)
    @PutMapping("/modify")
    public ResponseEntity<Map<String, String>> modifyArticle(
            @RequestBody @ApiParam(value = "수정할 게시글의 번호와 제목, 내용", required = true) Board board) {
        log.debug("Modify article");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (boardService.modifyArticle(board)) {
                log.debug("Article edit successful");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to edit article");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to edit article: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "게시글 조회", notes = "게시글을 조회한다.", response = Map.class)
    @GetMapping("/search/{article_no}")
    public ResponseEntity<Map<String, Object>> findArticle(
            @PathVariable("articleNo") @ApiParam(value = "조회할 게시글 번호", required = true) String articleNo) {
        log.debug("Get article");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            Board board = boardService.getArticle(Integer.parseInt(articleNo));
            if (board == null) {
                log.debug("The article does not exist.");
                status = HttpStatus.NO_CONTENT;
            } else {
                boardService.updateHit(Integer.parseInt(articleNo));
                log.debug("Acquisition of article success");
                resultMap.put("board", board);
                status = HttpStatus.OK;
            }
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.debug("Failed to fetch article: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.", response = String.class)
    @DeleteMapping("/search/{article_no}")
    public ResponseEntity<Map<String, String>> deleteArticle(
            @PathVariable("articleNo") @ApiParam(value = "제거할 게시글 번호", required = true) String articleNo) {
        log.debug("Delete article");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (boardService.deleteArticle(Integer.parseInt(articleNo))) {
                log.debug("Successfully deleted article");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to delete article");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to delete article: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "게시글 목록 조건 검색", notes = "게시글 목록을 조건에 따라 조회한다.", response = Map.class)
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchArticlesByCondition(
            @RequestBody @ApiParam(value = "게시글을 검색할 조건", required = true) Map<String, String> map) {
        log.debug("Get article list by condition");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Board> boardList = boardService.getArticleListByCondition(map);
            if (boardList.isEmpty()) {
                log.debug("The article does not exist.");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Acquisition of article list success");
                resultMap.put("boardList", boardList);
                status = HttpStatus.OK;
            }
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            log.debug("Failed to fetch articles: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
