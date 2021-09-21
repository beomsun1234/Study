package com.bs.hellofileupload.dto;

import com.bs.hellofileupload.domain.board.Board;
import com.bs.hellofileupload.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@Getter
@Setter//setter 사용이유는 이미지 파일을 받기 위해
public class BoardRequestDto {
    private Long memberId;
    private String title;
    private String content;
    private String author;
    private MultipartFile file; //이미지저장

    @Builder
    public BoardRequestDto(Long memberId, String title,String content, String author, MultipartFile file){
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.file = file;
    }

    public Board toEntity(Member member){
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .member(member)
                .build();
    }
}
