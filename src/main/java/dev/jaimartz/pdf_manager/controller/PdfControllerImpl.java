package dev.jaimartz.pdf_manager.controller;

import dev.jaimartz.pdf_manager.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PdfControllerImpl {

    private final PdfService service;

    @GetMapping(path = "/create", produces = "application/pdf")
    public ResponseEntity<byte[]> createPdf() {
        try {
            log.info("Este es un log de ejemplo hecho con lombok para SLF4J");

            InputStream is = service.createEmptyPdf();
            return ResponseEntity.ok(is.readAllBytes());
        } catch (Exception e) {
            log.error("error leyendo el input stream {}", e.getMessage());
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/ejemplo")
    public ResponseEntity<Object> ejemplo () {
        return ResponseEntity.ok().build();
    }
}
