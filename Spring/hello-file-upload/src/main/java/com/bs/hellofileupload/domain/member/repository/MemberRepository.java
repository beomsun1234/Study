package com.bs.hellofileupload.domain.member.repository;

import com.bs.hellofileupload.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
