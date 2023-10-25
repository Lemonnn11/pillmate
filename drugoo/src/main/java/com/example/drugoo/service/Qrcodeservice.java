package com.example.drugoo.service;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Qrcodeservice {

    void createQrcode(String id, String exp, String des) throws IOException, WriterException;

}
