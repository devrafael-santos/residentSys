package com.raffasdev.residentsys.controller;

import com.raffasdev.residentsys.dto.*;
import com.raffasdev.residentsys.model.Casa;
import com.raffasdev.residentsys.model.Cobranca;
import com.raffasdev.residentsys.model.Morador;
import com.raffasdev.residentsys.service.MoradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/moradores")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class MoradorController {

    private final MoradorService moradorService;

    @Transactional
    @PostMapping("")
    public Morador criarMorador(@RequestBody @Valid Morador morador) {
        return moradorService.criarMorador(morador);
    }

    @GetMapping("")
    public List<MoradorGetRetorno> listarMoradores() {
        return moradorService.pegarMoradores();
    }

    @GetMapping("/{moradorCpf}")
    public MoradorGetRetorno pegarMoradorPorCpf(@PathVariable String moradorCpf) {
        return moradorService.pegarMoradorPorCpf(moradorCpf);
    }

    @Transactional
    @PutMapping("/{moradorCpf}")
    public MoradorPutRetorno atualizarMorador(@RequestBody @Valid Morador morador, @PathVariable String moradorCpf) {
        return moradorService.atualizarMorador(morador, moradorCpf);
    }

    @DeleteMapping("/{moradorCpf}")
    public void deletarMorador(@PathVariable String moradorCpf) {
        moradorService.removerMorador(moradorCpf);
    }

    @Transactional
    @PostMapping("/{moradorCpf}/casa")
    public CasaCriadaRetorno criarCasa(@RequestBody @Valid Casa casa, @PathVariable String moradorCpf) {
        return moradorService.criarCasaComCpfMorador(moradorCpf, casa);
    }

    @Transactional
    @PostMapping("/{moradorCpf}/casa/cobrancas")
    public CobrancaPostRetorno criarCobranca(@RequestBody @Valid Cobranca cobranca, @PathVariable String moradorCpf) {
        return moradorService.criarCobranca(moradorCpf, cobranca);
    }

    @GetMapping("/{moradorCpf}/casa/cobrancas")
    public List<CobrancaGetRetorno> listarCobrancas(@PathVariable String moradorCpf) {
        return moradorService.pegarCobrancas(moradorCpf);
    }

    @GetMapping("/{moradorCpf}/casa")
    public CasaGetRetorno pegarCasa(@PathVariable String moradorCpf) {
        return moradorService.pegarCasa(moradorCpf);
    }

}
