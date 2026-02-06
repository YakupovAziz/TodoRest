package todo.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import todo.entity.Role;
import todo.entity.User;
import todo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username or mail: "+usernameOrEmail));

//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
//            authorities.add(simpleGrantedAuthority);
//        }

        Set<GrantedAuthority> authorities = user.getRoles()
                .stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toCollection(HashSet::new));

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities
        );
    }

}
