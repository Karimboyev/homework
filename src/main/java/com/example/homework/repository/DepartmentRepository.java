package com.example.homework.repository;

import com.example.homework.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Page<Department> findAllByDeletedFalse(Pageable pageable);

    Optional<Department> findByIdAndDeletedFalse(Long id);
}
