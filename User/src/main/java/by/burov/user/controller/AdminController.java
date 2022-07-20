package by.burov.user.controller;

import by.burov.user.service.JPAUserDetailsManager;
import by.burov.user.controller.utills.token.JwtTokenUtil;
import by.burov.user.core.api.APIConverter;
import by.burov.user.core.api.APIResponse;
import by.burov.user.core.dto.CreateUserDto;
import by.burov.user.core.dto.LoginUserDto;
import by.burov.user.core.dto.ReadUserDto;
import by.burov.user.core.dto.RegistrationUserDto;
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
public class AdminController {

    private final UserService userService;
    private final JPAUserDetailsManager userManager;
    private final PasswordEncoder encoder;

    public AdminController(UserService userService, JPAUserDetailsManager userManager,
                           PasswordEncoder encoder) {
        this.userService = userService;
        this.userManager = userManager;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<ReadUserDto> createUser(@RequestBody CreateUserDto user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<APIResponse<ReadUserDto>> getAllUsers(@RequestParam(defaultValue = "1") int pageNo,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        APIResponse<ReadUserDto> response = new APIConverter<ReadUserDto>().convert(userService.readAll(pageNo - 1, pageSize));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ReadUserDto> getUserByUuid(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.getUserByUuid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    // change to Read concert
    public ResponseEntity<ReadUserDto> updateUser(@PathVariable("uuid") UUID uuid,
                                                  @PathVariable("dt_update") Long dt,
                                                  @RequestBody CreateUserDto user) {
        //Should refactor
        LocalDateTime dtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt), ZoneId.systemDefault());

        return new ResponseEntity<>(userService.update(uuid, dtUpdate, user), HttpStatus.CREATED);
    }
}
