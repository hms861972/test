package cn.itcast.nsfw.user.action;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.utils.QueryHelper;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

public class UserAction extends BaseAction {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    private List<User> userList;
    private User user;
    private String[] userRoleIds;

    private File headImg;
    private String headImgContentType;
    private String headImgFileName;

    private File userExcel;
    private String userExcelContentType;
    private String userExcelFileName;



    //列表页面
    public String listUI(){
        QueryHelper queryHelper = new QueryHelper(User.class,"u");
        try {
            if (user !=null){
                if (StringUtils.isNotBlank(user.getName())){
                    queryHelper.addCondition("u.name like ?","%"+user.getName()+"%");
                }
            }
            pageResult = userService.getPageResult(queryHelper,getPageNo(),getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "listUI";
    }

    //挑转到新增页面
    public String addUI(){
        //加载角色列表
        ActionContext.getContext().getContextMap().put("roleList",roleService.findObjects());
        return "addUI";
    }


    //保存新增
    public String add(){
       if (user!=null){
           //处理头像
           if(headImg!=null){
               //1.保存头像到upload/user
               //获取保存路径的绝对地址
               String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
               //获取文件后缀
               String fileName = UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
               //复制文件
               try {
                   FileUtils.copyFile(headImg,new File(filePath,fileName));
               } catch (IOException e) {
                   e.printStackTrace();
               }
               //2.设置头像路径
               user.setHeadImg("user/"+fileName);
           }
           try {
               userService.addUserAndRole(user,userRoleIds);
           } catch (ServiceException e) {
               e.printStackTrace();
               return "addError";
           }
       }
        return "list";
    }

    //跳转到编辑页面
    public String editUI(){
        //加载角色列表
        ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
        if (user!=null&&user.getId()!=null){
            user = userService.findById(user.getId());
        }
        //处理角色回显
        List<UserRole> list = userService.getUserRolesByUserId(user.getId());
        if(list != null && list.size() > 0){
            userRoleIds = new String[list.size()];
            for(int i = 0; i < list.size(); i++){
                userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
            }
        }
        return "editUI";
    }

    //保存编辑
    public String edit(){
        if (user!=null&&user.getId()!=null){
            //处理头像
            if(headImg!=null){
                //1.保存头像到upload/user
                //获取保存路径的绝对地址
                String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
                //获取文件后缀
                String fileName = UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
                //复制文件
                try {
                    FileUtils.copyFile(headImg,new File(filePath,fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //2.设置头像路径
                user.setHeadImg("user/"+fileName);
            }
            userService.updateUserAndRole(user,userRoleIds);
        }
        return "list";
    }

    //删除
    public String delete(){
        if (user!=null&&user.getId()!=null){
            userService.deleteById(user.getId());
        }
        return "list";
    }

    //批量删除
    public String deleteSelected(){
        if(selectedRow != null){
            for(String id: selectedRow){
                userService.deleteById(id);
            }
        }
        return "list";
    }

    //导出所有用户
    public void exportExcel(){
        try{
            //1.查找所有用户
            List<User> userList = userService.findObjects();
            //2、导出
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/x-execl");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户列表.xls".getBytes(), "ISO-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            userService.exportExcel(userList, outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Excel文件导入数据库
    public String importExcel(){
        try{
            //1.获取文件
            if(userExcel!=null){
                //是否是Excel
                if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
                    userService.importExcel(userExcel,userExcelFileName);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "list";
    }

    //校验账号唯一性
    public void verifyAccount(){
        String result = "false";
        try{
            //1.获取账号
            if(user!=null&& StringUtils.isNotBlank(user.getAccount())){
                //2.根据账号到数据库查询
                List<User> list = userService.findUserByAccount(user.getId(),user.getAccount());
                if (list==null||list.size()==0){
                    //说明该账号不存在
                    result = "true";
                }
            }
            //输出
            HttpServletResponse response = ServletActionContext.getResponse();
            //设置响应格式
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public void setSelectedRow(String[] selectedRow) {
        this.selectedRow = selectedRow;
    }
    public String[] getSelectedRow() {
        return selectedRow;
    }

    public User getUser(){
        return this.user;
    }
    public void setUser(User user){
        this.user=user;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public File getHeadImg() {
        return headImg;
    }

    public void setHeadImg(File headImg) {
        this.headImg = headImg;
    }

    public String getHeadImgContentType() {
        return headImgContentType;
    }

    public void setHeadImgContentType(String headImgContentType) {
        this.headImgContentType = headImgContentType;
    }

    public String getHeadImgFileName() {
        return headImgFileName;
    }

    public void setHeadImgFileName(String headImgFileName) {
        this.headImgFileName = headImgFileName;
    }

    public File getUserExcel() {
        return userExcel;
    }

    public void setUserExcel(File userExcel) {
        this.userExcel = userExcel;
    }

    public String getUserExcelContentType() {
        return userExcelContentType;
    }

    public void setUserExcelContentType(String userExcelContentType) {
        this.userExcelContentType = userExcelContentType;
    }

    public String getUserExcelFileName() {
        return userExcelFileName;
    }

    public void setUserExcelFileName(String userExcelFileName) {
        this.userExcelFileName = userExcelFileName;
    }

    public String[] getUserRoleIds() {
        return userRoleIds;
    }

    public void setUserRoleIds(String[] userRoleIds) {
        this.userRoleIds = userRoleIds;
    }
}
