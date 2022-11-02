package cl.dcid.sumador.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    public Optional<String> getFromRequest(HttpServletRequest request) {
        final String headerValue = request.getHeader(AUTHORIZATION_HEADER);
        if (headerValue == null || !headerValue.startsWith(HEADER_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(headerValue.replace(HEADER_PREFIX, ""));
    }

    public boolean isValid(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String generate(String username) {
        Date issueDate = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issueDate)
                .setExpiration(new Date((issueDate).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
