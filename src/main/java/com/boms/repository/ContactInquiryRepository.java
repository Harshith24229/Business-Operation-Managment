package com.boms.repository;

import com.boms.entity.ContactInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {
    List<ContactInquiry> findByOrderByCreatedDateDesc();
    List<ContactInquiry> findByStatus(ContactInquiry.Status status);
}