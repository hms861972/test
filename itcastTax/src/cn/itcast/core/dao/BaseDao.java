package cn.itcast.core.dao;

import cn.itcast.core.page.PageResult;
import cn.itcast.core.utils.QueryHelper;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

    //新增
    void insert(T entity);
    //更新
    void update(T entity);
    //根据id删除
    void deleteByPrimary(Serializable primary);
    //根据id查找
    T selectByPrimary(Serializable primary);
    //查找列表
    List<T> selectAll();

    //条件查询
    List<T> selectAll(String hql,List<Object> parameters);

    PageResult getPageRsult(QueryHelper queryHelper, int pageNo, int pageSize);
}
