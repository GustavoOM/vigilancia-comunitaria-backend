package scc.vigilancia.comunitaria.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.services.AuthenticationService;
import scc.vigilancia.comunitaria.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthenticationFilter(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromHeader(request);
            if (authenticationService.isTokenValid(token)) {
                setUserContext(token);
            }

            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            setUnauthorizedResponse(response, e);
        }
    }

    private void setUnauthorizedResponse(HttpServletResponse response, EntityNotFoundException e) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        ApiResponse errorMessage = ApiResponse.builder()
                .code("USUARIO_NAO_ENCONTRADO")
                .message(message)
                .build();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(errorMessage));
    }

    private void setUserContext(String token) {
        String userEmail = authenticationService.gatherUserEmailFromToken(token);
        User user = userService.findUserByEmail(userEmail);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities())
                );
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = null;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
        return token;
    }
}
