package ProyectoFinal.ClinicaOdontologica;

import ProyectoFinal.ClinicaOdontologica.Service.OdontologoService;
import ProyectoFinal.ClinicaOdontologica.entities.Odontologo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    void guardarOdontologo() {
        Odontologo odontologoAGuardar = new Odontologo("Guillermo", "Gardella", "MP858");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoAGuardar);
        assertEquals(1L, odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    void listarOdontologos() {
        Odontologo odontologo2 = new Odontologo("Guillermo", "Gardella", "MN9242");
        odontologoService.guardarOdontologo(odontologo2);
        List<Odontologo> listaEncontrada = odontologoService.listarOdontologos();
        assertEquals(2, listaEncontrada.size());
    }

    @Test
    @Order(3)
    void buscarOdontologoPorId() {
        Long idABuscar = 1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(idABuscar);
        assertTrue(odontologoBuscado.isPresent());
    }

    @Test
    @Order(4)
    void buscarPorMatricula() {
        String matricula = "MP858";
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);
        assertTrue(odontologoBuscado.isPresent());
    }

    @Test
    @Order(5)
    void actualizarOdontologo() {
        Odontologo odontologo3 = new Odontologo(1L, "Guillermo", "Gardella", "MP858");
        odontologoService.actualizarOdontologo(odontologo3);
        assertEquals("Guillermo", odontologoService.buscarOdontologoPorId(1L).get().getNombre());
    }

    @Test
    @Order(6)
    void eliminarOdontologo() {
        Long id = 1L;
        odontologoService.eliminarOdontologo(id);
        Optional<Odontologo> odontologoEliminado = odontologoService.buscarOdontologoPorId(id);
        assertFalse(odontologoEliminado.isPresent());
    }
}