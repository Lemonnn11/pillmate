package com.example.drugoo.service;

import com.example.drugoo.model.Drug;
import com.example.drugoo.repository.DrugElasticRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrcodeServiceimpl implements Qrcodeservice{

    private final DrugElasticRepository drugElasticRepository;

    @Autowired
    public QrcodeServiceimpl(DrugElasticRepository drugElasticRepository) {
        this.drugElasticRepository = drugElasticRepository;
    }

    @Override
    public void createQrcode(String id, String exp, String des) throws IOException, WriterException {
        String path = "C:\\src\\demo.png";
        Drug drug = drugElasticRepository.findById(id).orElse(null);
        assert drug != null;
        String data = "{ " + "name: " + drug.getName() + ", action: " + drug.getAction() + ", metabolism: " + drug.getMetabolism() + ", formula: " + drug.getFormula() +
                ", expired date: " + exp + ", additional description: " + des +" }";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        String encodedString = new String(data.getBytes("UTF-8"), "UTF-8");
        BitMatrix matrix = new MultiFormatWriter().encode(encodedString, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
}
