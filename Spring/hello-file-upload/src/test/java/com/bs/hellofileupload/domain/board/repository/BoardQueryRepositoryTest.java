package com.bs.hellofileupload.domain.board.repository;

import com.bs.hellofileupload.domain.board.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardQueryRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardQueryRepository boardQueryRepository;
    @DisplayName("board엔티티 db저장")
    @Test
    @Transactional
    void test01(){
        //given
        Board board = Board.builder().title("test").content("test").author("test").build();
        //when
        Board savedBoard = boardRepository.save(board);
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow();
        //then
        Assertions.assertThat(savedBoard.getId()).isEqualTo(findBoard.getId());
    }
    @DisplayName("fetch조인으로 n+1 문제 해결")
    @Test
    @Transactional(readOnly = true)
    void test02(){
        //when
        List<Board> boards = boardQueryRepository.findAll();
        //then
        Assertions.assertThat(boards.get(0).getId()).isEqualTo(1L);
    }
    @DisplayName("fetch조인안할시 n+1발생")
    @Test
    @Transactional(readOnly = true)
    void test03(){
        //when
        List<Board> boards = boardRepository.findAll();
        //then
        Assertions.assertThat(boards.get(0).getId()).isEqualTo(1L);
    }
    @DisplayName("findBy id, 기본쿼리")
    @Test
    @Transactional(readOnly = true)
    void test04(){
        Board board = boardRepository.findById(1L).orElseThrow();

        Assertions.assertThat(board.getId()).isEqualTo(1L);
    }
    @DisplayName("findBy id, querydsl(fetch조인 안할시 n+1 발생한다)")
    @Test
    @Transactional(readOnly = true)
    void test05() {
        Board board = boardQueryRepository.findById(1L);

        Assertions.assertThat(board.getId()).isEqualTo(1L);
    }

    @DisplayName("findBy id, querydsl(fetch조인으로 n+1 해결)")
    @Test
    @Transactional(readOnly = true)
    void test06() {
        Board board = boardQueryRepository.findById(1L);

        Assertions.assertThat(board.getId()).isEqualTo(1L);
    }

}