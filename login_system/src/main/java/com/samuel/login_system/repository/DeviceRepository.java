package com.samuel.login_system.repository;



import com.samuel.login_system.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {


    // SELECT * FROM tb_devices WHERE user_id = ?
    List<Device> findAllByUserId(UUID userId);
}