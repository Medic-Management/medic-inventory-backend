package com.medic.inventory.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("=== HASHES BCRYPT PARA DATA.SQL ===");
        System.out.println("admin123: " + encoder.encode("admin123"));
        System.out.println("auxiliar123: " + encoder.encode("auxiliar123"));
        System.out.println("farm123: " + encoder.encode("farm123"));
        System.out.println("jefe123: " + encoder.encode("jefe123"));
    }
}
