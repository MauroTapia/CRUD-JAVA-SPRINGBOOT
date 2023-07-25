package ProyectoFinal.ClinicaOdontologica.Service;

import ProyectoFinal.ClinicaOdontologica.entities.Paciente;
import ProyectoFinal.ClinicaOdontologica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }
    public List<Paciente> listarPacientes(){
        return pacienteRepository.findAll();
    }
    public Optional<Paciente> buscarPacientePorEmail(String email){
        return pacienteRepository.findByEmail(email);
    }
    public Optional<Paciente> buscarPacientePorId(Long id){
        return pacienteRepository.findById(id);
    }
    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }
    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }

}
