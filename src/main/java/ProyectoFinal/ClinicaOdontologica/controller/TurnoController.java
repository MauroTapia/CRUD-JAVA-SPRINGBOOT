package ProyectoFinal.ClinicaOdontologica.controller;

import ProyectoFinal.ClinicaOdontologica.Service.OdontologoService;
import ProyectoFinal.ClinicaOdontologica.Service.PacienteService;
import ProyectoFinal.ClinicaOdontologica.Service.TurnoService;
import ProyectoFinal.ClinicaOdontologica.dto.TurnoDTO;
import ProyectoFinal.ClinicaOdontologica.entities.Odontologo;
import ProyectoFinal.ClinicaOdontologica.entities.Paciente;
import ProyectoFinal.ClinicaOdontologica.entities.Turno;
import ProyectoFinal.ClinicaOdontologica.exception.BadRequestException;
import ProyectoFinal.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());

        if (!pacienteBuscado.isPresent()) {
            throw new BadRequestException("El Id del paciente no existe.");
        }

        if (!odontologoBuscado.isPresent()) {
            throw new BadRequestException("El Id del odontólogo no existe.");
        }

        return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoDTO>> listarTurnos() {
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoPorId(id);
        if (turnoBuscado.isPresent()) {
            TurnoDTO turnoEncontrado = turnoBuscado.get();

            Optional<Paciente> pacienteTurno = pacienteService.buscarPacientePorId(turnoEncontrado.getPaciente_id());
            Optional<Odontologo> odontologoTurno = odontologoService.buscarOdontologoPorId(turnoEncontrado.getOdontologo_id());

            if (pacienteTurno.isPresent() && odontologoTurno.isPresent()) {
                Odontologo odontologoEncontrado = odontologoTurno.get();
                Paciente pacienteEncontrado = pacienteTurno.get();

                turnoEncontrado.setOdontologo_id(odontologoEncontrado.getId());
                turnoEncontrado.setOdontologoNombre(odontologoEncontrado.getNombre());

                turnoEncontrado.setPaciente_id(pacienteEncontrado.getId());
                turnoEncontrado.setPacienteNombre(pacienteEncontrado.getNombre());
            }

            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            throw new ResourceNotFoundException("El turno con el id " + id + "no existe");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoPorId(turno.getId());
        if (turnoBuscado.isPresent()) {
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Se actualizo correctamente el turno solicitado con el id: " + turno.getId());
        } else {
            throw new BadRequestException("No existe o no se puede actualizar el turno con el id: " + turno.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoPorId(id);
        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Se elimino correctamente el turno con el Id " + id + ".");
        } else {
            throw new ResourceNotFoundException("El turno asociado al id " + id + " no existe.");
        }
    }
}
