package hello.hellospringsecurity.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum AuthenticationProvider {
    LOCAL, GOOGLE;
}
