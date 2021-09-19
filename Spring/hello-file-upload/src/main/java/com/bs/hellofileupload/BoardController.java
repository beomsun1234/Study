package com.bs.hellofileupload;
import com.bs.hellofileupload.domain.board.Board;
import com.bs.hellofileupload.domain.board.repository.BoardQueryRepository;
import com.bs.hellofileupload.domain.board.repository.BoardRepository;
import com.bs.hellofileupload.dto.BoardRequestDto;
import com.bs.hellofileupload.dto.BoardResponseDto;
import com.bs.hellofileupload.service.BoardService;
import com.bs.hellofileupload.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final FileService fileService;
    private final BoardQueryRepository boardQueryRepository;
    @PostMapping("v1/post")
    public Long save(BoardRequestDto boardRequestDto) throws IOException {
        return boardService.save(boardRequestDto);
    }
    //---------
    @Transactional(readOnly = true)
    @GetMapping("v1/post")
    public List<BoardResponseDto> findAll(){
        return boardQueryRepository.findAll().stream().map(board -> BoardResponseDto.builder().board(board).build()).collect(Collectors.toList());
    }
    @GetMapping("v2/post")
    public ResponseEntity<List<BoardResponseDto>> findAllV2(){
        return boardService.findAll();
    }
    //----------------------
    @Transactional(readOnly = true)
    @GetMapping("/v1/post/{id}")
    public BoardResponseDto findOne(@PathVariable Long id){
        return BoardResponseDto.builder().board(boardQueryRepository.findById(id)).build();
    }
    @Transactional
    @PutMapping("/v1/post/{id}")
    public Long updateBoard(@PathVariable Long id,BoardRequestDto boardRequestDto) throws IOException {
        Board board = boardQueryRepository.findById(id);
        if(!boardRequestDto.getFile().isEmpty()){
            Long savedFile = fileService.saveOrUpdate(boardRequestDto.getFile(), board);
            log.info("파일저장성공={}",savedFile);
        }
        board.update(boardRequestDto.getTitle(), boardRequestDto.getContent());
        return board.getId();
    }

    @Transactional
    @DeleteMapping("/v1/post/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id){
        return boardService.delete(id);
    }
}
