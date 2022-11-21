package com.ssafy.sweethome.user.model.service;

import com.ssafy.sweethome.user.model.User;
import com.ssafy.sweethome.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findUserIdAndUserPwdAndType(User user) throws Exception {
        return userMapper.selectUser(user);
    }

    @Override
    public boolean join(User user) throws Exception {
        return userMapper.insertUser(user) == 1;
    }

    @Override
    public String findUserId(User user) throws Exception {
        return userMapper.selectUserIdByNameAndEmail(user);
    }

    @Override
    public boolean checkUserIdDuplication(String userId) throws Exception {
        System.out.println("########################");
        System.out.println(userId);
        System.out.println(userMapper.selectUserById(userId));
        User user = userMapper.selectUserById(userId);
        System.out.println(user);
        return user != null;
    }

    @Override
    public boolean checkUserPassword(User user) throws Exception {
        return userMapper.selectUser(user) != null;
    }

    @Override
    public boolean changeUserInfo(User user) throws Exception {
        return userMapper.updateUser(user) == 1;
    }

    @Override
    public boolean changeUserPwd(User user) throws Exception {
        return userMapper.updateUserPassword(user) == 1;
    }

    @Override
    public boolean deleteUser(User user) throws Exception {
        return userMapper.deleteUser(user) == 1;
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", refreshToken);
        userMapper.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String userid) throws Exception {
        return userMapper.getRefreshToken(userid);
    }

    @Override
    public void deleteRefreshToken(Map<String, String> map) throws Exception {
        map.put("token", null);
        userMapper.deleteRefreshToken(map);
    }

    @Override
    public User findUserInformation(String userId) throws Exception {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<User> searchAllUsers() throws Exception {
        return userMapper.selectUserList();
    }

    @Override
    public List<User> searchUsersByCondition(User user) throws Exception {
        return userMapper.selectUserListByCondition(user);
    }
}
