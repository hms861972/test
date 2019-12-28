package test.action;

import com.opensymphony.xwork2.ActionSupport;
import test.service.TestService;

public class TestAction extends ActionSupport {
    private TestService testService;
    public void setTestService(TestService testService){
        this.testService = testService;
    }
    @Override
    public String execute() throws Exception {
        testService.say();
        return SUCCESS;
    }
}
