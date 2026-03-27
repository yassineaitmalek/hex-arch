
package com.yatmk.test.common.excel.writer;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.yatmk.test.common.excel.ExcelType;
import com.yatmk.test.common.excel.components.writer.ExcelSheetExporter;
import com.yatmk.test.common.excel.components.writer.ExcelSheetWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelWriterService {

  public ExcelSheetExporter exportWorkBook(ExcelType type, String fileName, ExcelSheetWriter... writers) {
    return exportWorkBook(type, fileName, Arrays.asList(writers));
  }

  public ExcelSheetExporter exportWorkBook(ExcelType type, String fileName, List<? extends ExcelSheetWriter> writers) {

    try (Workbook workbook = ExcelType.createWorkbook(type);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
      writers.forEach(e -> e.write(workbook));
      workbook.write(baos);
      return ExcelSheetExporter.builder()
          .bytes(baos.toByteArray())
          .fileName(fileName)
          .ext(type.getExt())
          .build();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
