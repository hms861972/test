package cn.itcast.nsfw.user.dao.impl;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import org.hibernate.Query;

import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public List<User> selectUserByAccount(String id, String account) {
        String hql = "FROM User AS u WHERE u.account = ? AND u.id!=?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0,account);
        query.setParameter(1,id+"");
        return query.list();
    }

    @Override
    public void insertUserRole(UserRole userRole) {
        getHibernateTemplate().save(userRole);
    }

    @Override
    public void deleteUserAndRoleByUid(String id) {
        //delete from userRole where userId=id
        Query query = getSession().createQuery("delete from UserRole where id.userId=?");
        query.setParameter(0,id);
        query.executeUpdate();
    }

    @Override
    public List<UserRole> selectUserRoleByUid(String id) {
        Query query = getSession().createQuery("FROM UserRole WHERE id.userId=?");
        query.setParameter(0, id);
        return query.list();
    }

    @Override
    public List<User> selectUserByAccountAndPassword(String account, String password) {
        Query query = getSession().createQuery("FROM User WHERE account=? AND password=?");
        query.setParameter(0, account);
        query.setParameter(1, password);
        return query.list();
    }
}
