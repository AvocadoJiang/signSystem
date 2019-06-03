package com.isl.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isl.entity.Academy;

public interface AcademyRepository extends JpaRepository<Academy,String> {

}
