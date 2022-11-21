package com.ssafy.sweethome.user.model.mapper;

import com.ssafy.sweethome.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int insertUser(User user) throws SQLException;

    User selectUser(User user) throws SQLException;

    int updateUser(User user) throws SQLException;

    int updateUserPassword(User user) throws SQLException;

    int deleteUser(User user) throws SQLException;

    User selectUserById(String userId) throws SQLException;

    String selectUserIdByNameAndEmail(User user) throws SQLException;

    List<User> selectUserList() throws SQLException;

    List<User> selectUserListByCondition(User user) throws SQLException;

    void saveRefreshToken(Map<String, String> map) throws SQLException;

    Object getRefreshToken(String userid) throws SQLException;

    void deleteRefreshToken(Map<String, String> map) throws SQLException;
}
