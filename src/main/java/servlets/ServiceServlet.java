package servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ClinicDAO;
import models.ClinicService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/services")
public class ServiceServlet extends HttpServlet {
    private ClinicDAO clinicDAO = new ClinicDAO();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), clinicDAO.getAllServices());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ClinicService srv = mapper.readValue(req.getReader(), ClinicService.class);
        if (clinicDAO.addService(srv)) resp.setStatus(HttpServletResponse.SC_CREATED);
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (clinicDAO.deleteService(id)) resp.setStatus(HttpServletResponse.SC_OK);
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}