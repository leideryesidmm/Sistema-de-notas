package com.nelumbo.sistemanotas.util;

import com.nelumbo.sistemanotas.exceptions.FileFormatException;
import com.nelumbo.sistemanotas.services.ValidationDtoService;
import com.nelumbo.sistemanotas.services.dto.req.QualificationDtoReq;
import com.nelumbo.sistemanotas.services.dto.res.StudentDtoRes;
import com.nelumbo.sistemanotas.services.dto.res.SubjectStudentsDtoRes;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final ValidationDtoService validator;
    public List<QualificationDtoReq> leerExcel(File file){
        List<QualificationDtoReq> qualificationDtoReqs =new ArrayList<>();
        try{
            InputStream inp = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int i=1;
            Row row= sheet.getRow(i);
            while(row!=null){
            QualificationDtoReq qualificationDtoReq =getQualification(row);
            validator.validateDto(qualificationDtoReq);
            qualificationDtoReqs.add(qualificationDtoReq);
            row=sheet.getRow(++i);
            }
            wb.close();
            return qualificationDtoReqs;
        }catch (Exception e){
            throw new FileFormatException(e.getMessage());
        }
    }

    private QualificationDtoReq getQualification(Row row) {
        QualificationDtoReq qualificationDtoReq =new QualificationDtoReq();
        qualificationDtoReq.setStudent((long)row.getCell(0).getNumericCellValue());
        qualificationDtoReq.setQuialification1(row.getCell(1).getNumericCellValue());
        qualificationDtoReq.setQuialification2(row.getCell(2).getNumericCellValue());
        qualificationDtoReq.setQuialification3(row.getCell(3).getNumericCellValue());
        return qualificationDtoReq;
    }
    public byte[] exportExcel(SubjectStudentsDtoRes subjectStudentsDtoRes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook= new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("Estudiantes de "+subjectStudentsDtoRes.getSubject().getName());

        Row header=sheet.createRow(0);
        header.createCell(0).setCellValue("Codigo");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Primera nota");
        header.createCell(3).setCellValue("Segunda nota");
        header.createCell(4).setCellValue("Tercera nota");
        header.createCell(5).setCellValue("Definitiva");

        int rownum=1;
        for (StudentDtoRes s:subjectStudentsDtoRes.getStudents()) {
            Row row=sheet.createRow(rownum++);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getName()+"");
            row.createCell(2).setCellValue(s.getId());
            row.createCell(3).setCellValue(s.getId());
            row.createCell(4).setCellValue(s.getId());
            row.createCell(5).setCellValue(s.getAvgQualification());
        }
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }
}
