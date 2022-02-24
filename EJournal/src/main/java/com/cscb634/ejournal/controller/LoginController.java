//package com.cscb634.ejournal.controller;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.cscb634.ejournal.constants.Constants;
//import com.cscb634.ejournal.model.Client;
//import com.cscb634.ejournal.model.Director;
//import com.cscb634.ejournal.model.Parent;
//import com.cscb634.ejournal.model.Roles;
//import com.cscb634.ejournal.model.Student;
//import com.cscb634.ejournal.model.Teacher;
//import com.cscb634.ejournal.repository.DirectorRepository;
//import com.cscb634.ejournal.repository.ParentRepository;
//import com.cscb634.ejournal.repository.StudentRepository;
//import com.cscb634.ejournal.repository.TeacherRepository;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.AllArgsConstructor;
//
//@Controller
//@RequestMapping(Constants.LOGIN_API)
//@AllArgsConstructor
//public class LoginController {
//
//    private final ParentRepository parentRepository;
//    private final StudentRepository studentRepository;
//    private final TeacherRepository teacherRepository;
//    private final DirectorRepository directorRepository;
//
//    @PostMapping(value = "/register-user", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Client> registerPerson(@RequestBody Client person, HttpServletRequest request) {
//        if (existById(person)) {
//            throw new IllegalArgumentException("The provided client already exist.");
//        }
//        createEntity(person);
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication =
//                new TestingAuthenticationToken("username", "password", "ROLE_" + person.getRole());
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
//        return ResponseEntity.ok().body(person);
//    }
//
//    @PostMapping(value = "/login-user", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpStatus> loginPerson(@RequestBody Client person, HttpServletRequest request) {
//        if (!existById(person)) {
//            throw new IllegalArgumentException("The client is not registered!");
//        }
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication =
//                new TestingAuthenticationToken("username", "password", "ROLE_" + person.getRole());
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
//        return ResponseEntity.of(Optional.of(HttpStatus.OK));
//    }
//
//    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpStatus> logout(HttpServletRequest request) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(null);
//        SecurityContextHolder.setContext(context);
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
//        return ResponseEntity.of(Optional.of(HttpStatus.OK));
//    }
//
//    private boolean existById(Client client) {
//        UUID id = client.getId();
//        return studentRepository.existsById(id) || parentRepository.existsById(id)
//               || directorRepository.existsById(id) || teacherRepository.existsById(id);
//    }
//
//    private void createEntity(Client client) {
//        Roles role = client.getRole();
//        if (role.equals(Roles.STUDENT)) {
//            Student student = createStudent(client);
//            studentRepository.saveAndFlush(student);
//        } else if (role.equals(Roles.TEACHER)) {
//            Teacher teacher = createTeacher(client);
//            teacherRepository.saveAndFlush(teacher);
//        } else if (role.equals(Roles.DIRECTOR)) {
//            Director director = createDirector(client);
//            directorRepository.saveAndFlush(director);
//        } else if (role.equals(Roles.PARENT)) {
//            Parent parent = createParent(client);
//            parentRepository.saveAndFlush(parent);
//        }
//    }
//
//    private Student createStudent(Client client) {
//        return Student.builder()
//                .address(client.getAddress())
//                .age(client.getAge())
//                .id(client.getId())
//                .firstName(client.getFirstName())
//                .lastName(client.getLastName())
//                .build();
//    }
//
//    private Teacher createTeacher(Client client) {
//        return Teacher.builder()
//                .address(client.getAddress())
//                .age(client.getAge())
//                .id(client.getId())
//                .firstName(client.getFirstName())
//                .lastName(client.getLastName())
//                .build();
//    }
//
//    private Director createDirector(Client client) {
//        return Director.builder()
//                .address(client.getAddress())
//                .age(client.getAge())
//                .id(client.getId())
//                .firstName(client.getFirstName())
//                .lastName(client.getLastName())
//                .build();
//    }
//
//    private Parent createParent(Client client) {
//        return Parent.builder()
//                .address(client.getAddress())
//                .age(client.getAge())
//                .id(client.getId())
//                .firstName(client.getFirstName())
//                .lastName(client.getLastName())
//                .build();
//    }
//}