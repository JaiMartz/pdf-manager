package dev.jaimartz.pdf_manager.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface PdfService {

    ByteArrayInputStream createPdf();

    ByteArrayInputStream mergePdfs(MultipartFile pdf1, MultipartFile pdf2);

    ByteArrayInputStream splitPdf(MultipartFile pdfFile, int startPage, int endPage);
}
