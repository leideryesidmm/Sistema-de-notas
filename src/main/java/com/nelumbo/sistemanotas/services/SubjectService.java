package com.nelumbo.sistemanotas.services;

import com.nelumbo.sistemanotas.entities.Subject;
import com.nelumbo.sistemanotas.exceptions.ResourceNotFoundException;
import com.nelumbo.sistemanotas.repositories.SubjectRepository;
import com.nelumbo.sistemanotas.services.dto.res.SubjectDtoRes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    public Page<SubjectDtoRes> getSubject(Pageable pageable){
        Page<Subject> subjects= this.subjectRepository.findAll(pageable);
        List<SubjectDtoRes> subjectDtoResList=subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectDtoRes.class))
                .toList();
        return new PageImpl<>(subjectDtoResList,pageable,subjects.getTotalElements());
    }
    public SubjectDtoRes getSubjectById(Long id){
        Subject subject=this.subjectRepository.findById(id).orElseThrow(
                ()->
                new ResourceNotFoundException("La materia con el id "+id+" no existe")
        );
        return modelMapper.map(subject,SubjectDtoRes.class);
    }

}
