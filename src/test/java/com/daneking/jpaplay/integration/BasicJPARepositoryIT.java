package com.daneking.jpaplay.integration;

import com.daneking.jpaplay.common.Address;
import com.daneking.jpaplay.student.Student;
import com.daneking.jpaplay.student.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@Transactional
public class BasicJPARepositoryIT {

    @Resource
    private StudentRepository studentRepository;

    private Address address1;
    private Address address2;
    private Address address3;


    @Before
    public void setup(){
        Student student = new Student(1, "john");
        address1 = buildAddress("456 Main St", "Columbus");
        student.setAddress(Collections.singletonList(address1));
        studentRepository.save(student);
        student.setId(2);
        student.setName("jane");
        address2 = buildAddress("456 Main St", "Columbus");
        student.setAddress(Collections.singletonList(address2));
        studentRepository.save(student);
        student.setId(3);
        student.setName("jimmy");
        address3 = buildAddress("16 Water St", "Dublin");
        student.setAddress(Collections.singletonList(address3));
        studentRepository.save(student);
    }

    private Address buildAddress(String street, String city) {
        Address address = new Address();
        address.setStreet1(street);
        address.setCity(city);
        return address;
    }

    @Test
    public void whenFindByIdGetStudent() {
        Optional<Student> foundStudent = studentRepository.findStudentByName("john");
        assertEquals("john", safeGetName(foundStudent));
    }

    @Test
    public void whenFindByNameGetStudent() {
        Optional<Student> foundStudent = studentRepository.findStudentByName("john");
        assertEquals("john", safeGetName(foundStudent));
    }
    @Test
    public void whenFindByAddress() {
        List<Student> foundStudents = studentRepository.findAllByAddressCity("Columbus");
        List<Address> addresses=foundStudents
                .stream()
                .map(Student::getAddress)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        assertThat(addresses, hasItems(address1,address2));
        assertThat(addresses, not(hasItems(address3)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    //The purpose is to wrap the optional to eliminate the same code
    private String safeGetName(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<Student> foundStudent) {
        return foundStudent.map(Student::getName).orElse("");
    }


}
