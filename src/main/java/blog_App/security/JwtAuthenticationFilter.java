package blog_App.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader= request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
             jwtToken=tokenHeader.substring(7);
             try {
                 username=this.jwtUtil.extractUsername(jwtToken);
             }catch (Exception e){
                 e.printStackTrace();
             }
            UserDetails userDetails=userDetailService.loadUserByUsername(username);
             if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                         UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
             }else {
                 System.out.println("Token is not valid");
             }
        }
        filterChain.doFilter(request,response);
    }
}
