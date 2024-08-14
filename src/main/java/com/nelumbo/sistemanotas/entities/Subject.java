package com.nelumbo.sistemanotas.entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "subject")
public class Subject {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_subject")
        private Long id;
        @Column(nullable = false,unique = true)
        private String name;
}
