package com.nelumbo.sistemanotas.controllers;

import com.nelumbo.sistemanotas.exceptions.FileException;
import com.nelumbo.sistemanotas.services.dto.res.MessageDtoRes;
import com.nelumbo.sistemanotas.util.ExcelService;
import com.nelumbo.sistemanotas.services.RegistrationServices;
import com.nelumbo.sistemanotas.services.SubjectService;
import com.nelumbo.sistemanotas.services.dto.res.SubjectStudentsDtoRes;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final ExcelService excelService;
    private final RegistrationServices registrationServices;

    @PostMapping("{id}/upload-qualifications")
    public MessageDtoRes procesarExcel(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        try{
            Path templFile= Files.createTempFile(null,".xlsx");
            file.transferTo(templFile.toFile());
            return registrationServices.uploadQualifications(templFile.toFile(),id);
        }catch (Exception e){
            throw new FileException(e.getMessage());
        }
    }
    @GetMapping("/{id}/students")
    public SubjectStudentsDtoRes getStudentsBySubject(@PathVariable Long id) {
        return registrationServices.getRegistrationBySubject(id);
    }
    @GetMapping
    public Page<SubjectStudentsDtoRes> getAllSubjectsWithStudents(Pageable pageable) {
        return registrationServices.getSubjectsWithStudents(pageable);
    }

    @GetMapping("/{id}/excel")
    public void exportExcel(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        byte [] pdfbyte= registrationServices.exportExcel(id);
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=estudiantes.xlsx");
        httpServletResponse.setContentLength(pdfbyte.length);

        httpServletResponse.getOutputStream().write(pdfbyte);
    }
    @GetMapping("/{id}/pdf")
    public void exportPdf(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        byte [] pdfbyte= registrationServices.exportPDF(id);
        httpServletResponse.setContentType(MediaType.APPLICATION_PDF_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=estudiantes.pdf");
        httpServletResponse.setContentLength(pdfbyte.length);

        httpServletResponse.getOutputStream().write(pdfbyte);
    }
}
