package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Getter @Setter
public class Member {

    private Long id; //db저장시 시퀀스 하나씩 증가
    private String username;
    private int age;
    public Member(){
    } //기본생성자

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
