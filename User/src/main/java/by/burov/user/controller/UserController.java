package by.burov.user.controller;

import by.burov.user.controller.utills.token.JwtTokenUtil;
import by.burov.user.core.api.APIConverter;
import by.burov.user.core.api.APIResponse;
import by.burov.user.core.dto.CreateUserDto;
import by.burov.user.core.dto.LoginUserDto;
import by.burov.user.core.dto.ReadUserDto;
import by.burov.user.core.dto.RegistrationUserDto;
import by.burov.user.service.JPAUserDetailsManager;
import by.burov.user.service.UserHolder;
import by.burov.user.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JPAUserDetailsManager userManager;
    private final PasswordEncoder encoder;
    private final UserHolder holder;

    public UserController(UserService userService, JPAUserDetailsManager userManager,
                          PasswordEncoder encoder, UserHolder holder) {
        this.userService = userService;
        this.userManager = userManager;
        this.encoder = encoder;
        this.holder = holder;
    }

    @PostMapping("/registration")
    public ResponseEntity<ReadUserDto> registrationUser(@RequestBody RegistrationUserDto user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);

    }

    @RequestMapping("/login")
    public String login(@RequestBody LoginUserDto loginUserDto) {
        UserDetails details= userManager.loadUserByUsername(loginUserDto.getMail());

        if (!encoder.matches(loginUserDto.getPassword(), details.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        return JwtTokenUtil.generateAccessToken(details);
    }

    @GetMapping("/me")
    public ResponseEntity<ReadUserDto> getDetails() {
        return new ResponseEntity<>(holder.getUser(), HttpStatus.OK);
    }




}
