package dev.jaimartz.pdf_manager.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import dev.jaimartz.pdf_manager.utils.ChiquitoTexts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
@Service
public class PdfServiceImpl implements PdfService{

    @Value("${chiquito.ipsum.3}")
    private String chiquitoIpsum3;


    //ResourceLoader resourceLoader;

    @Override
    public ByteArrayInputStream createPdf() {

        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Hola desde OpenPDF y Spring Boot!"));
            document.add(new Paragraph("Este PDF fue generado dinámicament."));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(ChiquitoTexts.chiquitoIpsum3()));
            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            log.error("La hemos cagao amigo: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ByteArrayInputStream mergePdfs(MultipartFile pdf1, MultipartFile pdf2) {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, out);
            document.open();

            PdfReader reader1 = new PdfReader(pdf1.getInputStream());
            PdfReader reader2 = new PdfReader(pdf2.getInputStream());

            for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
                copy.addPage(copy.getImportedPage(reader1, i));
            }
            for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
                copy.addPage(copy.getImportedPage(reader2, i));
            }

            reader1.close();
            reader2.close();
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error uniendo PDF's");
        }
    }

    @Override
    public ByteArrayInputStream splitPdf(MultipartFile pdfFile, int startPage, int endPage) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfReader reader = new PdfReader(pdfFile.getInputStream());
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, out);
            document.open();

            if (startPage < 1 || endPage > reader.getNumberOfPages() || startPage > endPage) {
                throw new IllegalArgumentException("El rango de páginas no es válido.");
            }

            for (int i = startPage; i <= endPage ; i++) {
                copy.addPage(copy.getImportedPage(reader, i));
            }

            reader.close();
            document.close();

            return new ByteArrayInputStream(out.toByteArray());


        } catch (Exception e) {

        }
        return null;
    }


}
