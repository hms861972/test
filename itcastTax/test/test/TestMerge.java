package test;

import org.junit.Test;
import test.action.TestAction;
import test.entity.Person;
import test.service.TestService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMerge {

    @Test
    public void testSpring(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ac.getBean("testService", TestService.class).say();
    }

    @Test
    public void testAction() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        String ret = ac.getBean("testAction", TestAction.class).execute();
        System.out.println(ret);
    }

    @Test
    public void testHibernate(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Person person = new Person();
        person.setName("测试3");
        session.save(person);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testServiceAndDao(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestService testService = (TestService) ac.getBean("testService");
        Person p = testService.findPerson("8a8080826de9d73d016de9d73ea40000");
        System.out.println(p.getId()+p.getName());
    }

    @Test
    public void testRollback(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestService testService = (TestService) ac.getBean("testService");
        Person person = new Person();
        person.setName("测试2");
        testService.save(person);
    }

    public void testResult(){

    }
}
