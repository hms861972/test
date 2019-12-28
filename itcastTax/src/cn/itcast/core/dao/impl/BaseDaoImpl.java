package cn.itcast.core.dao.impl;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.utils.QueryHelper;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    private Class<?> clazz;

    public BaseDaoImpl(){
        ParameterizedType parameterizedType
                = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        clazz = (Class<?>) parameterizedType
                .getActualTypeArguments()[0];
    }

    @Override
    public void insert(T entity) {
        System.out.println("保存");
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void deleteByPrimary(Serializable primary) {
        getHibernateTemplate().delete(selectByPrimary(primary));
    }

    @Override
    public T selectByPrimary(Serializable primary) {
        return (T) getHibernateTemplate().get(clazz,primary);
    }

    @Override
    public List<T> selectAll() {
        Query query = getSession().createQuery("from "+clazz.getSimpleName());
        return query.list();
    }

    @Override
    public List<T> selectAll(String hql, List<Object> parameters) {
        Query query = getSession().createQuery(hql);
        System.out.println(hql);
        if (parameters !=null){
            for (Object parameter :parameters){
                int i = 0;
                query.setParameter(i++,parameter);
            }
        }
        return query.list();
    }

    @Override
    public PageResult getPageRsult(QueryHelper queryHelper, int pageNo, int pageSize){
        Query query = getSession().createQuery(queryHelper.getQueryListHql());
        System.out.println(queryHelper.getQueryListHql());
        List<Object> parameters = queryHelper.getParameters();
        if (parameters !=null){
            int i = 0;
            for (Object parameter :parameters){
                query.setParameter(i++,parameter);
            }
        }
        if (pageNo<1){
            pageNo = 1;
        }
        query.setFirstResult((pageNo-1)*pageSize);
        query.setMaxResults(pageSize);
        List<T> itmes = query.list();
        //获取总记录数
        Query queryCount = getSession().createQuery(queryHelper.getQueryCountHql());
        if(parameters != null){
            for(int i = 0; i < parameters.size(); i++){
                queryCount.setParameter(i, parameters.get(i));
            }
        }
        long totalCount = (Long)queryCount.uniqueResult();
        return new PageResult(totalCount,pageNo,pageSize,itmes);
    }

}
