package cn.itcast.nsfw.role.service.impl;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private RoleDao roleDao;

    @Resource
    public void setRoleDao(RoleDao roleDao) {
        setBaseDao(roleDao);
        this.roleDao = roleDao;
    }

    @Override
    public void add(Role role) {
        roleDao.insert(role);
    }

    @Override
    public void delete(Serializable id) {
        roleDao.deleteByPrimary(id);
    }

    @Override
    public void update(Role role) {
        //修改将角色权限表删除再插入
        roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
        roleDao.update(role);
    }

    @Override
    public Role findObjectById(Serializable id) {
        return roleDao.selectByPrimary(id);
    }

    @Override
    public List<Role> findObjects() {
        return roleDao.selectAll();
    }
}
