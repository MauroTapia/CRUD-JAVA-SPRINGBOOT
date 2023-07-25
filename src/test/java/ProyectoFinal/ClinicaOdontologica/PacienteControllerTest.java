package ProyectoFinal.ClinicaOdontologica;

import ProyectoFinal.ClinicaOdontologica.Service.PacienteService;
import ProyectoFinal.ClinicaOdontologica.controller.PacienteController;
import ProyectoFinal.ClinicaOdontologica.entities.Domicilio;
import ProyectoFinal.ClinicaOdontologica.entities.Paciente;
import ProyectoFinal.ClinicaOdontologica.exception.BadRequestException;
import ProyectoFinal.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PacienteControllerTest {
    @Mock
    private PacienteService pacienteService;
    @InjectMocks
    private PacienteController pacienteController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void guardarPaciente() throws BadRequestException {
        Domicilio domicilio = new Domicilio("calle 210A", "1234", "Sourigues", "Buenos Aires");
        Paciente paciente = new Paciente("Guillermo","Gardella","28778705", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio);
        when(pacienteService.guardarPaciente(paciente)).thenReturn(paciente);
        ResponseEntity<Paciente> response = pacienteController.guardarPaciente(paciente);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paciente, response.getBody());
    }

    @Test
    public void listarPacientes() throws BadRequestException {
        List<Paciente> pacientes = new ArrayList<>();
        Domicilio domicilio1 = new Domicilio("calle 210A", "1234", "Sourigues", "Buenos Aires");
        Paciente paciente1 = new Paciente("Guillermo","Gardella","28778705", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio1);
        Domicilio domicilio2 = new Domicilio(" 210A", "1234 dto 25", "Berazategui", "Buenos Aires");
        Paciente paciente2 = new Paciente("Francisco","Gardella","50787782", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio2);
        when(pacienteService.listarPacientes()).thenReturn(pacientes);
        ResponseEntity<List<Paciente>> response =pacienteController.listarPacientes();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(pacientes,response.getBody());
    }

    @Test
    public void buscarPacientePorId() throws ResourceNotFoundException {
        Domicilio domicilio = new Domicilio("calle 210A", "1234", "Sourigues", "Buenos Aires");
        Paciente paciente = new Paciente("Guillermo","Gardella","28778705", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio);
        Long id = 1L;
        when(pacienteService.buscarPacientePorId(id)).thenReturn(Optional.of(paciente));
        ResponseEntity<Paciente> response = pacienteController.buscarPacientePorId(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paciente,response.getBody());
    }

    @Test
    public void buscarPorEmail() throws BadRequestException, ResourceNotFoundException {
        Domicilio domicilio = new Domicilio("calle 210A", "1234", "Sourigues", "Buenos Aires");
        Paciente paciente = new Paciente("Guillermo","Gardella","28778705", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio);
        String email= "guillegardella@Gmail.com";
        when(pacienteService.buscarPacientePorEmail(email)).thenReturn(Optional.of(paciente));
        ResponseEntity<Paciente> response = pacienteController.buscarPacientePorEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paciente,response.getBody());
    }

    @Test
    public void actualizarPaciente() throws BadRequestException {
        Domicilio domicilio = new Domicilio("calle 210A", "1234", "Sourigues", "Buenos Aires");
        Paciente paciente = new Paciente("Guillermo","Gardella","28778705", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio);
        paciente.setId(1L);
        when(pacienteService.buscarPacientePorId(paciente.getId())).thenReturn(Optional.of(paciente));
        ResponseEntity<String> res= pacienteController.actualizarPaciente(paciente);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " se actualizó correctamente", res.getBody());
        verify(pacienteService).actualizarPaciente(paciente);
    }

    @Test
    public void eliminarPaciente() {
        Domicilio domicilio = new Domicilio("calle 210A", "1234", "Sourigues", "Buenos Aires");
        Paciente paciente = new Paciente("Guillermo","Gardella","28778705", LocalDate.of(2023,06,20), "guillegardella@gmail.com", domicilio);
        Long id= 1L;
        when(pacienteService.buscarPacientePorId(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,() -> pacienteController.eliminarPaciente(id));
    }
}
