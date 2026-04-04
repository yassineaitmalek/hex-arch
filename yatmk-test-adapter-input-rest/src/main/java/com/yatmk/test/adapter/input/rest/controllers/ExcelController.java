package com.yatmk.test.adapter.input.rest.controllers;


import com.yatmk.test.adapter.input.rest.config.AbstractResponseController;
import com.yatmk.test.adapter.input.rest.dto.FileInput;
import com.yatmk.test.common.excel.ExcelService;
import com.yatmk.test.common.excel.ExcelType;
import com.yatmk.test.common.excel.components.reader.ExcelSheetData;
import com.yatmk.test.common.excel.components.writer.ExcelSheetExporter;
import com.yatmk.test.common.excel.components.writer.SimpleExcelWriter;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import com.yatmk.test.ports.domain.presentation.ApiDownloadInput;
import com.yatmk.test.ports.domain.test.TestDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping( ExcelController.PATH )
@Tag( name = "excel-io", description = "Excel Input/Output" )
public class ExcelController implements AbstractResponseController {

	public static final String PATH = "/api/excel-io";

	private final ExcelService excelService;

	@ApiResponse(
	        responseCode = "200", description = "OK", content = @Content( mediaType = "application/octet-stream", schema = @Schema( type = "string", format = "binary" ) )
	)
	@GetMapping( value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
	public ResponseEntity<byte[]> downloadSimpleXLSX() {
		SimpleExcelWriter<TestDTO> writer = new SimpleExcelWriter<TestDTO>() {
			@Override
			public void write(TestDTO entity, Sheet sheet, AtomicInteger row, AtomicInteger column) {
				addLabel(sheet, column, row, entity.getId());
				addLabel(sheet, column, row, entity.getAttr1());
				addLabel(sheet, column, row, entity.getAttr2());
				addLabel(sheet, column, row, entity.getAttr3());
				addLabel(sheet, column, row, entity.getAttr4());
			}

		};
		writer.setHeader(Arrays.asList("id", "str1", "str2", "str3", "str4"));
		writer.setSheetName("str");
		writer.setLines(LongStream.range(1, 1000).mapToObj(TestDTO::new).collect(Collectors.toList()));
		ExcelSheetExporter excelSheetExporter = excelService.exportWorkBook(ExcelType.XLSX, "TestModelSimple", writer);

		return download(
		        ApiDownloadInput.builder().bytes(excelSheetExporter.getBytes()).fileName(excelSheetExporter.getFileName()).ext(excelSheetExporter.getExt()).build()
		);
	}

	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	        required = true, content = @Content(
	                mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema( implementation = FileInput.class )
	        )
	)
	@ApiResponse(
	        responseCode = "200", description = "excel file imported successfully", content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
	)
	@PutMapping(
	        value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ApiDataResponse<List<ExcelSheetData>>> importSimpleXLSX(
	                                                                              @ParameterObject @ModelAttribute FileInput fileDTO
	) {
		try ( InputStream in = fileDTO.getFile().getInputStream() ) {
			return ok(() -> excelService.readExcel(in, ExcelType.XLSX, true));
		} catch ( Exception e ) {
			throw new ServerSideException(e);
		}
	}

}
