package scc.vigilancia.comunitaria.configs;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import scc.vigilancia.comunitaria.exceptions.InvalidInputException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(rawPassword.toString().getBytes());
            return new String(Hex.encode(digest));
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidInputException("Falha ao realizar encode de senha");
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodedRawPassword = encode(rawPassword);
        return encodedRawPassword.equals(encodedPassword);
    }
}
