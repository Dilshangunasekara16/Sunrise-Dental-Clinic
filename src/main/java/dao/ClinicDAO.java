package dao;

import models.Doctor;
import models.ClinicService;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicDAO {

    // --- DOCTOR METHODS ---
    public boolean addDoctor(Doctor doc) {
        String sql = "INSERT INTO doctors (doctor_id, doctor_title) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doc.getDoctorId());
            stmt.setString(2, doc.getDoctorTitle());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean deleteDoctor(String doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctorId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM doctors"); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) { list.add(new Doctor(rs.getString("doctor_id"), rs.getString("doctor_title"))); }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // --- SERVICE METHODS ---
    public boolean addService(ClinicService service) {
        String sql = "INSERT INTO clinic_services (service_id, service_title) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, service.getServiceId());
            stmt.setString(2, service.getServiceTitle());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean deleteService(String serviceId) {
        String sql = "DELETE FROM clinic_services WHERE service_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serviceId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public List<ClinicService> getAllServices() {
        List<ClinicService> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM clinic_services"); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) { list.add(new ClinicService(rs.getString("service_id"), rs.getString("service_title"))); }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}