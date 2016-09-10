package com.familyan.smarth.manager.file.utils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by shaowenchao on 16/5/31.
 */
public class ExcelUtils {

    public static void createExcel(String sheetName, List<String> titles, List<List<String>> rows, OutputStream outputStream) throws IOException, WriteException {
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
        WritableSheet sheet = workbook.createSheet(sheetName, 0);
        // 设置标题
        for (int i = 0; i < titles.size(); i++) {
            sheet.addCell(new Label(i, 0, titles.get(i)));
        }

        // 填充数据
        for(int i = 0; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for(int j = 0; j < row.size(); j++) {
                sheet.addCell(new Label(j, i+1, row.get(j)));
            }
        }

        // 输出结果
        workbook.write();
        workbook.close();

    }

}
