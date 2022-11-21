package com.ssafy.sweethome.user.model.service;

import com.ssafy.sweethome.user.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findUserIdAndUserPwdAndType(User user) throws Exception;

    boolean join(User user) throws Exception;

    String findUserId(User user) throws Exception;

//    boolean findUserPwd(Map<String, String> map) throws Exception;

    boolean checkUserIdDuplication(String userId) throws Exception;

    boolean checkUserPassword(User user) throws Exception;

    boolean changeUserInfo(User user) throws Exception;

    boolean changeUserPwd(User user) throws Exception;

    boolean deleteUser(User user) throws Exception;

    void saveRefreshToken(String userId, String refreshToken) throws Exception;

    Object getRefreshToken(String userid) throws Exception;

    void deleteRefreshToken(Map<String, String> map) throws Exception;

    User findUserInformation(String userId) throws Exception;

    List<User> searchAllUsers() throws Exception;

    List<User> searchUsersByCondition(User user) throws Exception;
}
