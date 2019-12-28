package cn.itcast.home.action;

import cn.itcast.core.utils.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeAction extends ActionSupport {
    @Resource
    private UserService userService;
    @Resource
    private ComplainService complainService;

    private Map<String, Object> return_map;
    private Complain comp;

    //跳转到首页
    @Override
    public String execute() throws Exception {
        return "home";
    }

    //跳转到我要投诉页面
    public String complainAddUI(){

        return "complainAddUI";
    }

    public String getUserJson2(){
        //1、获取部门
        String dept = ServletActionContext.getRequest().getParameter("dept");
        if(StringUtils.isNotBlank(dept)){
            QueryHelper queryHelper = new QueryHelper(User.class, "u");
            queryHelper.addCondition("u.dept like ?", "%" +dept);
            //2、根据部门查询用户列表
            return_map = new HashMap<String, Object>();
            return_map.put("msg", "success");
            return_map.put("userList", userService.findObjects(queryHelper));
        }
        return SUCCESS;
    }

    public void complainAdd(){
        try {
            if(comp != null){
                //设置默写投诉状态为 待受理
                comp.setState(Complain.COMPLAIN_STATE_UNDONE);
                comp.setCompTime(new Timestamp(new Date().getTime()));
                complainService.save(comp);

                //输出投诉结果
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType("text/html");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("success".getBytes("utf-8"));
                outputStream.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Map<String, Object> getReturn_map() {
        return return_map;
    }

    public void setReturn_map(Map<String, Object> return_map) {
        this.return_map = return_map;
    }

    public Complain getComp() {
        return comp;
    }

    public void setComp(Complain comp) {
        this.comp = comp;
    }
}
