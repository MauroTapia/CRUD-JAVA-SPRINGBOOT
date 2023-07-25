package ProyectoFinal.ClinicaOdontologica.security;

import ProyectoFinal.ClinicaOdontologica.entities.User;
import ProyectoFinal.ClinicaOdontologica.entities.UserRole;
import ProyectoFinal.ClinicaOdontologica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitial implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String pass1="admin";
        String passCifrado1= cifrador.encode(pass1);
        String pass2="user";
        String passCifrado2= cifrador.encode(pass2);
        String passCifradoAdmin2 = cifrador.encode("grimm1234");

        User userADMIN= new User("Mauro","Tapia", "maurito","admin@admin.com",passCifrado1, UserRole.ROLE_ADMIN);
        User userADMIN2= new User("Pablo","Ramirez", "pablito","pablo@admin.com",passCifradoAdmin2, UserRole.ROLE_ADMIN);
        System.out.println("El nombre del user para ingresar en modo administrador es: " + userADMIN.getEmail());
        System.out.println("La contraseña del ADMIN es la siguiente: " + pass1);
        System.out.println("La contraseña cifrada del ADMIN es la siguiente: "+ passCifrado1);
        userRepository.save(userADMIN);
        userRepository.save(userADMIN2);
        User userUSER= new User("User","UserApellido","username","user@user.com",passCifrado2, UserRole.ROLE_USER);
        System.out.println("El nombre del user para ingresar en modo usuario es: " + userUSER.getEmail());
        System.out.println("La contraseña cifrada del USER es la siguiente: " + pass2);
        System.out.println("La contraseña cifrada del USER es la siguiente: " + passCifrado2);
        userRepository.save(userUSER);
    }
}

