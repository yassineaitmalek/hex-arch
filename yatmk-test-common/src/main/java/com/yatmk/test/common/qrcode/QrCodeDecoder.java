package com.yatmk.test.common.qrcode;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QrCodeDecoder {

    public String decode(InputStream inputStream) {
        try {
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            HybridBinarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            Result result = multiFormatReader.decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode QR code", e);
        }
    }
}
