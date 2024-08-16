package com.nelumbo.sistemanotas.services.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QualificationDtoReq {
    @NotNull
    private Long student;
    @NotNull
    @Min(value = 0,message = "La calificación debe ser igual o mayor a 0 y menor o igual que 5")
    @Max(value = 5,message = "La calificación debe ser igual o mayor a 0 y menor o igual que 5")
    private Double quialification1;
    @NotNull
    @Min(value = 0,message = "La calificación debe ser igual o mayor a 0 y menor o igual que 5")
    @Max(value = 5,message = "La calificación debe ser igual o mayor a 0 y menor o igual que 5")
    private Double quialification2;
    @NotNull
    @Min(value = 0,message = "La calificación debe ser igual o mayor a 0 y menor o igual que 5")
    @Max(value = 5,message = "La calificación debe ser igual o mayor a 0 y menor o igual que 5")
    private Double quialification3;
}
