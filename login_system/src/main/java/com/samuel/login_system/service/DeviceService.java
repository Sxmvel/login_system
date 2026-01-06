package com.samuel.login_system.service;

import com.samuel.login_system.dto.DeviceRegisterDTO;
import com.samuel.login_system.model.Device;
import com.samuel.login_system.model.User;
import com.samuel.login_system.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    // Cadastrar Dispositivo - Vinculando ao usuário logado
    public Device createDevice(DeviceRegisterDTO data) {

        // Pega o usuário que está no contexto
        User userLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device newDevice = new Device();
        newDevice.setName(data.getName());
        newDevice.setStatus(data.getStatus());
        newDevice.setUser(userLogado); // Vincula automaticamente!

        return deviceRepository.save(newDevice);
    }

    // Listar APENAS os meus dispositivos
    public List<Device> listMyDevices() {
        User userLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return deviceRepository.findAllByUserId(userLogado.getId());
    }

    // Deletar Com verificação de posse
    public void deleteDevice(UUID deviceId) {
        User userLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Busca o dispositivo no banco
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        // Verifica se o dono do dispositivo é o mesmo cara que está logado
        if (!device.getUser().getId().equals(userLogado.getId())) {
            throw new RuntimeException("Acesso Negado: Você não pode deletar o dispositivo de outro usuário!");
        }
        deviceRepository.delete(device);
    }
}