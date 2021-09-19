package com.bs.hellofileupload.domain.file.repository;

import com.bs.hellofileupload.domain.board.Board;
import com.bs.hellofileupload.domain.file.File;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    @Transactional
    void 파일저장테스트(){
        //given
        Board board = Board.builder().title("test").content("test").author("test").build();
        File file = File.builder().board(board).filePath("images/eqwadds").originalName("qkrqjatjs").build();
        //when
        File save = fileRepository.save(file);
        //then
        Assertions.assertThat(save.getFilePath()).isEqualTo(file.getFilePath());

    }

}