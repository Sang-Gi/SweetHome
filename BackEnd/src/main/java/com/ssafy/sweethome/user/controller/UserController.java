package com.ssafy.sweethome.user.controller;

import com.ssafy.sweethome.user.model.User;
import com.ssafy.sweethome.user.model.service.JwtServiceImpl;
import com.ssafy.sweethome.user.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Api("User Controller API")
@Slf4j
public class UserController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final JwtServiceImpl jwtService;
    private final UserService userService;

    @Autowired
    public UserController(JwtServiceImpl jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @ApiOperation(value = "로그인", notes = "Access-token, 로그인 결과 메세지를 반환한다.", response = Map.class)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 정보(아이디 및 비밀번호).", required = true) User user) {
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        log.debug("Login");
        try {
            User loginUser = userService.findUserIdAndUserPwdAndType(user);
            if (loginUser != null
                    && loginUser.getUserId().equals(user.getUserId())
                    && BCrypt.checkpw(user.getUserPwd(), loginUser.getUserPwd())) {
                String accessToken = jwtService.createAccessToken("userId", loginUser.getUserId());
                String refreshToken = jwtService.createRefreshToken("userId", loginUser.getUserId());
                userService.saveRefreshToken(user.getUserId(), refreshToken);
                log.debug("login accessToken information : {}", accessToken);
                log.debug("login refreshToken information : {}", refreshToken);
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("login failed: Non-existent ID or wrong password");
                resultMap.put("message", FAIL);
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            log.debug("login failed: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = String.class)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody @ApiParam(value = "현재 로그인된 아이디", required = true) Map<String, String> map) {
        String message;
        HttpStatus status;
        log.debug("Logout");
        try {
            userService.deleteRefreshToken(map);
            log.debug("refreshToken removed - User ID: {}", map.get("userId"));
            message = SUCCESS;
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.debug("logout Failed: {}", e.getMessage());
            message = e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입을 수행한다.", response = String.class)
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @ApiParam(value = "가입할 회원의 정보", required = true) User user) {
        String message;
        HttpStatus status;
        log.debug("Join");
        try {
            user.setUserPwd(BCrypt.hashpw(user.getUserPwd(), BCrypt.gensalt()));
            if (userService.join(user)) {
                log.debug("join User ID: {}", user.getUserId());
                message = SUCCESS;
                status = HttpStatus.OK;
            } else {
                log.debug("rejected join request User ID: {}", user.getUserId());
                message = FAIL;
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Failed to join: {}", e.getMessage());
            message = e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디를 찾는다.", response = Map.class)
    @PostMapping("/find/id")
    public ResponseEntity<Map<String, String>> findUserId(@RequestBody @ApiParam(value = "찾을 아이디의 가입 정보", required = true) User user) {
        Map<String, String> resultMap = new HashMap<>();
        HttpStatus status;
        log.debug("Find User ID");
        try {
            String userId = userService.findUserId(user);
            System.out.println(userId);
            if (userId != null) {
                log.debug("found user ID: {}", userId);
                resultMap.put("message", SUCCESS);
                resultMap.put("userId", userId);
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to find user - Not Joined User");
                resultMap.put("message", FAIL);
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            log.debug("Failed to find user ID: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

//    @ApiOperation(value = "비밀번호 찾기", notes = "임시 비밀번호를 생성하여 메일로 전송한다.", response = String.class)
//    @PostMapping("/find/password")
//    public ResponseEntity<String> findUserPassword(
//            @RequestBody @ApiParam(value = "비밀번호를 찾을 아이디와 가입 정보", required = true)
//            Map<String, String> map) {
//        String message;
//        HttpStatus status;
//        try {
//            if (userService.findUserPwd(map)) {
//                /* 메일 보내는 로직 처리 필요 */
//                log.debug("Temporary password sent complete - ID: {}, Email: {}", map.get("userId"), map.get("userEmail"));
//                message = SUCCESS;
//                status = HttpStatus.ACCEPTED;
//            } else {
//                log.debug("Failed to send temporary password - Inconsistent input information");
//                message = FAIL;
//                status = HttpStatus.NO_CONTENT;
//            }
//        } catch (Exception e) {
//            log.debug("Failed to send temporary password: {}", e.getMessage());
//            message = FAIL;;
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<>(message, status);
//    }

    @ApiOperation(value = "중복 계정 확인", notes = "이미 가입된 아이디인지 확인한다", response = String.class)
    @GetMapping("/check/{user_id}")
    public ResponseEntity<String> checkUserIdDuplication(
            @PathVariable("user_id") @ApiParam(value = "중복 여부를 확인할 아이디", required = true) String userId) {
        String message;
        HttpStatus status;
        log.debug("ID duplicate check");
        try {
            if (!userService.checkUserIdDuplication(userId)) {
                // 중복 X
                log.debug("ID available: {}", userId);
                message = SUCCESS;
                status = HttpStatus.OK;
            } else {
                System.out.println("dupl");
                // 중복 O
                log.debug("ID not available: {}", userId);
                message = FAIL;
                status = HttpStatus.CONFLICT;
            }
        } catch (Exception e) {
            log.debug("Duplicate check failed: {}", e.getMessage());
            message = FAIL;
            status = HttpStatus.INSUFFICIENT_STORAGE;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "비밀번호 확인", notes = "로그인된 아이디의 비밀번호를 확인한다", response = String.class)
    @PostMapping("/check/password")
    public ResponseEntity<String> checkUcheckUserPassword(@RequestBody @ApiParam(value = "확인할 아이디와 비밀번호", required = true) User user) {
        String message;
        HttpStatus status;
        log.debug("Check Password");
        try {
            User loginUser = userService.findUserIdAndUserPwdAndType(user);
            if (loginUser != null
                    && loginUser.getUserId().equals(user.getUserId())
                    && BCrypt.checkpw(user.getUserPwd(), loginUser.getUserPwd())) {
                log.debug("Password verification successful: match");
                message = SUCCESS;
                status = HttpStatus.OK;
            } else {
                log.debug("Password verification successful: mismatch");
                message = FAIL;
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("Password verification failed: {}", e.getMessage());
            message = FAIL;
            status = HttpStatus.INSUFFICIENT_STORAGE;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정한다.", response = String.class)
    @PutMapping("/change")
    public ResponseEntity<String> modifyUserInformation(
            @RequestBody @ApiParam(value = "변경할 회원 정보", required = true) User user) {
        String message;
        HttpStatus status;
        log.debug("Modify User Information");
        try {
            if (userService.changeUserInfo(user)) {
                log.debug("User information modified");
                message = SUCCESS;
                status = HttpStatus.OK;
            } else {
                log.debug("Failed to modify user information");
                message = FAIL;
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("Failed to modify user information: {}", e.getMessage());
            message = FAIL;
            status = HttpStatus.INSUFFICIENT_STORAGE;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호를 수정한다.", response = String.class)
    @PutMapping("/change/password")
    public ResponseEntity<String> modifyUserPassword(
            @RequestBody @ApiParam(value = "아이디, 현재 비밀번호, 새로운 비밀번호", required = true) Map<String, String> map) {
        String message;
        HttpStatus status;
        log.debug("Modify password");
        try {
            String userId = map.get("userId");
            String oldUserPwd = map.get("userPwd");
            String newUserPwd = map.get("newUserPwd");
            User user = userService.findUserIdAndUserPwdAndType(
                    User.builder().userId(userId).build()
            );
            System.out.println("############################");
            System.out.println(userId);
            System.out.println(oldUserPwd);
            System.out.println(newUserPwd);
            System.out.println(user);
            if (user != null && BCrypt.checkpw(oldUserPwd, user.getUserPwd())) {
                user.setUserPwd(BCrypt.hashpw(newUserPwd, BCrypt.gensalt()));
                if (userService.changeUserPwd(user)) {
                    log.debug("Password modified");
                    message = SUCCESS;
                    status = HttpStatus.OK;
                } else {
                    log.debug("Password modification failed");
                    message = FAIL;
                    status = HttpStatus.UNAUTHORIZED;
                }
            } else {
                log.debug("Password modification failed");
                message = FAIL;
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("Password modification failed: {}", e.getMessage());
            message = FAIL;
            status = HttpStatus.INSUFFICIENT_STORAGE;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 조회한다.", response = Map.class)
    @PostMapping("/detail")
    public ResponseEntity<Map<String, Object>> inquiryUserInformation(
            @RequestBody @ApiParam(value = "조회할 회원의 아이디", required = true) Map<String, String> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        log.debug("User information inquiry");
        try {
            String userId = map.get("userId");
            User user = userService.findUserInformation(userId);
            if (user != null) {
                log.debug("User information search success: {}", user.getUserId());
                resultMap.put("user", user);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.OK;
            } else {
                log.debug("User information inquiry failed: {}", userId);
                resultMap.put("message", FAIL);
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("User information inquiry failed: {}", e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INSUFFICIENT_STORAGE;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴한다.", response = String.class)
    @DeleteMapping("/delete")
    public ResponseEntity<String> withdrawUser(
            @RequestBody @ApiParam(value = "탈퇴할 회원의 아이디와 비밀번호", required = true) User user) {
        String message;
        HttpStatus status;
        log.debug("User withdrawal");
        try {
            User loginUser = userService.findUserIdAndUserPwdAndType(user);
            if (loginUser != null
                    && loginUser.getUserId().equals(user.getUserId())
                    && BCrypt.checkpw(user.getUserPwd(), loginUser.getUserPwd())
                    && userService.deleteUser(user)) {
                log.debug("User withdrawal success: {}", user.getUserId());
                status = HttpStatus.OK;
                message = SUCCESS;
            } else {
                log.debug("User withdrawal failed: invalid input");
                message = FAIL;
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("User withdrawal failed: {}", e.getMessage());
            message = e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(message, status);
    }

    @ApiOperation(value = "회원 인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @PostMapping("/authentication")
    public ResponseEntity<Map<String, Object>> getUserInformation(
            @RequestBody @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        log.debug("Authentication: {}", userId);
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            log.info("valid token");
            try {
                User user = userService.findUserInformation(userId);
                resultMap.put("userInformation", user);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                log.debug("회원 정보 조회 실패: {}", e.getMessage());
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            log.debug("Could not find user information.");
            resultMap.put("message", FAIL);
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "Access Token 재발급", notes = "만료된 Access Token을 재발급받는다.", response = Map.class)
    @PostMapping("/reissue")
    public ResponseEntity<Map<String, Object>> refreshToken(
            @RequestBody @ApiParam(value = "인증할 회원의 아이디.", required = true) User user,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("refresh-token");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        log.debug("token : {}, User Information: {}", token, user);
        if (jwtService.checkToken(token)) {
            if (token.equals(userService.getRefreshToken(user.getUserId()))) {
                String accessToken = jwtService.createAccessToken("userid", user.getUserId());
                log.debug("token : {}", accessToken);
                log.debug("Access token reissue successful");
                resultMap.put("access-token", accessToken);
                resultMap.put("message", SUCCESS);
            }
        } else {
            log.debug("Refresh Token not available");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
