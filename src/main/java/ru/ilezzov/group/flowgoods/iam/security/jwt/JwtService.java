package ru.ilezzov.group.flowgoods.iam.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;
import ru.ilezzov.group.flowgoods.iam.exception.jwt.JwtExpiredException;
import ru.ilezzov.group.flowgoods.iam.exception.jwt.JwtInvalidSignatureException;
import ru.ilezzov.group.flowgoods.iam.exception.jwt.JwtMalformedException;
import ru.ilezzov.group.flowgoods.iam.exception.jwt.JwtSubjectExtractionFailedException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtService(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.secret().getBytes(StandardCharsets.UTF_8)
        );

        this.jwtParser = Jwts.parser()
                .verifyWith(this.secretKey)
                .build();
    }

    public String generate(final UUID uuid) {
        return this.generate(uuid, Collections.emptyMap());
    }

    public String generate(final UUID uuid, final Map<String, Object> claims) {
        final long nowMillis = System.currentTimeMillis();
        final Date now = new Date(nowMillis);
        final Date exp = new Date(nowMillis + this.jwtProperties.expiration());

        return Jwts.builder()
                .claims(claims)
                .subject(uuid.toString())
                .issuedAt(now)
                .expiration(exp)
                .signWith(this.secretKey)
                .compact();
    }

    public UUID validateAndExtractUuid(final String token) {
        final String subject = this.extractClaim(token, Claims::getSubject);

        try {
            return UUID.fromString(subject);
        } catch (final IllegalArgumentException e) {
            throw new JwtSubjectExtractionFailedException();
        }
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public <T> T extractClaimByKey(final String token, final String key, final Class<T> clazz) {
        return extractClaim(token, claims -> claims.get(key, clazz));
    }

    private Claims extractAllClaims(final String token) {
        try {
            return this.jwtParser
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (final ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (final SignatureException e) {
            throw new JwtInvalidSignatureException();
        } catch (final MalformedJwtException e) {
            throw new JwtMalformedException();
        } catch (final JwtException e) {
            throw new ru.ilezzov.group.flowgoods.iam.exception.jwt.JwtException(token);
        }
    }
}
