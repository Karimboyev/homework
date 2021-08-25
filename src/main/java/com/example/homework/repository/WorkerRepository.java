package com.example.homework.repository;

import com.example.homework.domain.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Page<Worker> findAllByDeletedFalse(Pageable pageable);

    Optional<Worker> findByIdAndDeletedFalse(Long id);

}
