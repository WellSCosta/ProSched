package com.wellscosta.ProSched.security;

import com.wellscosta.ProSched.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Classe de configuração do Token JWT
 */
public class JwtService {

    /**
     * Chave de segurança para validar os tokens criados
     * TODO salvar a chave em um lugar mais seguro
     */
    private static final String SECRET_KEY = "prodshed-secret-key-123";
    private static final long EXPIRATION_TIME = 86400000;

    public String gerarToken(Usuario user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extrai username do token
     * @param token
     * @return username
     */
    public String extractUsername (String token) {
        return extractClaim(token, Jwts.parser().setSigningKey(SECRET_KEY)::parseClaimsJws)
                .getBody()
                .getSubject();
    }

    private <T> T extractClaim(String token, Function<String, T> claimsResolver) {
        return claimsResolver.apply(token);
    }

    /**
     * Verifica se o token recebido é valido
     * @param token
     * @param usuario
     * @return true ou false
     */
    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getUsername()));
    }
}
