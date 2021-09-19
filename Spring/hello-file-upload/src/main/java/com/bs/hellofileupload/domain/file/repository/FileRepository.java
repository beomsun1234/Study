package com.bs.hellofileupload.domain.file.repository;
import com.bs.hellofileupload.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {
    Optional<File> findByBoard_Id(Long boardId);
}
