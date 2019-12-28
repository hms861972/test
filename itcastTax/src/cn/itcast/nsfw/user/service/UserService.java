package cn.itcast.nsfw.user.service;

import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface UserService extends BaseService<User> {
    //新增
    void add(User user) throws ServiceException;
    //更新
    void update(User user);
    //根据id删除
    void deleteById(Serializable id);
    //根据id查找
    User findById(Serializable id);
    //查找列表
    List<User> findAll();

    //导出所有用户
    void exportExcel(List<User> userList, ServletOutputStream outputStream) throws IOException;

    //导入用户列表
    void importExcel(File userExcel, String userExcelFileName) throws IOException, ServiceException;

    /**
     * 根据用户id和账号查询用户
     * @param id
     * @param account
     * @return
     */
    List<User> findUserByAccount(String id, String account);

    void addUserAndRole(User user,String... roleId) throws ServiceException;

    void updateUserAndRole(User user, String... roleId);

    List<UserRole> getUserRolesByUserId(String id);

    List<User> findUserByAccountAndPassword(String account, String password);
}
