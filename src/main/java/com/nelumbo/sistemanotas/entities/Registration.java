package com.nelumbo.sistemanotas.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registration")
    private Long id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id_student", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id_subject", nullable = false)
    private Subject subject;
    private Double qualification1;
    private Double qualification2;
    private Double qualification3;
}
