package com.github.makijaveli.springbootjwtauth.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class JWTUserDetailsImpl implements UserDetails {

    private String username;

    private Long id;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static JWTUserDetailsImpl build(final Long id, final String userName, final List<String> roles) {
        List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JWTUserDetailsImpl(userName, id, null, authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
