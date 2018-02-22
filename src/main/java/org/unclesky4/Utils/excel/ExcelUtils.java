package org.unclesky4.Utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* POI操作excel
* @author unclesky4
* @date Feb 21, 2018 10:10:26 PM
*
*
*
*
    HSSF － 提供读写Microsoft Excel XLS格式档案的功能。
    XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
    HWPF － 提供读写Microsoft Word DOC格式档案的功能。
    HSLF － 提供读写Microsoft PowerPoint格式档案的功能。
    HDGF － 提供读Microsoft Visio格式档案的功能。
    HPBF － 提供读Microsoft Publisher格式档案的功能。
    HSMF － 提供读Microsoft Outlook格式档案的功能。
*/

public class ExcelUtils {
	
	/**
	 * 写XLS表格
	 * @param sheeetName
	 * @param row
	 * @param column
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public Boolean wirteXLS(String sheeetName,Integer row, Integer column, DataBean data, String path) throws IOException {
		
		Logger logger = Logger.getLogger(this.getClass());
		
		String[] tmp = path.split("\\.");
		if (!"xls".equals(tmp[tmp.length-1]) || !"xls".equals(tmp[tmp.length-1])) {
			logger.warn("路径错误");
			System.out.println("路径错误");
			return false;
		}
		File file = new File(path);
		if (!file.exists() && !file.createNewFile()) {
			logger.warn("创建文件失败");
			System.out.println("创建文件失败");
			return false;
		}
		
		// 创建xls工作薄
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    //创建xlsx工作薄
	    //XSSFWorkbook wb = new XSSFWorkbook(); 
	    
	    // 创建工作表
	    HSSFSheet sheet = workbook.createSheet(sheeetName);
	    
	    //excel列数
	    List<String> titleArray = data.getTitleArray();
	    int columnCount = titleArray.size();
	    columnCount = titleArray.size()>column?columnCount:column;
	    
	    //excel行数
	    List<ArrayList<String>> list = data.getData();
	    int rowCount = list.size();
	    rowCount = list.size()>row?rowCount:row;
	    
	    //创建第一栏
	    HSSFRow headRow = sheet.createRow(0);
	    for (int i = 0; i < titleArray.size(); i++) {
			HSSFCell cell = headRow.createCell(i);
			//cell.setCellType(CellType.EQUAL);
			sheet.setColumnWidth(i, 1000);
			
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			//填写数据
	        cell.setCellStyle(style);
	        cell.setCellValue(titleArray.get(i));
		}
	    
	    int index = 0;
        //写入数据
        Iterator<ArrayList<String>> iterator = list.iterator();
        while(iterator.hasNext())
        {
            HSSFRow data_row = sheet.createRow(index+1);
            ArrayList<String> info_data = iterator.next();
            for (int i = 0; i < info_data.size(); i++) {
				data_row.createCell(i);
				data_row.getCell(i).setCellValue(info_data.get(i));
				//data_row.createCell(i).setCellValue(Calendar.getInstance());
			}
            index++;
        }

        //写到磁盘上
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return true;
	}
	
	/**
	 * 读取xls文件
	 * @param path
	 * @throws IOException 
	 */
	public void readXLS(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			Logger.getLogger(this.getClass()).error("file not found!");
			return;
		}
	    POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
	    HSSFWorkbook hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);
	    HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

	    int rowstart = hssfSheet.getFirstRowNum();
	    int rowEnd = hssfSheet.getLastRowNum();
	    for(int i=rowstart;i<=rowEnd;i++)
	    {
	        HSSFRow row = hssfSheet.getRow(i);
	        if(null == row) continue;
	        int cellStart = row.getFirstCellNum();
	        int cellEnd = row.getLastCellNum();

	        for(int k=cellStart;k<=cellEnd;k++)
	        {
	            HSSFCell cell = row.getCell(k);
	            if(null==cell) continue;
	            //System.out.print("" + k + "  ");
	            //System.out.print("type:"+cell.getCellType());

	            switch (cell.getCellType())
	            {
	                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
	                                System.out.print(cell.getNumericCellValue()
	                            + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_STRING: // 字符串
	                    System.out.print(cell.getStringCellValue()
	                            + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
	                    System.out.println(cell.getBooleanCellValue()
	                            + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_FORMULA: // 公式
	                    System.out.print(cell.getCellFormula() + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_BLANK: // 空值
	                    System.out.println(" ");
	                    break;
	                case HSSFCell.CELL_TYPE_ERROR: // 故障
	                    System.out.println(" ");
	                    break;
	                default:
	                    System.out.print("未知类型   ");
	                    break;
	            }

	        }
	        System.out.print("\n");
	    }
	}
	
	/**
	 * 读取xlsx文件
	 * @param path
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public void readXLSX(String path) throws InvalidFormatException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			Logger.getLogger(this.getClass()).error("file not found!");
			return;
		}
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

	    DataFormatter formatter = new DataFormatter();
	    XSSFSheet sheet1 = xssfWorkbook.getSheetAt(0);
	    for (Row row : sheet1) {
	        for (Cell cell : row) {
	            CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
	            System.out.print(cellRef.formatAsString());
	            System.out.print(" - ");

	            // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
	            String text = formatter.formatCellValue(cell);
	            System.out.println(text);

	            // Alternatively, get the value and format it yourself
	            switch (cell.getCellType()) {
	                case HSSFCell.CELL_TYPE_STRING:
	                    System.out.println(cell.getRichStringCellValue().getString());
	                    break;
	                case HSSFCell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(cell)) {
	                        System.out.println(cell.getDateCellValue());
	                    } else {
	                        System.out.println(cell.getNumericCellValue());
	                    }
	                    break;
	                case HSSFCell.CELL_TYPE_BOOLEAN:
	                    System.out.println(cell.getBooleanCellValue());
	                    break;
	                case HSSFCell.CELL_TYPE_FORMULA:
	                    System.out.println(cell.getCellFormula());
	                    break;
	                case HSSFCell.CELL_TYPE_BLANK:
	                    System.out.println();
	                    break;
	                default:
	                    System.out.println();
	            }
	        }
	    }
	}

	public static void main(String[] args) throws IOException {
		ExcelUtils excelUtils = new ExcelUtils();
		DataBean data = new DataBean();
		ArrayList<String> titleArray = new ArrayList<>();
		titleArray.add("姓名");
		titleArray.add("性别");
		titleArray.add("年龄");
		titleArray.add("专业");
		data.setTitleArray(titleArray);
		
		List<ArrayList<String>> dataArray = new ArrayList<ArrayList<String>>();
		for(int i=0; i<2; i++) {
			ArrayList<String> info = new ArrayList<>();
			info.add("1");
			info.add("1");
			info.add("1");
			info.add("1");
			info.add("1");
			dataArray.add(info);
		}
		data.setData(dataArray);
		excelUtils.wirteXLS("stu", 2, 5, data, "/home/uncle/Desktop/a.xls");
		
		
		//读取
		excelUtils.readXLS("/home/uncle/Desktop/a.xls");
	}

}

/**
 * 
 * excel表格数据类
 * @author unclesky4
 *
 */
class DataBean {
	//第一栏标题
	private List<String> titleArray;
	
	//行列数据
	private List<ArrayList<String>> data;  //ArrayList<String>可以更改为实体对象
	
	public DataBean() {}

	public List<String> getTitleArray() {
		return titleArray;
	}

	public void setTitleArray(List<String> titleArray) {
		this.titleArray = titleArray;
	}

	public List<ArrayList<String>> getData() {
		return data;
	}

	public void setData(List<ArrayList<String>> data) {
		this.data = data;
	}
}