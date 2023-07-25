package ProyectoFinal.ClinicaOdontologica;

import ProyectoFinal.ClinicaOdontologica.Service.OdontologoService;
import ProyectoFinal.ClinicaOdontologica.controller.OdontologoController;
import ProyectoFinal.ClinicaOdontologica.entities.Odontologo;
import ProyectoFinal.ClinicaOdontologica.exception.BadRequestException;
import ProyectoFinal.ClinicaOdontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OdontologoControllerTest {
    @Mock
    private OdontologoService odontologoService;
    @InjectMocks
    private OdontologoController odontologoController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void guardarOdontologo() throws BadRequestException {
        // Paso 1: Crear un objeto Odontologo con datos de prueba
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Guillermo");
        odontologo.setApellido("Gardella");
        odontologo.setMatricula("MN0858");
        // Paso 2: Configurar el comportamiento esperado del servicio mock
        when(odontologoService.guardarOdontologo(odontologo)).thenReturn(odontologo);
        // Paso 3: Llamar al método guardarOdontologo del controlador
        ResponseEntity<Odontologo> response = odontologoController.guardarOdontologo(odontologo);
        // Paso 4: Verificar la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologo, response.getBody());
    }
    @Test
    public void buscarOdontologoPorId() throws ResourceNotFoundException {
        //Creamos un objeto con datos de pueba
        Odontologo odontologo= new Odontologo("Guillermo","Gardella","MP9242");
        Long id = 1L;
        //configuramos el comportamiento esperado por el service
        when(odontologoService.buscarOdontologoPorId(id)).thenReturn(Optional.of(odontologo));
        // llamamos al metodo del controler
        ResponseEntity<Odontologo> response = odontologoController.buscarOdontologoPorId(id);
        //verificamos la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologo,response.getBody());
    }
    @Test
    public void buscarOdontologoPorIdNoEncontrado() {
        //Creamos el objeto inexistente
        Long id= 1L;
        //configuamos el comportamiento del service
        when(odontologoService.buscarOdontologoPorId(id)).thenReturn(Optional.empty());
        //buscamos id del controlador
        assertThrows(ResourceNotFoundException.class,() ->odontologoController.buscarOdontologoPorId(id));
    }
    @Test
    public void buscarPorMatricula() throws BadRequestException, ResourceNotFoundException {
        //Creamos un objeto con datos de pueba
        Odontologo odontologo= new Odontologo("Guillermo","Gardella","MN0858");
        String matricula= "MN0858";
        //configuramos el comportamiento esperado por el service
        when(odontologoService.buscarPorMatricula(matricula)).thenReturn(Optional.of(odontologo));
        // llamamos al metodo del controler
        ResponseEntity<Odontologo> response = odontologoController.buscarPorMatricula(matricula);
        //verificamos la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologo,response.getBody());
    }
    @Test
    public void buscarPorMatriculaNoEncontrada() {
        //Creamos el objeto inexistente
        String matricula= "MP9242";
        //configuamos el comportamiento del service
        when(odontologoService.buscarPorMatricula(matricula)).thenReturn(Optional.empty());
        //buscamos id del controlador
        assertThrows(ResourceNotFoundException.class,() -> odontologoController.buscarPorMatricula(matricula));
    }
    @Test
    public void actualizarOdontologoTest() throws BadRequestException {
        //creamos el objeto
        Odontologo odontologo= new Odontologo("Guillermo","Gardella","MN0858");
        odontologo.setId(1L);
        //configuramos el service buscando primero el odontologo por su id
        when(odontologoService.buscarOdontologoPorId(odontologo.getId())).thenReturn(Optional.of(odontologo));
        //llamamos al metodo actualizar
        ResponseEntity<String> res= odontologoController.actualizarOdontogo(odontologo);
        // verificamos la respuesta del servicio actualizado
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals("El odontologo " + odontologo.getNombre() + " " + odontologo.getApellido() + " se actualizó correctamente", res.getBody());
        verify(odontologoService).actualizarOdontologo(odontologo);
    }
    @Test
    public void actualizarOdontologoInexistente() {
        Odontologo odontologo= new Odontologo("MP4569","Diego","Carrizo");
        odontologo.setId(1L);
        when(odontologoService.buscarOdontologoPorId(odontologo.getId())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class,() -> odontologoController.actualizarOdontogo(odontologo));
        verify(odontologoService, Mockito.never()).actualizarOdontologo(odontologo);
    }
    @Test
    public void listarOdontologos() throws BadRequestException {
        //Creamos la lista y le agregamos 2 odontologos
        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(new Odontologo("MP456","Maria","Tuñon"));
        odontologos.add(new Odontologo("MP789","Fernanda","Robles"));
        //comportamiento esperado del servicio
        when(odontologoService.listarOdontologos()).thenReturn(odontologos);
        //llamamos al metodo listar del controlador
        ResponseEntity<List<Odontologo>> response =odontologoController.listarOdontologos();
        // verificar la respuesta
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(odontologos,response.getBody());
    }
    @Test
    public void listarOdontologosVacioTest() {
        //creamos la lista vacia
        List<Odontologo> odontologos = new ArrayList<>();
        //comportamiento del service
        when(odontologoService.listarOdontologos()).thenReturn(odontologos);
        //llamar al metodo listar del controlador donde se verifica que lance el BadRequest
        assertThrows(BadRequestException.class,() -> odontologoController.listarOdontologos());
    }
    @Test
    public void eliminarOdontolo() {
        Long id= 1L;
        when(odontologoService.buscarOdontologoPorId(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,() -> odontologoController.eliminarOdontologo(id));
    }
}
