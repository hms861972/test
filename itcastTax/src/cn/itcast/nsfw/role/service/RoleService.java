package cn.itcast.nsfw.role.service;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.role.entity.Role;

import java.io.Serializable;
import java.util.List;

public interface RoleService extends BaseService<Role> {

    //添加方法
    void add(Role role);

    //删除角色
    void delete(Serializable id);

    //修改角色
    void update(Role role);

    //根据id查询
    Role findObjectById(Serializable id);

    //查询全部
    List<Role> findObjects();

}
