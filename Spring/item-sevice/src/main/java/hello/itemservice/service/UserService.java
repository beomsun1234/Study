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
//    public User save(User user) {
//        String encodePassword=passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodePassword);
//        user.setEnabled(true);
//        return userRepository.save(user);
//    }
//
//
//}
