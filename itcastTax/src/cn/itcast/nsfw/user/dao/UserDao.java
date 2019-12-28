package cn.itcast.nsfw.user.dao;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    List<User> selectUserByAccount(String id, String account);

    void insertUserRole(UserRole userRole);

    void deleteUserAndRoleByUid(String id);

    List<UserRole> selectUserRoleByUid(String id);

    List<User> selectUserByAccountAndPassword(String account, String password);


}
