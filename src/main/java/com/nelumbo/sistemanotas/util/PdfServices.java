package com.nelumbo.sistemanotas.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nelumbo.sistemanotas.services.dto.res.StudentDtoRes;
import com.nelumbo.sistemanotas.services.dto.res.SubjectStudentsDtoRes;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfServices {

    public byte[] exportPDF(SubjectStudentsDtoRes subjectStudentsDtoRes) throws IOException {
        Document document=new Document();
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        try{
            PdfWriter.getInstance(document,out);
            document.open();

            BaseColor bgColor=new BaseColor(49, 66, 114);
            BaseColor bgColor2=new BaseColor(78, 125, 221);
            BaseColor pasa=new BaseColor(14, 117, 34);
            BaseColor pierde=new BaseColor(200, 45, 33);


            document.add(new Paragraph("Listado de estudiantes de "+subjectStudentsDtoRes.getSubject().getName()));
            document.add(new Paragraph("\n"));

            Font headerFont=new Font(Font.FontFamily.HELVETICA,12,Font.BOLD, BaseColor.WHITE);
            PdfPCell pdfPCell;
            PdfPTable table=new PdfPTable(6);

            pdfPCell=new PdfPCell(new Paragraph("Código",headerFont));
            pdfPCell.setBackgroundColor(bgColor);
            table.addCell(pdfPCell);
            pdfPCell=new PdfPCell(new Paragraph("Nombre",headerFont));
            pdfPCell.setBackgroundColor(bgColor);
            table.addCell(pdfPCell);
            pdfPCell=new PdfPCell(new Paragraph("Primera nota",headerFont));
            pdfPCell.setBackgroundColor(bgColor);
            table.addCell(pdfPCell);

            pdfPCell=new PdfPCell(new Paragraph("Segunda nota",headerFont));
            pdfPCell.setBackgroundColor(bgColor);
            table.addCell(pdfPCell);
            pdfPCell=new PdfPCell(new Paragraph("Tercera nota",headerFont));
            pdfPCell.setBackgroundColor(bgColor);
            table.addCell(pdfPCell);

            pdfPCell=new PdfPCell(new Paragraph("Definitiva",headerFont));
            pdfPCell.setBackgroundColor(bgColor);
            table.addCell(pdfPCell);

            table.setHeaderRows(1);
            for (StudentDtoRes s:subjectStudentsDtoRes.getStudents()) {
                pdfPCell=new PdfPCell(new Paragraph(""+s.getId(),headerFont));
                pdfPCell.setBackgroundColor(bgColor2);
                table.addCell(pdfPCell);
                table.addCell(""+s.getName());
                table.addCell(""+s.getQualification1());
                table.addCell(""+s.getQualification2());
                table.addCell(""+s.getQualification3());
                PdfPCell pdfPCell2=new PdfPCell(new Paragraph(""+s.getAvgQualification(),headerFont));
                pdfPCell2.setBackgroundColor(s.getAvgQualification()>=3?pasa:pierde);
                table.addCell(pdfPCell2);
            }
            document.add(table);
            document.close();
        }catch (DocumentException e){
            throw new IOException("Eror al generar el PDF",e);
        }
        return out.toByteArray();
    }
}
