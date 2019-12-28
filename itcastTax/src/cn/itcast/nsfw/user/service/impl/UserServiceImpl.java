package cn.itcast.nsfw.user.service.impl;

import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.utils.ExcelUtil;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
import cn.itcast.nsfw.user.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService  {


    private UserDao userDao;
    @Resource
    public void setUserDao(UserDao userDao) {
        setBaseDao(userDao);
        this.userDao = userDao;
    }

    @Override
    public void add(User user) throws ServiceException {
        //判断账号是否存在
        List<User> list = findUserByAccount(user.getId(),user.getAccount());
        if (list==null||list.size()==0){
            throw new ServiceException("账号:'"+user.getAccount()+"',已被使用添加失败!");
        }
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteById(Serializable id) {
        userDao.deleteByPrimary(id);
    }

    @Override
    public User findById(Serializable id) {
        return userDao.selectByPrimary(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }

    @Override
    public void exportExcel(List<User> userList, ServletOutputStream outputStream) throws IOException {
        ExcelUtil.exportUserExcel(userList, outputStream);
    }

    @Override
    public void importExcel(File userExcel, String userExcelFileName) throws IOException, ServiceException {
        FileInputStream fileInputStream = new FileInputStream(userExcel);
        boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
        //1、读取工作簿
        Workbook workbook = is03Excel?new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
        //2、读取工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 3、读取行
        if (sheet.getPhysicalNumberOfRows()>2){
            Row row;
            Cell cell;
            User user ;
            for(int k = 2;k<sheet.getPhysicalNumberOfRows();k++ ){
                user = new User();
                // 4、读取单元格
                row = sheet.getRow(k);
                //用户名
                cell = row.getCell(0);
                user.setName(cell.getStringCellValue());
                //账号
                cell = row.getCell(1);
                user.setAccount(cell.getStringCellValue());
                //所属部门
                cell = row.getCell(2);
                user.setDept(cell.getStringCellValue());
                //性别
                cell = row.getCell(3);
                user.setGender(cell.getStringCellValue().equals("男"));
                //手机号
                cell = row.getCell(4);
                String mobile = "";
                try{
                    mobile = cell.getStringCellValue();
                }catch (Exception e){
                    double deMobile = cell.getNumericCellValue();
                    mobile = BigDecimal.valueOf(deMobile).toString();
                }
                user.setMobile(mobile);
                //电子邮箱
                cell = row.getCell(5);
                user.setEmail(cell.getStringCellValue());
                //生日
                cell = row.getCell(6);
                if (cell.getDateCellValue()!=null){
                    user.setBirthday(cell.getDateCellValue());
                }
                //默认用户密码
                user.setPassword("123456");
                //默认用户状态值为有效
                user.setState(User.USER_STATE_VALID);
                // 5、保存用户
                add(user);
            }
        }

        workbook.close();
        fileInputStream.close();
    }

    @Override
    public List<User> findUserByAccount(String id, String account) {
        return userDao.selectUserByAccount(id,account);
    }

    @Override
    public void addUserAndRole(User user,String... roleIds) throws ServiceException {
        //保存用户
        add(user);
        //保存用户角色
        if (roleIds!=null){
            for (String roleId:roleIds){
                userDao.insertUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
            }
        }
    }

    @Override
    public void updateUserAndRole(User user, String... roleIds) {
        //1.根据用户删除角色
        userDao.deleteUserAndRoleByUid(user.getId());
        //2.更新用户
        userDao.update(user);
        //3.保存用户角色
        if (roleIds!=null){
            for (String roleId:roleIds){
                userDao.insertUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
            }
        }
    }

    @Override
    public List<UserRole> getUserRolesByUserId(String id) {
        return userDao.selectUserRoleByUid(id);
    }

    @Override
    public List<User> findUserByAccountAndPassword(String account, String password) {
        return userDao.selectUserByAccountAndPassword(account,password);
    }

}
