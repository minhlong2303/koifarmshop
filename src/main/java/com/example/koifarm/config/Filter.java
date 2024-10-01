package com.example.koifarm.config;

import com.example.koifarm.entity.User;
import com.example.koifarm.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register"         //ai cung co quyen truy cap
    );

    public boolean checkIsPublicAPI(String uri) {
        //uri: api/register
        //neu gap api trong list o tren -> cho phep truy cap ->true
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //check token -> false
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException{
        // cho phep request co the truy cap duoc vo controller
        //filterChain.doFilter(request, response);

        //check xem api ng dung yeu cau co phai public api khong
        boolean isPublicAPI = checkIsPublicAPI(request.getRequestURI());

        if (isPublicAPI){
            filterChain.doFilter(request, response);
        } else {
            String token = getToken(request);
            if (token == null){
                //khong duoc phep truy cap
                resolver.resolveException(request, response, null, new AuthException("Empty token!"));
                return;
            }


            //-> co token
            //check xem token dang co dung hay khong-> lay thong tin user tu Token
            User user;
            try{
                user = tokenService.getUserByToken(token);
            } catch (ExpiredJwtException e){
                // response token het han
                resolver.resolveException(request, response, null, new AuthException("Expired token!"));
                return;
            } catch (MalformedJwtException malformedJwtException){
                //response token sai
                resolver.resolveException(request, response, null, new AuthException("Invalid token!"));
                return;
            }

            //-> token chuan
            //cho phep truy cap
            //luu lai thong tin user
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    token,
                    user.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //token ok, cho vao
            filterChain.doFilter(request, response);
        }
    }

    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.substring(7);
    }
}
