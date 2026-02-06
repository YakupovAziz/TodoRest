package todo.service;

import todo.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);
}
