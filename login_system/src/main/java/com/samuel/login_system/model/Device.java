package com.samuel.login_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_devices")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name; // Nome do dispositivo "Iphone 11 pro max"

    @Column(nullable = false)
    private String status; // "On" ou "OFF"


    // Relacao com user 
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
