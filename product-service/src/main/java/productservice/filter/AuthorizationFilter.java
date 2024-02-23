package productservice.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import productservice.service.AuthService;

import java.io.IOException;

@WebFilter("/products/*")
public class AuthorizationFilter implements Filter {
    private final AuthService authService = new AuthService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");

        if (token != null && authService.isTokenValid(token)) {
            if (isAdminUser(token)) {
                if (httpRequest.getMethod().equals("POST")) {
                    chain.doFilter(request, response);
                } else {
                    chain.doFilter(request, response);
                }
            } else if (isSimpleUser(token)) {
                if (httpRequest.getMethod().equals("GET")) {
                    chain.doFilter(request, response);
                } else if (httpRequest.getMethod().equals("POST")) {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("Permission denied for POST requests");
                } else if (httpRequest.getMethod().equals("PUT")) {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("Permission denied for PUT requests");
                } else if (httpRequest.getMethod().equals("DELETE")) {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("Permission denied for DELETE requests");
                }
            }
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized access");
        }
    }

    private boolean isAdminUser(String token) {
        return token.contains("admin");
    }

    private boolean isSimpleUser(String token) {
        return token.contains("simple");
    }
}
