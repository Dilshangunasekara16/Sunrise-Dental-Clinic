package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AppointmentDAO;
import models.Appointment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// This annotation maps the servlet to the URL endpoint
@WebServlet("/api/appointments")
public class AppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    // Handles GET requests for displaying an appointment (Requirement #3)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Retrieve the appointment number from the URL parameter (e.g., ?id=A001)
        String appointmentNo = req.getParameter("id");

        if (appointmentNo != null && !appointmentNo.isEmpty()) {
            Appointment appt = appointmentDAO.getAppointmentByNumber(appointmentNo);

            if (appt != null) {
                resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
                // Convert the Java object back to JSON and send it to the frontend
                objectMapper.writeValue(resp.getWriter(), appt);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                resp.getWriter().write("{\"message\": \"Appointment not found.\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            resp.getWriter().write("{\"message\": \"Missing appointment ID.\"}");
        }
    }

    // Handles POST requests for registering a new appointment (Requirement #2)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Read the JSON body from the HTTP request and map it to the Appointment class
            Appointment newAppt = objectMapper.readValue(req.getReader(), Appointment.class);

            boolean isSaved = appointmentDAO.registerAppointment(newAppt);

            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
                resp.getWriter().write("{\"message\": \"Appointment successfully registered!\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Server Error
                resp.getWriter().write("{\"message\": \"Failed to register appointment.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            resp.getWriter().write("{\"message\": \"Invalid input format.\"}");
        }
    }
}