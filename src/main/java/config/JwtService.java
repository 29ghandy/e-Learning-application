// ────────── src/main/java/config/JwtService.java ──────────
package config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    /* 256-bit (32-byte) base64-encoded key. KEEP IT SAFE! */
    private static final String SECRET =
            "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;   // 24h

    /* -------------------------------------------------------------- */

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        try {
            return user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;   // signature bad / token malformed / expired
        }
    }

    /* -------------------------------------------------------------- */
    /*  Internal helpers                                              */
    /* -------------------------------------------------------------- */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(allClaims(token));
    }

    private Claims allClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);   // SECRET is Base64 string
        return Keys.hmacShaKeyFor(keyBytes);
    }
}