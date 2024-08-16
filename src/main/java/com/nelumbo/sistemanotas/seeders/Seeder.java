package com.nelumbo.sistemanotas.seeders;

import com.github.javafaker.Faker;
import com.nelumbo.sistemanotas.entities.Registration;
import com.nelumbo.sistemanotas.entities.Student;
import com.nelumbo.sistemanotas.entities.Subject;
import com.nelumbo.sistemanotas.repositories.RegistrationRepository;
import com.nelumbo.sistemanotas.repositories.StudentRepository;
import com.nelumbo.sistemanotas.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class Seeder {
    Faker faker=new Faker();
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final RegistrationRepository registrationRepository;
    Random random=new Random();
    int cantStudents=30;
    int cantSubject=3;
    int maxStudentbySubject=30;
    int minStudentBySubject=10;

    public void seed(){
        List<Student> students=seedStudent();
        List<Subject> subjects=seedSubject();
        for (Subject subject:subjects) {
            seedRegistration(subject,students);
        }
    }

    public List<Student> seedStudent(){
        List<Student> students=new ArrayList<>();
        for(int i =0;i<cantStudents;i++){
            Student student=new Student();
            student.setName(faker.name().fullName());
            this.studentRepository.save(student);
            students.add(student);
        }
        return students;
    }
    public List<Subject> seedSubject(){
        List<Subject> subjects=new ArrayList<>();
        for(int i =0;i<cantSubject;i++){
            Subject student=new Subject();
            student.setName(faker.educator().course().concat(" "+random.nextInt(4)));
            this.subjectRepository.save(student);
            subjects.add(student);
        }
        return subjects;
    }

    public void seedRegistration(Subject subject,List<Student> students){
        List<Student> students1=new ArrayList<>(students);
        int catStudents= random.nextInt((maxStudentbySubject-minStudentBySubject+1))+minStudentBySubject;
        for (int i =0;i<catStudents&&!students1.isEmpty();i++) {
            int indexStudent=random.nextInt(students1.size());
            Student student=students1.remove(indexStudent);
            Registration registration=new Registration();
            registration.setSubject(subject);
            registration.setStudent(student);
            this.registrationRepository.save(registration);
        }
    }

}
