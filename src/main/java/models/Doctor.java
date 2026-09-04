package models;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Doctor {
    private String doctorId;
    private String doctorTitle;
}