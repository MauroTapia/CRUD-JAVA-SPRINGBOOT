package ProyectoFinal.ClinicaOdontologica.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class TurnoDTO {
    private Long id;
    private LocalDate fecha;
    private Long paciente_id;
    private Long odontologo_id;
    private String pacienteNombre;
    private String odontologoNombre;

    public TurnoDTO(LocalDate fecha, Long paciente_id, Long odontologo_id, String pacienteNombre, String odontologoNombre) {
        this.fecha = fecha;
        this.paciente_id = paciente_id;
        this.odontologo_id = odontologo_id;
        this.pacienteNombre = pacienteNombre;
        this.odontologoNombre = odontologoNombre;
    }

    public TurnoDTO() {
    }
}
