package com.bs.hellofileupload.controller;
import com.bs.hellofileupload.domain.member.Member;
import com.bs.hellofileupload.domain.member.repository.MemberRepository;
import com.bs.hellofileupload.dto.MemberInfoDto;
import com.bs.hellofileupload.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Transactional(readOnly = true)
    @GetMapping("v1/membre/{id}")
    public ResponseEntity<MemberInfoDto> findOneById(@PathVariable Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없습니다!"));
        return new ResponseEntity<>(MemberInfoDto.builder().entity(member).build(), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("v1/membre/{id}")
    public ResponseEntity deleteMember(@PathVariable Long id){
        memberRepository.deleteById(id);
        return new ResponseEntity("delete success",HttpStatus.OK);
    }
}
