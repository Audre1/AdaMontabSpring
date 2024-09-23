package ci.digitalacademy.monetab.services.dto;

import ci.digitalacademy.monetab.models.Student;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class registationStudentDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String ville;
    private String email;
    private String genre;
    private Student student;
    private String matricule;
    private LocalDate datenaiss;
    private Integer age;

}
