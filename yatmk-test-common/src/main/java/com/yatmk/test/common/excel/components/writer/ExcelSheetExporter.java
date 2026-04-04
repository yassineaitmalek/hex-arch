package com.yatmk.test.common.excel.components.writer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelSheetExporter {

	private byte[] bytes;

	private String fileName;

	private String ext;

}
