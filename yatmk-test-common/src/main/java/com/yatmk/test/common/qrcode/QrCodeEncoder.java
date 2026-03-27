package com.yatmk.test.common.qrcode;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QrCodeEncoder {

  private static final String EXT = "png";

  private static final int DEFAULT_HEIGTH = 300;

  private static final int DEFAULT_WIDTH = 300;

  public byte[] encode(String text, Integer height, Integer width) {
    Assert.notNull(text, "text input must not be null");
    try {
      String utf8Input = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
      MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
      int realHeight = (Objects.nonNull(height)) ? height : DEFAULT_HEIGTH;
      int realWidth = (Objects.nonNull(width)) ? width : DEFAULT_WIDTH;
      BitMatrix bitMatrix = multiFormatWriter.encode(utf8Input, BarcodeFormat.QR_CODE, realWidth, realHeight);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, EXT, baos);
      return baos.toByteArray();

    } catch (Exception e) {
      throw new RuntimeException("Failed to encode QR code", e);
    }
  }

}
