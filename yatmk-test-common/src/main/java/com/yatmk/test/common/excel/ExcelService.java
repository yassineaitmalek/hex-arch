package com.yatmk.test.common.excel;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yatmk.test.common.excel.components.reader.ExcelSheetData;
import com.yatmk.test.common.excel.components.writer.ExcelSheetExporter;
import com.yatmk.test.common.excel.components.writer.ExcelSheetWriter;
import com.yatmk.test.common.excel.reader.ExcelReaderService;
import com.yatmk.test.common.excel.writer.ExcelWriterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {

  private final ExcelReaderService excelReaderService;

  private final ExcelWriterService excelWriterService;

  public ExcelSheetExporter exportWorkBook(ExcelType type, String fileName, List<? extends ExcelSheetWriter> writers) {
    return excelWriterService.exportWorkBook(type, fileName, writers);
  }

  public ExcelSheetExporter exportWorkBook(ExcelType type, String fileName, ExcelSheetWriter... writers) {
    return excelWriterService.exportWorkBook(type, fileName, writers);
  }

  public List<ExcelSheetData> readExcel(InputStream in, ExcelType type, boolean isHeader) {
    return excelReaderService.readExcel(in, type, isHeader);
  }

}
