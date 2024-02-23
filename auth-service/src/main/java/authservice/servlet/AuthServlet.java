package authservice.servlet;

import authservice.model.AuthToken;
import authservice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = AuthService.getINSTANCE();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String token = authService.authenticate(username, password);

        if (token != null) {
            AuthToken authToken = new AuthToken(token);
            String json = mapper.writeValueAsString(authToken);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid credentials");
        }
    }
}
