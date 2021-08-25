package com.example.homework.repository;

import com.example.homework.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Page<Company> findAllByDeletedFalse(Pageable pageable);

    Optional<Company> findByIdAndDeletedFalse(Long id);
}
