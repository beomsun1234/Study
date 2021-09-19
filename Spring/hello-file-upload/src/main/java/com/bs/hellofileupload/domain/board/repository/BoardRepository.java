package com.bs.hellofileupload.domain.board.repository;

import com.bs.hellofileupload.domain.board.Board;
import com.bs.hellofileupload.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
