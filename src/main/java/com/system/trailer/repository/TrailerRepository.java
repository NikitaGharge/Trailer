package com.system.trailer.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.system.trailer.dto.TrailerDTO;
import com.system.trailer.dto.TrailerHistoryDTO;
import com.system.trailer.entity.Trailer;
public interface TrailerRepository extends JpaRepository<Trailer, Long> {
    List<Trailer> findByInspectedFalse();
    List<Trailer> findByApprovedTrue();
}
