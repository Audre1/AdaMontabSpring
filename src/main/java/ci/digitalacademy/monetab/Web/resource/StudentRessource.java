package ci.digitalacademy.monetab.web.resources;

import ci.digitalacademy.monetab.services.StudentService;
import ci.digitalacademy.monetab.services.dto.RegistrationStudentDTO;
import ci.digitalacademy.monetab.services.dto.ResponseRegisterStudentDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentRessource {

    private final StudentService studentService;


    @ApiResponse(responseCode = "201", description = "Rest, request to save a student")
    @PostMapping

    public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO) {
        log.debug("REST Request to save : {}", studentDTO);
        StudentDTO student = studentService.saveStudent(studentDTO);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable Long id) {
        log.debug("REST Request to update : {}", studentDTO);
        return studentService.update(studentDTO, id);
    }


    @GetMapping
    public List<StudentDTO> getAllStudent() {
        log.info("REST Request to get all Students");
        return studentService.findAll();
    }



    @ApiResponses({@ApiResponse(responseCode = "200", description = "Rest, request to get a student"),
            @ApiResponse(responseCode = "404", description = "Student not found ", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    @Operation(summary = "Get one student", description = "This endpoint allow to found one student")
    public ResponseEntity<?> getStudentById(@Parameter(required = true, description = "ID of student to be founded") @PathVariable Long id) {
        log.info("REST Request to get Student : {}", id);
        Optional<StudentDTO> studentDTO = studentService.findOne(id);
        if (studentDTO.isPresent()) {
            return new ResponseEntity<>(studentDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("student not fond", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        log.info("REST Request to delete Student : {}", id);
        studentService.delete(id);
    }

    @PatchMapping("/{id}")
    public StudentDTO partialUpdate(@RequestBody StudentDTO studentDTO, @PathVariable Long id){
        log.debug("REST request to partial update {} {}", studentDTO, id);
        return studentService.partialupdate(studentDTO, id);
    }

    @PostMapping("/registrer-student")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseRegisterStudentDTO registrerStudent(@RequestBody RegistrationStudentDTO registrationStudentDTO){
        log.debug("REST request to registrer student : {}", registrationStudentDTO);
        return studentService.registerStudent(registrationStudentDTO);
    }

    @GetMapping("/slug/{slug}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request to get student by slug"),
            @ApiResponse(responseCode = "404", description = "student not found", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "get one by slug", description = "this endpoint allow to get one slug")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST Request to get by slug student : {}", slug);
        Optional<StudentDTO> studentDTO = studentService.findOneBySlug(slug);
        if (studentDTO.isPresent()){
            return new ResponseEntity<>(studentDTO.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(studentDTO.get(),HttpStatus.NOT_FOUND);
        }
    }

}