package com.example.pasir_nizio_patryk.repository;

import com.example.pasir_nizio_patryk.model.Group;
import com.example.pasir_nizio_patryk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMemberships_User(User user);
}
