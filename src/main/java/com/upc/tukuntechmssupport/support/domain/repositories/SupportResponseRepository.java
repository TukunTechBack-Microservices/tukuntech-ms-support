package com.upc.tukuntechmssupport.support.domain.repositories;

import com.upc.tukuntechmssupport.support.domain.entity.SupportResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportResponseRepository extends JpaRepository<SupportResponse, Long> {
}
