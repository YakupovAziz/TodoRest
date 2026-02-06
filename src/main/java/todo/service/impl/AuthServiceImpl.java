package todo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todo.dto.RegisterDto;
import todo.entity.Role;
import todo.entity.User;
import todo.exception.TodoAPIException;
import todo.repository.RoleRepository;
import todo.repository.UserRepository;
import todo.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername()) || userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username or email already exists");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
