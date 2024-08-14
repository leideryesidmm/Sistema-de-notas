package com.nelumbo.sistemanotas.controllers;

import com.nelumbo.sistemanotas.exceptions.FileException;
import com.nelumbo.sistemanotas.services.ExcelService;
import com.nelumbo.sistemanotas.services.dto.req.QualificationDtoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final ExcelService excelService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QualificationDtoReq> procesarExcel(@RequestParam("file") MultipartFile file) {
        try{
            Path templFile= Files.createTempFile(null,".xlsx");
            file.transferTo(templFile.toFile());
            return excelService.leerExcel(templFile.toFile());
        }catch (Exception e){
            throw new FileException("error al leer el excel: "+ e.getMessage());
        }
    }
}
