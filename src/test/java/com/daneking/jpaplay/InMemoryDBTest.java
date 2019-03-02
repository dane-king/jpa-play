package com.daneking.jpaplay;

import com.daneking.jpaplay.config.StudentJpaConfig;
import com.daneking.jpaplay.student.Student;
import com.daneking.jpaplay.student.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@Transactional
public class InMemoryDBTest {

    @Resource
    private StudentRepository studentRepository;



    @Test
    public void givenStudent_whenSave_thenGetOk() {
        Student student = new Student(1, "john");
        studentRepository.save(student);

        Optional<Student> student2 = studentRepository.findById(1L);
        assertEquals("john", student2.get().getName());
    }
}
