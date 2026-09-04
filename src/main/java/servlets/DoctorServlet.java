package servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ClinicDAO;
import models.Doctor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/doctors")
public class DoctorServlet extends HttpServlet {
    private ClinicDAO clinicDAO = new ClinicDAO();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), clinicDAO.getAllDoctors());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Doctor doc = mapper.readValue(req.getReader(), Doctor.class);
        if (clinicDAO.addDoctor(doc)) resp.setStatus(HttpServletResponse.SC_CREATED);
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (clinicDAO.deleteDoctor(id)) resp.setStatus(HttpServletResponse.SC_OK);
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}