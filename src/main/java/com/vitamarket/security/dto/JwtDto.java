package com.vitamarket.security.dto;

import org.springframework.security.core.GrantedAuthority;
import lombok.*;
import java.util.Collection;

@Getter
@Setter
public class JwtDto {
	
	@Getter(value=AccessLevel.PRIVATE)
	@Setter(value=AccessLevel.PRIVATE)
    private String bearer = "Bearer";
	
	private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
	
    public JwtDto(String token, String username, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.username = username;
		this.authorities = authorities;
	}

}
