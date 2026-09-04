package models;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ClinicService {
    private String serviceId;
    private String serviceTitle;
}