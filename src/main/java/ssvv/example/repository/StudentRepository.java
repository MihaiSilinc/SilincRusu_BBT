package ssvv.example.repository;

import ssvv.example.domain.Student;
import ssvv.example.validation.*;

public class StudentRepository extends AbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}

