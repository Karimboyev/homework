package com.example.homework.repository;

import com.example.homework.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findAllByDeletedFalse(Pageable pageable);

    Optional<Address> findByIdAndDeletedFalse(Long id);
}
