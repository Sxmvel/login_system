package com.samuel.login_system.controller;

import com.samuel.login_system.dto.DeviceRegisterDTO;
import com.samuel.login_system.model.Device;
import com.samuel.login_system.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<Device> create(@RequestBody DeviceRegisterDTO data) {
        Device device = deviceService.createDevice(data);
        return ResponseEntity.ok(device);
    }

    @GetMapping
    public ResponseEntity<List<Device>> listAll() {
        List<Device> devices = deviceService.listMyDevices();
        return ResponseEntity.ok(devices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}