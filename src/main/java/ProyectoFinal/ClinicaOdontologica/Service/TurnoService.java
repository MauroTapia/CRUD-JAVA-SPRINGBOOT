package ProyectoFinal.ClinicaOdontologica.Service;

import ProyectoFinal.ClinicaOdontologica.dto.TurnoDTO;
import ProyectoFinal.ClinicaOdontologica.entities.Odontologo;
import ProyectoFinal.ClinicaOdontologica.entities.Paciente;
import ProyectoFinal.ClinicaOdontologica.entities.Turno;
import ProyectoFinal.ClinicaOdontologica.repository.OdontologoRepository;
import ProyectoFinal.ClinicaOdontologica.repository.PacienteRepository;
import ProyectoFinal.ClinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private OdontologoRepository odontologoRepository;
    public TurnoDTO guardarTurno(Turno turno){
        Turno turnoGuardado= turnoRepository.save((turno));
        return turnoATurnoDTO(turnoGuardado);
    }
    public List<TurnoDTO> listarTurnos(){
        List<Turno> turnosEncontrados = turnoRepository.findAll();
        List<TurnoDTO> turnoDTOLista = new ArrayList<>();
        for (Turno turno: turnosEncontrados) {
            turnoDTOLista.add(turnoATurnoDTO(turno));
        }
        return turnoDTOLista;
    }
    public Optional<TurnoDTO> buscarTurnoPorId(Long id){
        Optional<Turno> turnoBuscado= turnoRepository.findById(id);
        if(turnoBuscado.isPresent()){
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }else{
            return Optional.empty();
        }
    }
    public void actualizarTurno(Turno turno){
        turnoRepository.save((turno));
    }
    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }
    private TurnoDTO turnoATurnoDTO(Turno turno){
        TurnoDTO turnoDTO= new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setFecha(turno.getFecha());
        turnoDTO.setPaciente_id(turno.getPaciente().getId());
        turnoDTO.setOdontologo_id(turno.getOdontologo().getId());

        Optional<Paciente> paciente = pacienteRepository.findById(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turno.getOdontologo().getId());

        if(paciente.isPresent() && odontologo.isPresent()){
            turnoDTO.setOdontologoNombre(odontologo.get().getNombre());
            turnoDTO.setPacienteNombre(paciente.get().getNombre());
        }

        return turnoDTO;
    }
    private Turno turnoDTOATurno(TurnoDTO turnoDTO){
            Turno turno = new Turno();
            Odontologo odontologo= new Odontologo();
            Paciente paciente= new Paciente();
            odontologo.setId(turnoDTO.getOdontologo_id());
            paciente.setId(turnoDTO.getPaciente_id());
            turno.setId(turnoDTO.getId());
            turno.setFecha(turnoDTO.getFecha());
            return  turno;

        }
}

