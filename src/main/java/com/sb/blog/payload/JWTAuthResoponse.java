package com.sb.blog.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthResoponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthResoponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
