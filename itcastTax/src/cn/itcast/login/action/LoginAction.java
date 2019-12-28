package cn.itcast.login.action;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;

public class LoginAction extends ActionSupport {
    @Resource
    private UserService userService;

    private User user;

    private String loginResult;

    //跳转到登录页面
    public String toLoginUI(){
        return "loginUI";
    }

    //登录
    public String login(){
        if (user!=null){
            if (StringUtils.isNotEmpty(user.getAccount())&&StringUtils.isNotEmpty(user.getPassword())){
                //根据用户账号或密码查询用户
                List<User> userList = userService.findUserByAccountAndPassword(user.getAccount(),user.getPassword());
                if (userList!=null&&userList.size()>0){
                    //2.1、登录成功
                    User user = userList.get(0);
                    //2.1.1、将用户信息保存到session中
                    ServletActionContext.getRequest().getSession().setAttribute(Constant.USER,user);
                    //2.1.2、***
                    //2.1.3、将用户登录记录到日志文件
                    Log log = LogFactory.getLog(getClass());
                    log.info("用户名称为:"+user.getPassword()+"的用户登录了系统.");
                    //2.1.4、重定向跳转到首页
                    return "home";
                }else {
                    loginResult = "账号或密码不正确!";
                }
            }else {
                loginResult = "账号或密码不能为空!";
            }
        }else {
            loginResult = "请输入账号和密码!";
        }
        return toLoginUI();
    }

    //退出，注销
    public String logout(){
        //清除session中保存的用户信息
        ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
        return toLoginUI();
    }

    //跳转到没有权限提示页面
    public String toNoPermissionUI(){
        return "noPermissionUI";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }
}
