package com.samuel.login_system.client;

import com.samuel.login_system.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name = nome interno, url = o site que vamos acessar
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    // O {cep} será substituído pelo valor que passarmos no método
    @GetMapping("/{cep}/json/")
    ViaCepResponse getAddress(@PathVariable("cep") String cep);
}