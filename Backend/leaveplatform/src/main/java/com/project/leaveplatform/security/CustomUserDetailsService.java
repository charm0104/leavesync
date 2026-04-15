package com.project.leaveplatform.security;

import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.repo.StakeholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Stakeholder stakeholder = stakeholderRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User.builder()
                .username(stakeholder.getEmail())
                .password(stakeholder.getPassword())
                .roles(stakeholder.getRole().name())
                .build();
    }
}