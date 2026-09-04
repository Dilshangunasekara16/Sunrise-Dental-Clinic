package models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private String appointmentNo;
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentType;
    private String appointmentDate;
    private String appointmentTime;
}