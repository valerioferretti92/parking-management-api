package com.valerioferretti.parking.config;

import lombok.Data;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    public byte[] getJwtSecretRawBytes() throws DecoderException {
        return Hex.decodeHex(jwtSecret);
    }
}
