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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/api/billing")
public class BillingServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String appointmentNo = req.getParameter("id");

        // Validate the request
        if (appointmentNo == null || appointmentNo.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            resp.getWriter().write("{\"message\": \"Missing appointment ID.\"}");
            return;
        }

        // Retrieve the appointment from the database
        Appointment appt = appointmentDAO.getAppointmentByNumber(appointmentNo);

        if (appt == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
            resp.getWriter().write("{\"message\": \"Appointment not found.\"}");
            return;
        }

        // Calculate costs
        double consultationFee = 2000.00; // Standard base fee
        double treatmentFee = 0.00;
        String treatment = appt.getTreatmentType().toLowerCase();

        // Determine cost based on the treatment type
        switch (treatment) {
            case "cleaning":
                treatmentFee = 3500.00;
                break;
            case "filling":
                treatmentFee = 4500.00;
                break;
            case "extraction":
                treatmentFee = 5000.00;
                break;
            case "root canal":
                treatmentFee = 15000.00;
                break;
            case "whitening":
                treatmentFee = 12000.00;
                break;
            default:
                treatmentFee = 3000.00; // Default fee for other/unspecified treatments
                break;
        }

        double totalCost = consultationFee + treatmentFee;

        // Construct a structured receipt using LinkedHashMap to preserve order
        Map<String, Object> receipt = new LinkedHashMap<>();
        receipt.put("appointmentNo", appt.getAppointmentNo());
        receipt.put("date", appt.getAppointmentDate());
        receipt.put("patientName", appt.getPatientName());
        receipt.put("dentistName", appt.getDentistName());
        receipt.put("treatmentType", appt.getTreatmentType());
        receipt.put("consultationFee", consultationFee);
        receipt.put("treatmentFee", treatmentFee);
        receipt.put("totalCost", totalCost);
        receipt.put("currency", "LKR");

        // Return the final receipt as JSON
        resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
        objectMapper.writeValue(resp.getWriter(), receipt);
    }
}