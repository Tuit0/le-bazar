package uz.pdp.lebazar.payload;

import lombok.Data;

@Data
public class JwtResponse {
    private String tokenType="Bearer ";
    private String accessToken;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
