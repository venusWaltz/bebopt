package com.bebopt.app.data.service;

import com.bebopt.app.data.entity.SampleBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SampleBookRepository extends JpaRepository<SampleBook, Long>, JpaSpecificationExecutor<SampleBook> {

}
