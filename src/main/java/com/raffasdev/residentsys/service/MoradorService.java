package com.raffasdev.residentsys.service;

import com.raffasdev.residentsys.dto.*;
import com.raffasdev.residentsys.model.Casa;
import com.raffasdev.residentsys.model.Cobranca;
import com.raffasdev.residentsys.model.Morador;
import com.raffasdev.residentsys.model.Usuario;
import com.raffasdev.residentsys.repository.CasaRepository;
import com.raffasdev.residentsys.repository.CobrancaRepository;
import com.raffasdev.residentsys.repository.MoradorRepository;
import com.raffasdev.residentsys.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MoradorService {

    private final UsuarioRepository usuarioRepository;
    private final MoradorRepository moradorRepository;
    private final CasaRepository casaRepository;
    private final CobrancaRepository cobrancaRepository;

    public Morador criarMorador(Morador morador) {
        if (moradorRepository.existsById(morador.getCpf())) {
            throw new RuntimeException("Morador ja existe com o CPF " + morador.getCpf());
        }

        if (moradorRepository.existsByEmail(morador.getEmail())) {
            throw new RuntimeException("Morador ja existe com o E-mail " + morador.getEmail());
        }

        return moradorRepository.save(morador);
    }

    public List<MoradorGetRetorno> pegarMoradores() {
        return moradorRepository.findAll().stream().map(
                        morador -> MoradorGetRetorno.builder()
                                .casaId(morador.getCasa() != null ? morador.getCasa().getCasaId() : null)
                                .nome(morador.getNome())
                                .telefone(morador.getTelefone())
                                .email(morador.getEmail())
                                .cpf(morador.getCpf())
                                .build()
                )
                .toList();
    }

    public MoradorGetRetorno pegarMoradorPorCpf(String moradorCpf) {
        Morador morador = moradorRepository.findById(moradorCpf).orElseThrow(RuntimeException::new);

        return MoradorGetRetorno.builder()
                .casaId(morador.getCasa() != null ? morador.getCasa().getCasaId() : null)
                .nome(morador.getNome())
                .telefone(morador.getTelefone())
                .email(morador.getEmail())
                .cpf(morador.getCpf())
                .build();
    }

    public MoradorPutRetorno atualizarMorador(Morador moradorASerAtualizado, String moradorCpf) {
        Morador morador = moradorRepository.findById(moradorCpf).orElseThrow(RuntimeException::new);

        if (moradorRepository.existsByEmail(moradorASerAtualizado.getEmail())
                && !morador.getEmail().equals(moradorASerAtualizado.getEmail())) {
            throw new RuntimeException("Morador ja existe com o E-mail " + moradorASerAtualizado.getEmail());
        }

        morador.setNome(moradorASerAtualizado.getNome());
        morador.setTelefone(moradorASerAtualizado.getTelefone());
        morador.setEmail(moradorASerAtualizado.getEmail());

        Morador moradorAtualizado = moradorRepository.save(morador);

        return MoradorPutRetorno.builder()
                .email(moradorASerAtualizado.getEmail())
                .nome(moradorASerAtualizado.getNome())
                .telefone(moradorASerAtualizado.getTelefone())
                .build();
    }

    public void removerMorador(String moradorCpf) {
        moradorRepository.deleteById(moradorCpf);
    }

    public CasaCriadaRetorno criarCasaComCpfMorador(String cpf, Casa casa) {

        if (casaRepository.existsByEnderecoAndNumCasa(casa.getEndereco(), casa.getNumCasa())) {
            throw new RuntimeException(casa.getEndereco() + " " + casa.getNumCasa());
        }

        Morador morador = moradorRepository.findById(cpf).orElseThrow(RuntimeException::new);
        Usuario usuario = usuarioRepository.findById("admin").orElseThrow(RuntimeException::new);

        Casa novaCasa = casaRepository.save(
                Casa.builder()
                        .endereco(casa.getEndereco())
                        .numCasa(casa.getNumCasa())
                        .morador(morador)
                        .usuario(usuario)
                        .build()
        );

        return CasaCriadaRetorno.builder()
                .casaId(novaCasa.getCasaId())
                .endereco(casa.getEndereco())
                .numCasa(casa.getNumCasa())
                .moradorCpf(morador.getCpf())
                .usuarioLogin(usuario.getLogin())
                .build();
    }

    public CobrancaPostRetorno criarCobranca(String moradorCpf, Cobranca cobranca) {
        Casa casa = casaRepository.findByMoradorCpf(moradorCpf).orElseThrow(RuntimeException::new);

        Cobranca novaCobranca = cobrancaRepository.save(
                Cobranca.builder()
                        .valor(cobranca.getValor())
                        .dataVencimento(cobranca.getDataVencimento())
                        .dataPagamento(cobranca.getDataPagamento())
                        .status(cobranca.getStatus())
                        .casa(casa)
                        .build()
        );

        return CobrancaPostRetorno.builder()
                .cobrancaId(novaCobranca.getCobrancaId())
                .valor(cobranca.getValor())
                .dataVencimento(cobranca.getDataVencimento())
                .dataPagamento(cobranca.getDataPagamento())
                .status(cobranca.getStatus())
                .casa_id(casa.getCasaId())
                .build();
    }

    public List<CobrancaGetRetorno> pegarCobrancas(String moradorCpf) {
        Casa casa = casaRepository.findByMoradorCpf(moradorCpf).orElseThrow(RuntimeException::new);

        return cobrancaRepository.findCobrancasByCasa_CasaId(casa.getCasaId()).stream().map(
                cobranca -> {
                    return CobrancaGetRetorno.builder()
                            .cobrancaId(cobranca.getCobrancaId())
                            .valor(cobranca.getValor())
                            .dataVencimento(cobranca.getDataVencimento())
                            .dataPagamento(cobranca.getDataPagamento())
                            .status(cobranca.getStatus())
                            .casa_id(cobranca.getCasa().getCasaId())
                            .build();
                }
        ).toList();
    }

    public CasaGetRetorno pegarCasa(String moradorCpf) {
        Casa casa = casaRepository.findByMoradorCpf(moradorCpf).orElseThrow(RuntimeException::new);

        return CasaGetRetorno.builder()
                .casaId(casa.getCasaId())
                .endereco(casa.getEndereco())
                .numCasa(casa.getNumCasa())
                .cobrancas(casa.getCobrancas())
                .moradorCpf(casa.getMorador().getCpf())
                .usuarioLogin(casa.getUsuario().getLogin())
                .build();
    }
}
