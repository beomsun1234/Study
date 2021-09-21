package com.bs.hellofileupload.dto;

import com.bs.hellofileupload.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinDto {

    private String email;
    private String name;

    @Builder
    public MemberJoinDto(String email, String name){
        this.email =email;
        this.name = name;
    }

    public Member toEntity(){
        return Member.builder().email(email).name(name).build();
    }
}
