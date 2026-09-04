package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/auth/login")
public class LoginServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Parse the incoming JSON to extract the username and password
            Map<String, String> credentials = objectMapper.readValue(req.getReader(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            // Verify against authorized staff credentials
            if ("admin".equals(username) && "admin123".equals(password)) {

                // Create an HTTP session to keep the user logged in
                HttpSession session = req.getSession(true);
                session.setAttribute("user", username);

                resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
                resp.getWriter().write("{\"message\": \"Login successful\", \"status\": \"success\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                resp.getWriter().write("{\"message\": \"Invalid username or password\", \"status\": \"error\"}");
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            resp.getWriter().write("{\"message\": \"Invalid request format\", \"status\": \"error\"}");
        }
    }
}