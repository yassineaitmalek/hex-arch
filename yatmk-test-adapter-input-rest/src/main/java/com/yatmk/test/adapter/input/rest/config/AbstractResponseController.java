package com.yatmk.test.adapter.input.rest.config;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import com.yatmk.test.ports.domain.presentation.ApiDownloadInput;
import com.yatmk.test.ports.domain.presentation.ApiPartialInput;

public interface AbstractResponseController {
        public default <T> ResponseEntity<ApiDataResponse<T>> ok(Supplier<T> supplier) {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(
                                                ApiDataResponse
                                                                .<T>builder()
                                                                .data(supplier.get())
                                                                .httpStatus(HttpStatus.OK.value())
                                                                .status(HttpStatus.OK.toString())
                                                                .build());
        }

        public default <T> ResponseEntity<ApiDataResponse<T>> create(Supplier<T> supplier) {
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(
                                                ApiDataResponse
                                                                .<T>builder()
                                                                .data(supplier.get())
                                                                .httpStatus(HttpStatus.CREATED.value())
                                                                .status(HttpStatus.CREATED.toString())
                                                                .build());
        }

        public default ResponseEntity<Void> noContent(Runnable runnable) {
                runnable.run();
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        public default <T> ResponseEntity<ApiDataResponse<T>> async(Runnable runnable, Supplier<T> supplier) {
                runnable.run();
                return ResponseEntity
                                .status(HttpStatus.ACCEPTED)
                                .body(
                                                ApiDataResponse
                                                                .<T>builder()
                                                                .data(supplier.get())
                                                                .httpStatus(HttpStatus.ACCEPTED.value())
                                                                .status(HttpStatus.ACCEPTED.toString())
                                                                .build());
        }

        public default ResponseEntity<byte[]> download(ApiDownloadInput apiDownloadInput) {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .header("Content-Type", "application/octet-stream")
                                .header("Content-Disposition",
                                                "attachment; filename=" + apiDownloadInput.getValidName())
                                .header("Content-Length", String.valueOf(apiDownloadInput.getBytes().length))
                                .body(apiDownloadInput.getBytes());
        }

        public default ResponseEntity<byte[]> partial(ApiPartialInput apiPartialInput) {
                return ResponseEntity
                                .status(HttpStatus.PARTIAL_CONTENT)
                                .header("Content-Type", apiPartialInput.getContent() + "/" + apiPartialInput.getExt())
                                .header("Accept-Ranges", "bytes")
                                .header("Content-Length", String.valueOf(apiPartialInput.getLenght()))
                                .header(
                                                "Content-Range",
                                                "bytes" +
                                                                " " +
                                                                apiPartialInput.getStart() +
                                                                "-" +
                                                                apiPartialInput.getEnd() +
                                                                "/" +
                                                                apiPartialInput.getSize())
                                .body(apiPartialInput.getBytes());
        }
}
