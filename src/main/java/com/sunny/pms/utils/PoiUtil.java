package com.sunny.pms.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class PoiUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static List<String[]> readExcel(MultipartFile file) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();

                Row firstRow = sheet.getRow(firstRowNum);
                if(firstRow == null){
                    continue;
                }
                int lastCellNum = firstRow.getLastCellNum();//为空列获取

                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){ //为了过滤到第一行因为我的第一行是数据库的列
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }

//                    int lastCellNum = row.getPhysicalNumberOfCells();//为空列不获取
//                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    String[] cells = new String[lastCellNum];
                    //循环当前行
                    for(int cellNum = 0; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }

    public static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == CellType.NUMERIC){
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
//                cellValue = String.valueOf(cell.getCellFormula());
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 导出公用方法
     * @param list  导出数据对象列表
     * @param titleArr  标题字段和中文翻译[{name:'字段状态组',value:'czdztz'},{name:'凭证字段_CN',value:'cpzzdcn'},....
     * @param <T>
     * @throws Exception
     */
    public static <T> void export(List<T> list , JSONArray titleArr, String fileName, HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //设置标题导出时间
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        style.setFont(font);
        Row timeRow = sheet.createRow(0);
        CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, titleArr.length());
        sheet.addMergedRegion(titleRegion);
        Cell timeCell = timeRow.createCell(0);
        timeCell.setCellStyle(style);
        timeCell.setCellValue("导出时间: " + DateUtil.format(new Date(),"yyyy-MM-dd HH:mm"));
        //设置标题行
        Row titleRow = sheet.createRow(1);
        for (int j = 0; j < titleArr.length(); j++) {
            org.json.JSONObject jsonObj = titleArr.getJSONObject(j);
            String name = jsonObj.getString("name");
            Cell cell = titleRow.createCell(j);
            cell.setCellValue(name);
            cell.setCellStyle(style);
        }
        //设置导出数据
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i + 2);
            for (int j = 0; j < titleArr.length(); j++) {
                org.json.JSONObject jsonObj = titleArr.getJSONObject(j);
                String value = jsonObj.getString("value");
                /*截取方法名的第一个字符变大写*/
                String firstLetter = value.substring(0, 1).toUpperCase();
                jsonObj.getString("name");
                /*拼接get方法名*/
                String getter = "get" + firstLetter + value.substring(1);
                /*变成get方法*/
                Method method = list.get(i).getClass().getMethod(getter, new Class[]{});
                /*获取value值*/
                Object beanValue = StringUtil.NullToEmpty(method.invoke(list.get(i), new Object[]{}));

                Cell cell = row.createCell(j);
                cell.setCellValue(beanValue.toString());
            }
        }

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            workbook.write(output);
        }catch (IOException e){
            throw e;
        }finally {
            output.close();
        }
    }

    /**
     * 导出公用方法
     * @param list  导出数据对象列表
     * @param titleArr  标题字段和中文翻译[{name:'字段状态组',value:'czdztz'},{name:'凭证字段_CN',value:'cpzzdcn'},....
     * @param <T>
     * @throws Exception
     */
    public static <T> void export1(List<T> list , JSONArray titleArr,String fileName, HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //设置标题导出时间
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        style.setFont(font);
        Row timeRow = sheet.createRow(0);
        CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, titleArr.length());
        sheet.addMergedRegion(titleRegion);
        Cell timeCell = timeRow.createCell(0);
        timeCell.setCellStyle(style);
        timeCell.setCellValue("导出时间: " + DateUtil.format(new Date(),"yyyy-MM-dd HH:mm"));
        //设置标题行
        Row titleRow = sheet.createRow(1);
        for (int j = 0; j < titleArr.length(); j++) {
            org.json.JSONObject jsonObj = titleArr.getJSONObject(j);
            String name = jsonObj.getString("name");
            Cell cell = titleRow.createCell(j);
            cell.setCellValue(name);
            cell.setCellStyle(style);
        }
        //设置导出数据
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i + 2);
            for (int j = 0; j < titleArr.length(); j++) {
                org.json.JSONObject jsonObj = titleArr.getJSONObject(j);
                String value = jsonObj.getString("value");
                String firstLetter = null;
                //截取方法名的第一个字符变大小写
                if(value.equals("id") || value.equals("lzDate") || value.equals("lb") || value.equals("gh")){
                    firstLetter = value.substring(0, 1).toUpperCase();
                }else{
                    firstLetter = value.substring(0, 1).toLowerCase();
                }
                jsonObj.getString("name");
                /*拼接get方法名*/
                String getter = "get" + firstLetter + value.substring(1);
                /*变成get方法*/
                Method method = list.get(i).getClass().getMethod(getter, new Class[]{});
                /*获取value值*/
                Object beanValue = StringUtil.NullToEmpty(method.invoke(list.get(i), new Object[]{}));

                Cell cell = row.createCell(j);
                cell.setCellValue(beanValue.toString());
            }
        }

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            workbook.write(output);
        }catch (IOException e){
            throw e;
        }finally {
            output.close();
        }
    }
}
