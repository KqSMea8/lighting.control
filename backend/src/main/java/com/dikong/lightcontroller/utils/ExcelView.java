package com.dikong.lightcontroller.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.dikong.lightcontroller.vo.HistoryList;


public class ExcelView extends AbstractXlsView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"历史数据.xls\"");

        @SuppressWarnings("unchecked")
        List<HistoryList> historyLists = (List<HistoryList>) model.get("results");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Sheet1");

        String[] header = (String[]) model.get("headers");

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(HistoryList historyList: historyLists) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(historyList.getDtuName());
            row.createCell(1).setCellValue(historyList.getDeviceName());
            row.createCell(2).setCellValue(historyList.getRegisName());
            row.createCell(4).setCellValue(historyList.getVarValue());
            row.createCell(5).setCellValue(historyList.getCreateTime());
            row.createCell(6).setCellValue(historyList.getCreateBy());
        }

    }

}
