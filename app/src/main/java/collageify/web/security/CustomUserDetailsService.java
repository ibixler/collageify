package collageify.web.security;

import collageify.web.entity.User;
import collageify.web.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    public CustomUserDetailsService() {
    }
    private UserRepository users;
    @Autowired
    public CustomUserDetailsService(UserRepository users){
        this.users = users;
    }


    @Override
    public UserDetails loadUserByUsername(String usernamerOrEmail) throws UsernameNotFoundException {
        User user = users.findByUsernameOrEmail(usernamerOrEmail, usernamerOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found under:" + usernamerOrEmail));

        Set<GrantedAuthority> authorities = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);

    }
}
