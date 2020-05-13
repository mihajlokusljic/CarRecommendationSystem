package kusljic.mihajlo.sbnz.spring.backend.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private final String SECRET_KEY = "8A0F6CBDC92B6690B4EE6621927025C66567264AC9D4B1C710394A92F609E47A";
	private final byte[] SECRET_KEY_BASE64 = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	
	// expiration time in milliseconds (token will expire 12 hours after issuing)
	private final long EXPIRES_IN = 12 * 60 * 60 * 1000;
	
	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	
	private String AUTH_HEADER = "Authorization";
	
	public String generateToken(UserDetails userDetails) {
		// claims contain users role
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(userDetails.getUsername())
        		.setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY_BASE64)
                .compact();
    }
	
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	
	public Boolean isTokenExpired(String jwt) {
		return getExpirationDateFromToken(jwt).before(new Date());
	}
	
	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET_KEY_BASE64)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		Boolean expired = isTokenExpired(token);
		
		return (username != null && username.equals(userDetails.getUsername())
				&& !expired);
	}
	
	// Functions for getting JWT token out of HTTP request

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}
}
