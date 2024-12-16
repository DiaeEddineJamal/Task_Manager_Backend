package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Project;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    // Existing query
    List<Project> findByBudgetGreaterThan(int budget);


}