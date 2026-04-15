package com.project.leaveplatform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.enums.Role;

import java.util.List;
import java.util.Optional;

public interface StakeholderRepository extends JpaRepository<Stakeholder, Long> {
    Optional<Stakeholder> findByEmail(String email);
    List<Stakeholder> findByRole(Role role);
    List<Stakeholder> findByCourse(String course);
}
