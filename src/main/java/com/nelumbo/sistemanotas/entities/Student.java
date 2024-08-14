package com.nelumbo.sistemanotas.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
}
