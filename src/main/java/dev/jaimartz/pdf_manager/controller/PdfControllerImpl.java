package dev.jaimartz.pdf_manager.controller;

import dev.jaimartz.pdf_manager.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PdfControllerImpl {

    private final PdfService service;

    @GetMapping(path = "/create", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> createPdf() {
        try {
            ByteArrayInputStream pdf = service.createPdf();
            log.info("Este es un log de ejemplo hecho con lombok para SLF4J");

            InputStream is = service.createPdf();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdf));

        } catch (Exception e) {
            log.error("error leyendo el input stream {}", e.getMessage());
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/merge")
    public ResponseEntity<InputStreamResource> mergePdfs(
            @RequestParam("pdf1")MultipartFile pdf1,
            @RequestParam("pdf2")MultipartFile pdf2) {

        ByteArrayInputStream pdf = service.mergePdfs(pdf1, pdf2);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=merged.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @PostMapping("/split")
    public ResponseEntity<InputStreamResource> splitPdf(
            @RequestParam("pdf") MultipartFile pdfFile,
            @RequestParam("startPage") int startPage,
            @RequestParam("endPage") int endPage) {
        ByteArrayInputStream splitPdf = service.splitPdf(pdfFile, startPage, endPage);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=split..pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(splitPdf));
    }
}
