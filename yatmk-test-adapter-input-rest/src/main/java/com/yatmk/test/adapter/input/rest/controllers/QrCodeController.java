package com.yatmk.test.adapter.input.rest.controllers;


import com.yatmk.test.adapter.input.rest.config.AbstractResponseController;
import com.yatmk.test.adapter.input.rest.dto.FileInput;
import com.yatmk.test.adapter.input.rest.dto.QrInput;
import com.yatmk.test.common.qrcode.QrCodeDecoder;
import com.yatmk.test.common.qrcode.QrCodeEncoder;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import com.yatmk.test.ports.domain.presentation.ApiDownloadInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping( QrCodeController.PATH )
@Tag( name = "qr-code", description = "Operations for QR Code" )
public class QrCodeController implements AbstractResponseController {

	public static final String PATH = "/api/qr-code";

	private final QrCodeEncoder qrCodeEncoder;

	private final QrCodeDecoder qrCodeDecoder;

	@Operation( summary = "Decode a QR Code" )
	@io.swagger.v3.oas.annotations.parameters.RequestBody( required = true, content = @Content( mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema( implementation = FileInput.class ) ) )
	@ApiResponse( responseCode = "200", description = "QR Code decoded successfully", content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE ) )
	@PutMapping( value = "/decode", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ApiDataResponse<String>> decode(@ParameterObject @ModelAttribute FileInput input) {
		Assert.notNull(input);
		Assert.notNull(input.getFile());
		MultipartFile file = input.getFile();
		Assert.notNull(file);
		try ( InputStream stream = file.getInputStream() ) {
			return ok(() -> qrCodeDecoder.decode(stream));
		} catch ( Exception e ) {
			throw new ServerSideException("Failed to decode QR Code", e);
		}
	}

	@ApiResponse( responseCode = "200", description = "OK", content = @Content( mediaType = "application/octet-stream", schema = @Schema( type = "string", format = "binary" ) ) )
	@PostMapping( value = "/encode", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
	public ResponseEntity<byte[]> encode(@ParameterObject @ModelAttribute QrInput input) {
		Assert.notNull(input);
		byte[] qrCodeBytes = qrCodeEncoder.encode(input.getText(), input.getHeight(), input.getWidth());
		ApiDownloadInput apiDownloadInput = ApiDownloadInput.builder().fileName("qr_code_" + UUID.randomUUID()).bytes(qrCodeBytes).ext("png").build();
		return download(apiDownloadInput);
	}

}
