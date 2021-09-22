package com.bs.hellofileupload.controller;

import com.bs.hellofileupload.domain.member.repository.MemberRepository;
import com.bs.hellofileupload.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @Transactional
    @PostMapping("v1/member")
    public Long save(@RequestBody MemberJoinDto memberJoinDto){
        return memberRepository.save(memberJoinDto.toEntity()).getId();
    }

}
