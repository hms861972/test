package test.dao.impl;

import test.dao.TestDao;
import test.entity.Person;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao {

    @Override
    public void save(Person person) {
        getHibernateTemplate().save(person);
    }

    @Override
    public Person findPerson(Serializable id) {
        return getHibernateTemplate().get(Person.class,id);
    }

    @Override
    public List<Object[]> testSelect(String sql) {

        return null;
    }
}
