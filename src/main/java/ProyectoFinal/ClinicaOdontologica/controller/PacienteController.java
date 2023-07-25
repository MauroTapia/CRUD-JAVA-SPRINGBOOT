package ProyectoFinal.ClinicaOdontologica.controller;

import ProyectoFinal.ClinicaOdontologica.Service.PacienteService;
import ProyectoFinal.ClinicaOdontologica.entities.Paciente;
import ProyectoFinal.ClinicaOdontologica.exception.BadRequestException;
import ProyectoFinal.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente) throws BadRequestException {

        if(paciente.getDocumento().isBlank()){
            throw new BadRequestException("El paciente no se puede guardar porque no tiene un documento asignado");
        }

        if (paciente.getEmail().contains("@")) {
            Optional<Paciente> pacienteBuscadoEmail = pacienteService.buscarPacientePorEmail(paciente.getEmail());
            if (pacienteBuscadoEmail.isPresent()) {
                throw new BadRequestException("El correo ya está registrado");
            }
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        } else {
            throw new BadRequestException("El paciente no se puede guardar porque no tiene mail o número de documento");
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            throw new ResourceNotFoundException("El paciente con el id " + id + "no existe");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPacientePorEmail(@PathVariable String email) throws BadRequestException, ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorEmail(email);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else if (!pacienteBuscado.get().getEmail().contains("@")) {
            throw new BadRequestException("El paciente con el email " + email + "debe tener el @");
        } else {
            throw new ResourceNotFoundException("El paciente con el email " + email + "no existe");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        Optional<Paciente> pacientebuscado = pacienteService.buscarPacientePorId(paciente.getId());
        if (pacientebuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " se actualizó correctamente");
        } else {
            throw new BadRequestException("Paciente no encontrado con el ID " + paciente.getId() + " y con nombre el " + paciente.getNombre());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(id);
        if (pacienteBuscado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Se elimino correctamente el paciente con el ID " + id);
        } else {
            throw new ResourceNotFoundException("El paciente con el " + id + " no existe");
        }
    }
}
