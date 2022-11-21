package com.ssafy.sweethome.comment.controller;

import com.ssafy.sweethome.comment.model.Comment;
import com.ssafy.sweethome.comment.model.service.CommentService;
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
@RequestMapping("/comment")
@Api("Comment Controller API")
@Slf4j
public class CommentController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "댓글 목록 조회", notes = "댓글 목록을 조회한다.", response = Map.class)
    @GetMapping("/list/{article_no}")
    public ResponseEntity<Map<String, Object>> searchAllComments(
            @PathVariable("articleNo") @ApiParam(value = "댓글을 조회할 게시글 번호", required = true) String articleNo) {
        log.debug("Search the comment list");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Comment> commentList = commentService.getCommentList(Integer.parseInt(articleNo));
            if (commentList.isEmpty()) {
                log.debug("Fetching list of comments failed: No comments");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Retrieving the list of comments succeeded");
                resultMap.put("commentList", commentList);
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            log.debug("Failed to get comment list: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.", response = String.class)
    @PostMapping()
    public ResponseEntity<Map<String, String>> writeComment(
            @RequestBody @ApiParam(value = "작성할 댓글 정보", required = true) Comment comment) {
        log.debug("Write a comment");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (commentService.writeComment(comment)) {
                log.debug("Comment success");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to write comment");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to write comment: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.", response = String.class)
    @PutMapping
    public ResponseEntity<Map<String, String>> modifyComment(
            @RequestBody @ApiParam(value = "수정할 댓글 정보", required = true) Comment comment) {
        log.debug("Edit your comment");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (commentService.modifyComment(comment)) {
                log.debug("Comment edit successful");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Comment edit failed");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Comment edit failed: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.", response = String.class)
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Map<String, String>> deleteComment(
            @PathVariable("commentId") @ApiParam(value = "삭제할 댓글의 고유 번호", required = true) String commentId) {
        log.debug("Delete comments");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (commentService.deleteComment(Integer.parseInt(commentId))) {
                log.debug("Comment deleted successfully");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Comment deletion failed");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Comment deletion failed: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
