package com.bs.hellodockercompose;

import com.bs.hellodockercompose.domain.Board;
import com.bs.hellodockercompose.domain.BoardRepository;
import com.bs.hellodockercompose.domain.BoardSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class BoardController {
    private final BoardRepository boardRepository;

    @PostMapping("/board")
    public Long saveBoard(@RequestBody BoardSaveDto boardSaveDto){
        log.info("title={}", boardSaveDto.getTitle());
        return boardRepository.save(boardSaveDto.toEntity()).getId();
    }

    @GetMapping("/board")
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

}
