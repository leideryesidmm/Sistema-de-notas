package com.nelumbo.sistemanotas.services;

import com.nelumbo.sistemanotas.exceptions.FileException;
import com.nelumbo.sistemanotas.services.dto.req.QualificationDtoReq;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    public List<QualificationDtoReq> leerExcel(File file){
        List<QualificationDtoReq> qualificationDtoReqs =new ArrayList<>();
        try{
            InputStream inp = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int i=0;
            Row row= sheet.getRow(i);
            while(row!=null){
            QualificationDtoReq qualificationDtoReq =getQualification(row);
            qualificationDtoReqs.add(qualificationDtoReq);
            row=sheet.getRow(++i);
            }
            System.out.println(qualificationDtoReqs);
            return qualificationDtoReqs;
        }catch (Exception e){
            throw new FileException(e.getMessage());
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
}
