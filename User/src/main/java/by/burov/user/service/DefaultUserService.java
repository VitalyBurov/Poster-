package by.burov.user.service;

import by.burov.user.core.enums.UserRole;
import by.burov.user.core.enums.UserStatus;
import by.burov.user.repository.UserRepository;
import by.burov.user.repository.entity.Authority;
import by.burov.user.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class DefaultUserService implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

      if (!userRepository.findByMail("admin@gmail.com").isPresent()) {

            LocalDateTime now = LocalDateTime.now();

            User admin = new User();
            admin.setUuid(UUID.randomUUID());
            admin.setDtCreate(now);
            admin.setDtUpdate(now.minusNanos(now.getNano() % 1_000_000));
            admin.setMail("admin@gmail.com");
            admin.setNick("admin");

          admin.setAuthorities(Set.of(new Authority(2L, UserRole.ROLE_ADMIN), new Authority(1L, UserRole.ROLE_USER)));
            admin.setStatus(UserStatus.ACTIVATED);
            admin.setPassword(passwordEncoder.encode("admin"));

            userRepository.save(admin);
        }
    }
}

