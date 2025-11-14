package com.system.trailer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.trailer.entity.Trailer;
public interface TrailerRepository extends JpaRepository<Trailer, Long> {
    List<Trailer> findByInspectedFalse();
    List<Trailer> findByApprovedTrue();
}
