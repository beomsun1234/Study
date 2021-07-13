//package hello.itemservice.service;
//
//import hello.itemservice.domain.User;
//import hello.itemservice.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    public User registerNewUserAccount(User accountDto) {
//        User user = new User();
//        user.setUsername(accountDto.getUsername());
//        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//        return userRepository.save(user);
//    }
//}
