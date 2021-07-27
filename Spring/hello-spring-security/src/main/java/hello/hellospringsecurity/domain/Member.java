package hello.hellospringsecurity.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String name;


    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authenticationProvider;

    @Builder
    public Member(Long id, String email, String name, AuthenticationProvider authenticationProvider){
        this.id = id;
        this.email =email;
        this.name = name;
        this.authenticationProvider = authenticationProvider;
    }


}
