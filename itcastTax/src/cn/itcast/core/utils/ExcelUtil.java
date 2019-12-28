package cn.itcast.core.utils;

import cn.itcast.nsfw.user.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    public static void exportUserExcel(List<User> userList, ServletOutputStream outputStream) throws IOException {
        //1、创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        ////1.1、创建合并单元格对象
        //起始行号，结束行号，起始列号，结束列号
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,4);
        //1.2、头标题样式
        HSSFCellStyle style1 = createCellStyle(workbook, (short)16);
        //1.3、列标题样式
        HSSFCellStyle style2 = createCellStyle(workbook, (short)13);
        //2、创建工作表
        HSSFSheet sheet = workbook.createSheet("用户列表");
        //2.1、加载合并单元格对象
        sheet.addMergedRegion(cellRangeAddress);
        //设置默认列宽
        sheet.setDefaultColumnWidth(25);
        //3、创建行
        //3.1、创建头标题行；并且设置头标题
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell1 = row1.createCell(0);
        //加载单元格样式
        cell1.setCellStyle(style1);
        cell1.setCellValue("用户列表");
        //3.2、创建列标题行；并且设置列标题
        HSSFRow row2 = sheet.createRow(1);
        String[] titles = {"用户名","帐号", "所属部门", "性别", "电子邮箱"};
        for(int i = 0; i < titles.length; i++){
            HSSFCell cell2 = row2.createCell(i);
            //加载单元格样式
            cell2.setCellStyle(style2);
            cell2.setCellValue(titles[i]);
        }

        //4、操作单元格；将用户列表写入excel
        if(userList != null){
            HSSFRow row ;
            HSSFCell cell;
            for(int j = 0; j < userList.size(); j++){
                row = sheet.createRow(j+2);
                //第一列
                cell = row.createCell(0);
                cell.setCellValue(userList.get(j).getName());
                //第二列
                cell = row.createCell(1);
                cell.setCellValue(userList.get(j).getAccount());
                //第三列
                cell = row.createCell(2);
                cell.setCellValue(userList.get(j).getDept());
                //第四列
                cell = row.createCell(3);
                cell.setCellValue(userList.get(j).getGender()?"男":"女");
                //第五列
                cell = row.createCell(4);
                cell.setCellValue(userList.get(j).getEmail());
            }
        }
        //5、输出
        workbook.write(outputStream);
        workbook.close();
    }

    /**
     * 创建单元格样式
     * @param workbook 工作簿
     * @param fontSize  字体大小
     * @return
     */
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //创建字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
        font.setFontHeightInPoints(fontSize);
        //加载字体
        style.setFont(font);
        return style;
    }
}
