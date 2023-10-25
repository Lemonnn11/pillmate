package com.example.drugoo.controller;

import com.example.drugoo.model.Drug;
import com.example.drugoo.model.QrcodeAdditionalInfo;
import com.example.drugoo.service.Qrcodeservice;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/qrcode")
public class QrcodeController {
    private final Qrcodeservice qrcodeservice;

    @Autowired
    public QrcodeController(Qrcodeservice qrcodeservice) {
        this.qrcodeservice = qrcodeservice;
    }

    @CrossOrigin
    @GetMapping("/create")
    public ResponseEntity<HttpStatus> createQrcode(@RequestParam("id") String id, @RequestBody QrcodeAdditionalInfo info) throws IOException, WriterException {
        qrcodeservice.createQrcode(id, info.getExpiredDate(), info.getDescription());
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
