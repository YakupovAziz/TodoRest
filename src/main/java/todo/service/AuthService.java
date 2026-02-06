package todo.service;

import todo.dto.LoginDto;
import todo.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
