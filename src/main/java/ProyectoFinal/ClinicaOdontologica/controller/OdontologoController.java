package ProyectoFinal.ClinicaOdontologica.controller;

import ProyectoFinal.ClinicaOdontologica.Service.OdontologoService;
import ProyectoFinal.ClinicaOdontologica.entities.Odontologo;
import ProyectoFinal.ClinicaOdontologica.exception.BadRequestException;
import ProyectoFinal.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        if (odontologo.getMatricula().contains("M")) {
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        } else {
            throw new BadRequestException("El odontólogo no se puede guardar porque debe tener al principio de la matricula las letras MP o MN");
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() throws BadRequestException {
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        if (!odontologos.isEmpty()) {
            return ResponseEntity.ok(odontologos);
        } else {
            throw new BadRequestException("No se puede listar odontologos porque no hay datos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            throw new ResourceNotFoundException("El paciente con el id " + id + "no existe");
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) throws BadRequestException, ResourceNotFoundException {

        if (!matricula.contains("M")) {
            throw new BadRequestException("El odontólogo con la matrícula " + matricula + "debe tener al inicio las siglas MN (matrícula nacional) o MP (matrícula provincial");
        }

        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);
        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            throw new ResourceNotFoundException("El odontólogo con la matrícula " + matricula + "no existe");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontogo(@RequestBody Odontologo odontologo) throws BadRequestException {
        Optional<Odontologo> odontologobuscado = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if (odontologobuscado.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("El odontologo " + odontologo.getNombre() + " " + odontologo.getApellido() + " se actualizó correctamente");
        } else {
            throw new BadRequestException("El odontologo: " + odontologo.getNombre() + "con ID: " + odontologo.getId() + " no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Se elimino correctamente el odontologo con el ID " + id);
        } else {
            throw new ResourceNotFoundException("El odontologo con el ID " + id + " no existe");
        }
    }

}

