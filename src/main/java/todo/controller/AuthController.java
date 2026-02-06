package todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todo.dto.RegisterDto;
import todo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto) {
        //return ResponseEntity.ok(authService.register(registerDto));
        String response = authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
