package com.tallerwebi.dominio.servicioImpl;

import com.password4j.Password;
import com.tallerwebi.dominio.ServicioPassword;
import org.springframework.stereotype.Service;

@Service
public class ServicioPasswordImpl implements ServicioPassword {
    @Override
    public String hashearPassword(String password) {
        return Password.hash(password).withBcrypt().getResult();
    }

    @Override
    public boolean verificarPassword(String passwordIngresado, String hashGuardado) {
        return Password.check(passwordIngresado, hashGuardado).withBcrypt();
    }

//    public static class HashGenerator {
//        public static void main(String[] args) {
//            String hash = Password.hash("test").withBcrypt().getResult();
//            System.out.println(hash);
//        }
//    }

}
