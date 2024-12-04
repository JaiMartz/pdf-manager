package dev.jaimartz.pdf_manager.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
public class PdfServiceImpl implements PdfService{

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public InputStream createEmptyPdf() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //Path path = Paths.get(ClassLoader.getSystemResource("DDD_logo.webp").toURI());
            ClassPathResource resource = new ClassPathResource("static/DDD_logo.png");
            File file = resource.getFile();
            //Resource resource = resourceLoader.getResource("classpath:static/DDD_logo.webp");

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);
            document.add(chunk);

            Image img = Image.getInstance(file.getAbsolutePath());
            document.add(img);

            document.close();
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return inputStream;
        } catch (Exception e) {
            log.error("La hemos cagao amigo: {}", e.getMessage());
            return null;
        }
    }
}
