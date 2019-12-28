package cn.itcast.core.service.impl;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.utils.QueryHelper;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T> implements cn.itcast.core.service.BaseService<T> {

    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public void save(T entity) {
        baseDao.insert(entity);
    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Override
    public void delete(Serializable id) {
        baseDao.deleteByPrimary(id);
    }

    @Override
    public T findObjectById(Serializable id) {
        return baseDao.selectByPrimary(id);
    }

    @Override
    public List<T> findObjects() {
        return baseDao.selectAll();
    }


    @Override
    public List<T> findObjects(String hql, List<Object> parameters){
        return baseDao.selectAll(hql,parameters);
    }

    @Override
    public List<T> findObjects(QueryHelper queryHelper){

        return baseDao.selectAll(queryHelper.getQueryListHql(),queryHelper.getParameters());
    }

    @Override
    public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize){
        return baseDao.getPageRsult(queryHelper,pageNo,pageSize);
    }


}
