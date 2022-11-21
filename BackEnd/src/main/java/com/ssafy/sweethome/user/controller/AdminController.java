package com.ssafy.sweethome.user.controller;

import com.ssafy.sweethome.user.model.User;
import com.ssafy.sweethome.user.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Api("Admin Controller API")
@Slf4j
public class AdminController {

    private static final String SUCCESS = "success";
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원 목록 조회", notes = "회원 목록을 조회한다.", response = Map.class)
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> searchAllUsers() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        log.debug("Search All Users");
        try {
            List<User> userList = userService.searchAllUsers();
            log.debug("Return user list");
            resultMap.put("userList", userList);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            log.error("Failed to return user list:", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "회원 목록 조회(조건 검색)", notes = "조건에 맞는 회원 목록을 조회한다", response = Map.class)
    @GetMapping("/user/{attribute}")
    public ResponseEntity<Map<String, Object>> searchUsersByCondition(@PathVariable Map<String, String> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        log.debug("Search Users By Condition");
        try {
            User user = new User();
            if (map.containsKey("userId")) user.setUserId(map.get("userId"));
            if (map.containsKey("userName")) user.setUserName(map.get("userName"));
            if (map.containsKey("userAddress")) user.setUserAddress(map.get("userAddress"));
            if (map.containsKey("userEmail")) user.setUserEmail(map.get("userEmail"));
            System.out.println(user);
            List<User> userList = userService.searchUsersByCondition(user);
            log.debug("Return user list");
            resultMap.put("userList", userList);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            log.error("Failed to return user list:", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
