package test.service.imp;

import test.dao.TestDao;
import test.entity.Person;
import test.service.TestService;

import java.io.Serializable;

public class TestServiceImpl implements TestService {

    private TestDao testDao;
    public void setTestDao(TestDao testDao){
        this.testDao = testDao;
    }
    @Override
    public void say() {
        System.out.println("测试输出!service saying hi...");
    }

    @Override
    public void save(Person person) {
        testDao.save(person);
        int i = 1/0;
    }

    @Override
    public Person findPerson(Serializable id) {
        /*Person person = new Person();
        person.setName("测试3");
        save(person);*/
        return testDao.findPerson(id);
    }
}
