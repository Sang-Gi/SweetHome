package com.ssafy.sweethome.favorite_region.controller;

import com.ssafy.sweethome.favorite_region.model.FavoriteRegion;
import com.ssafy.sweethome.favorite_region.model.service.FavoriteRegionService;
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
@RequestMapping("/favorite-region")
@Api("FavoriteRegion Controller API")
@Slf4j
public class FavoriteRegionController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final FavoriteRegionService favoriteRegionService;

    @Autowired
    public FavoriteRegionController(FavoriteRegionService favoriteRegionService) {
        this.favoriteRegionService = favoriteRegionService;
    }

    @ApiOperation(value = "관심 지역 코드 목록 조회", notes = "관심 지역 코드 목록을 조회한다.", response = Map.class)
    @GetMapping("/list/{user_id}")
    public ResponseEntity<Map<String, Object>> searchAllFavoriteRegion(
            @PathVariable("user_id") @ApiParam(value = "관심 지역 코드 목록을 조회할 계정", required = true) String userId) {
        log.debug("Search the list of interested transactions");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            System.out.println(userId);
            List<FavoriteRegion> favoriteRegionCodeList = favoriteRegionService.getFavoriteRegionCodeList(userId);
            System.out.println(favoriteRegionCodeList);
            if (favoriteRegionCodeList.isEmpty()) {
                log.debug("No region of interest");
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Successfully importing the list of points of interest");
                resultMap.put("favoriteRegionCodeList", favoriteRegionCodeList);
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            log.debug("Failed to get list of points of interest: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "관심 지역 추가", notes = "관심 지역을 추가한다.", response = String.class)
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addFavoriteRegion(
            @RequestBody @ApiParam(value = "관심 지역 코드와 계정", required = true) FavoriteRegion favoriteRegion) {
        log.debug("Add the transaction of interest");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (favoriteRegionService.insertFavoriteRegion(favoriteRegion)) {
                log.debug("Successful adding region of interest");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Add region of interest failed");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Add region of interest failed: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "관심 지역 제거", notes = "관심 지역을 제거한다.", response = String.class)
    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteFavoriteRegion(
            @RequestBody @ApiParam(value = "관심 지역 코드와 계정", required = true) FavoriteRegion favoriteRegion) {
        log.debug("Remove the transaction of interest");
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (favoriteRegionService.deleteFavoriteRegion(favoriteRegion)) {
                log.debug("Successful region of interest removal");
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("Failure to remove region of interest");
                resultMap.put("message", FAIL);
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failure to remove region of interest: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "지역 코드로 관심 구역 주소 가져오기", notes = "지역 코드로 관심 구역 주소를 가져온다.", response = Map.class)
    @GetMapping("/search/{dongCode}")
    public ResponseEntity<Map<String, Object>> searchAddressByDongCode(
            @PathVariable @ApiParam(value = "지역 코드", required = true) String dongCode) {
        log.debug("Get Areas of Interest by Area Code");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<String> addressList = favoriteRegionService.searchAddressByDongCode(dongCode);
            if (addressList.isEmpty()) {
                log.debug("Get Areas of Interest by Area Code failure: Empty");
                resultMap.put("message", FAIL);
                status = HttpStatus.NO_CONTENT;
            } else {
                log.debug("Successfully importing areas of interest by area code");
                resultMap.put("addressList", addressList);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            log.debug("Get Areas of Interest by Area Code failure: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
