package com.bs.hellofileupload.service;
import com.bs.hellofileupload.domain.board.Board;
import com.bs.hellofileupload.domain.board.repository.BoardQueryRepository;
import com.bs.hellofileupload.domain.board.repository.BoardRepository;
import com.bs.hellofileupload.domain.member.Member;
import com.bs.hellofileupload.domain.member.repository.MemberRepository;
import com.bs.hellofileupload.dto.BoardRequestDto;
import com.bs.hellofileupload.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final BoardQueryRepository boardQueryRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public Long save(BoardRequestDto boardRequestDto) throws IOException {
        Member member = memberRepository.findById(boardRequestDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("맴버가 없습니다"));
        Board savedBoard = boardRepository.save(boardRequestDto.toEntity(member));
        if(boardRequestDto.getFile() !=null){
            Long saveFilId = fileService.save(boardRequestDto.getFile(), savedBoard);
            log.info("파일저장성공={}",saveFilId);
        }
        return savedBoard.getId();
    }

    @Transactional
    public ResponseEntity<String> delete(Long boardId){
        boardRepository.deleteById(boardId);
        return new ResponseEntity<>("sucess",HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BoardResponseDto>findOne(Long boardId){
        return new ResponseEntity<>(BoardResponseDto.builder().board(boardQueryRepository.findById(boardId)).build(),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<List<BoardResponseDto>> findAll(){
        return new ResponseEntity<>(boardQueryRepository.findAll().stream().map(board -> BoardResponseDto.builder().board(board).build()).collect(Collectors.toList()),HttpStatus.OK);
    }


}
