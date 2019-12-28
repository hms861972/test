package test.dao;

import test.entity.Person;

import java.io.Serializable;
import java.util.List;

public interface TestDao {
    //保存人员
    public void save(Person person);

    //根据id查询人员
    public Person findPerson(Serializable id);

    public List<Object[]> testSelect(String sql);
}
