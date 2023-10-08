package scc.vigilancia.comunitaria.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.LoginRequest;
import scc.vigilancia.comunitaria.dto.NewUserRequest;
import scc.vigilancia.comunitaria.dto.TokenResponse;
import scc.vigilancia.comunitaria.exceptions.InvalidInputException;
import scc.vigilancia.comunitaria.models.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {

    @Value("${api.jwt.expiration}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    @Value("${api.jwt.issuer}")
    private String issuer;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public TokenResponse authenticate(LoginRequest request){
        if(Strings.isNullOrEmpty(request.getEmail()) || Strings.isNullOrEmpty(request.getPassword())) {
            throw new InvalidInputException("Email e senha devem estar preenchidos");
        }
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String userName = userService.findUserByEmail(request.getEmail()).getName();
        String token = generateToken(authenticate, userName);

        return new TokenResponse(token);
    }


    private String generateToken(Authentication authentication, String name) {
        User userToLogIn = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expireDate = new Date(today.getTime() + Long.parseLong(expiration));

         String token = JWT.create()
                .withClaim("role", userToLogIn.getPermission().name())
                .withClaim("username", name)
                .withSubject(userToLogIn.getEmail())
                .withIssuer(issuer)
                .withExpiresAt(expireDate)
                .sign(this.createAlgorithm());

        log.info("[ AuthenticationService ] - Generating token for user {}", userToLogIn.getEmail());

        log.info("[ AuthenticationService ] - Saving access log.");

        return token;
    }

    private Algorithm createAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public boolean isTokenValid(String token){
        try {
            if (token == null)
                return false;

            JWT.require(this.createAlgorithm()).withIssuer(issuer).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String gatherUserEmailFromToken(String token) {
        String subject = JWT.require(this.createAlgorithm()).withIssuer(issuer).build().verify(token).getSubject();

        return subject;

    }


}
