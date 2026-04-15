package com.project.leaveplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Optional;

import com.project.leaveplatform.enums.Role;

@Entity
@Table(name = "stakeholder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stakeholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String course;

    public String getCourse() {
    return course;
}

    public static Optional<LeaveApplication> findByEmail(String email2) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }
}