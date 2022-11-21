package com.ssafy.sweethome.favorite_housedeal.controller;

import com.ssafy.sweethome.favorite_housedeal.model.FavoriteHousedeal;
import com.ssafy.sweethome.favorite_housedeal.model.service.FavoriteHousedealService;
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
@RequestMapping("/favorite-housedeal")
@Api("FavoriteHousedeal Controller API")
@Slf4j
public class FavoriteHousedealController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final FavoriteHousedealService favoriteHousedealService;

    @Autowired
    public FavoriteHousedealController(FavoriteHousedealService favoriteHousedealService) {
        this.favoriteHousedealService = favoriteHousedealService;
    }

    @ApiOperation(value = "관심 거래 목록 조회", notes = "관심 거래 목록을 조회한다.", response = Map.class)
    @GetMapping("/list/{user_id}")
    public ResponseEntity<Map<String, Object>> searchAllFavoriteHousedeal(
            @PathVariable("userId") @ApiParam(value = "관심 거래 목록을 조회할 계정", required = true) String userId) {
        log.debug("Search the list of interested transactions");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<FavoriteHousedeal> favoriteHousedealList = favoriteHousedealService.getFavoriteHousedealList(userId);
            if (favoriteHousedealList.isEmpty()) {
                log.debug("No trades of interest listed");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Successfully importing a list of interested transactions");
                resultMap.put("favoriteHousedealList", favoriteHousedealList);
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            log.debug("Failed to get list of interested transactions: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "관심 거래 추가", notes = "관심 거래를 추가한다.", response = String.class)
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addFavoriteHousedeal(
            @RequestBody @ApiParam(value = "관심 거래 번호와 계정", required = true) FavoriteHousedeal favoriteHousedeal) {
        log.debug("Add the transaction of interest");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        System.out.println(favoriteHousedeal);
        try {
            if (favoriteHousedealService.insertFavoriteHousedeal(favoriteHousedeal)) {
                log.debug("Add interest transaction success");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Add interest transaction failed");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Add interest transaction failed: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "관심 거래 제거", notes = "관심 거래를 제거한다.", response = String.class)
    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteFavoriteHousedeal(
            @RequestBody @ApiParam(value = "관심 거래 번호와 계정", required = true) FavoriteHousedeal favoriteHousedeal) {
        log.debug("Remove the transaction of interest");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (favoriteHousedealService.deleteFavoriteHousedeal(favoriteHousedeal)) {
                log.debug("Remove interest transaction success");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to remove interest transaction");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to remove interest transaction: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
