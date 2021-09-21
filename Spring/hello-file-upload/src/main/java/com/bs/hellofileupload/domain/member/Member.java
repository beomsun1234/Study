package com.bs.hellofileupload.domain.member;

import com.bs.hellofileupload.domain.BaseTimeEntity;
import com.bs.hellofileupload.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    public Member(String email, String name){
        this.email = email;
        this.name = name;
    }
}
