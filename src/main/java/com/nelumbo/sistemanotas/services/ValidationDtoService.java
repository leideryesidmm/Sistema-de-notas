package com.nelumbo.sistemanotas.services;

import com.nelumbo.sistemanotas.exceptions.DatosInvalidosException;
import com.nelumbo.sistemanotas.services.dto.req.QualificationDtoReq;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidationDtoService {
    private final Validator validator;
    public void validateDto(QualificationDtoReq dto){
        Set<ConstraintViolation<QualificationDtoReq>> violations=validator.validate(dto);
        if(!violations.isEmpty()){
            String errores=violations.stream().map(
                    violation ->String.format("Propiedad: '%s', Valor invalido:'%s', Mensaje: '%s'",
                            violation.getPropertyPath(),violation.getInvalidValue(),violation.getMessage())
            ).collect(Collectors.joining(", "));
            throw new DatosInvalidosException("Errores de validación: "+errores);
        }
    }
}
