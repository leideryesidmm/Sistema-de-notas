package com.nelumbo.sistemanotas.services;

import com.nelumbo.sistemanotas.entities.Registration;
import com.nelumbo.sistemanotas.entities.Student;
import com.nelumbo.sistemanotas.entities.Subject;
import com.nelumbo.sistemanotas.exceptions.ResourceNotFoundException;
import com.nelumbo.sistemanotas.repositories.RegistrationRepository;
import com.nelumbo.sistemanotas.services.dto.req.QualificationDtoReq;
import com.nelumbo.sistemanotas.services.dto.res.*;
import com.nelumbo.sistemanotas.util.ExcelService;
import com.nelumbo.sistemanotas.util.PdfServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServices {
    private final RegistrationRepository registrationRepository;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final ExcelService excelService;
    private final PdfServices pdfServices;
    private final ModelMapper modelMapper;

    public SubjectStudentsDtoRes getRegistrationBySubject(Long id){
        SubjectDtoRes subjectDtoRes=subjectService.getSubjectById(id);
        SubjectStudentsDtoRes subjectStudentsDtoRes=new SubjectStudentsDtoRes();
        subjectStudentsDtoRes.setSubject(subjectDtoRes);

        List<Registration> registrations=this.registrationRepository.findAllOrderedByTotalQualifications(modelMapper.map(subjectDtoRes,Subject.class));
        if(!registrations.isEmpty()){
            List<StudentDtoRes> studentsDtoRes=new ArrayList<>();
            for (Registration registration:registrations) {
                StudentDtoRes studentDtoRes=(modelMapper.map(registration.getStudent(),StudentDtoRes.class));
                studentDtoRes.setQualification1(registration.getQualification1());
                studentDtoRes.setQualification2(registration.getQualification2());
                studentDtoRes.setQualification3(registration.getQualification3());
                studentDtoRes.setAvgQualification(Math.round(getAVG(registration)*100.0)/100.0);
                studentsDtoRes.add(studentDtoRes);
            }
            subjectStudentsDtoRes.setStudents(studentsDtoRes);
        }

        return subjectStudentsDtoRes;
    }
    public Page<SubjectStudentsDtoRes> getSubjectsWithStudents(Pageable pageable){
        List<SubjectStudentsDtoRes> subjectStudentsDtoRes=new ArrayList<>();
        Page<SubjectDtoRes> subjectDtoRes=this.subjectService.getSubject(pageable);
        for (SubjectDtoRes s:subjectDtoRes) {
            SubjectStudentsDtoRes subjectStudentsDtoRes1=getRegistrationBySubject(s.getId());
            subjectStudentsDtoRes.add(subjectStudentsDtoRes1);
        }
        return new PageImpl<>(subjectStudentsDtoRes,pageable,subjectDtoRes.getTotalElements());
    }
    public Double getAVG(Registration registration){
        return (registration.getQualification1()+
                registration.getQualification2()+
                registration.getQualification3())/3 ;
    }
    @Transactional
    public MessageDtoRes uploadQualifications(File file, Long id){
        List<QualificationDtoReq> qualificationDtoReqs=excelService.leerExcel(file);
        for (QualificationDtoReq q:qualificationDtoReqs) {
            editQualificationsToStudent(q,id);
        }

        return new MessageDtoRes("Notas cargadas exitosamente.");
    }
    public void editQualificationsToStudent(QualificationDtoReq qualificationDtoReq, Long idSubject){
        StudentDtoRes studentDtoRes=this.studentService.getStudentById(qualificationDtoReq.getStudent());
        SubjectDtoRes subjectDtoRes=this.subjectService.getSubjectById(idSubject);
        RegistrationDtoRes registrationDtoRes=getRegistration(studentDtoRes,subjectDtoRes);

            this.registrationRepository.editQualifications(
                    registrationDtoRes.getId(),
                    qualificationDtoReq.getQuialification1(),
                    qualificationDtoReq.getQuialification2(),
                    qualificationDtoReq.getQuialification3());
    }
    public RegistrationDtoRes getRegistration(StudentDtoRes studentDtoRes,SubjectDtoRes subjectDtoRes){
        Student student=modelMapper.map(studentDtoRes,Student.class);
        Subject subject=modelMapper.map(subjectDtoRes,Subject.class);
        Registration registration=this.registrationRepository.findByStudentAndSubject(student,subject).orElseThrow(
                ()->
                        new ResourceNotFoundException("El estudiante con el id "+student.getId()+" no esta inscrito en la materia"+subject.getId())
        );
        return modelMapper.map(registration,RegistrationDtoRes.class);
    }
    public byte[] exportExcel(Long id) throws IOException {
        return excelService.exportExcel(getRegistrationBySubject(id));
    }
    public byte[] exportPDF(Long id) throws IOException {
        return pdfServices.exportPDF(getRegistrationBySubject(id));
    }
}
