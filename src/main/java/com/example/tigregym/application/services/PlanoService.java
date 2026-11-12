package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.PlanoRequest;
import com.example.tigregym.application.DTOs.PlanoResponse;
import com.example.tigregym.domain.entities.EnumStatusPlano;
import com.example.tigregym.domain.entities.Plano;
import com.example.tigregym.domain.repository.EmpresaRepository;
import com.example.tigregym.domain.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<PlanoResponse> listarTodosPlanoTable() {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        return planoRepository
                .findByEmpresa_IdOrderByStatusAsc(usuarioLogado.empresa_id())
                .stream()
                .map(PlanoResponse::new)
                .toList();
    }

    public PlanoResponse salvarPlano(PlanoRequest plano, Long id) {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        var empresaLogada = empresaRepository
                .findById(usuarioLogado.empresa_id())
                .orElse(null);

        if (empresaLogada == null) {
            throw new IllegalStateException("Empresa do usuário não encontrada!");
        }

        if (id == 0L) {
            Plano planoBanco = new Plano();
            planoBanco.setNome(plano.nome());
            planoBanco.setDescricao(plano.descricao());
            planoBanco.setValor(plano.valor());
            planoBanco.setDuracaoEmMeses(plano.duracaoEmMeses());
            planoBanco.setEmpresa(empresaLogada);

            planoRepository.save(planoBanco);
            return new PlanoResponse(planoBanco);
        }

        Plano planoBanco = planoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        usuarioLogado.empresa_id(),
                        EnumStatusPlano.EXCLUIDO
                )
                .orElse(null);

        if (planoBanco != null) {
            planoBanco.setNome(plano.nome());
            planoBanco.setDescricao(plano.descricao());
            planoBanco.setValor(plano.valor());
            planoBanco.setDuracaoEmMeses(plano.duracaoEmMeses());

            planoRepository.save(planoBanco);
            return new PlanoResponse(planoBanco);
        }

        return null;
    }

    public PlanoResponse buscarPorId(Long id) {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        Plano plano = planoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        usuarioLogado.empresa_id(),
                        EnumStatusPlano.EXCLUIDO
                )
                .orElse(null);

        if (plano == null) {
            return null;
        }

        return new PlanoResponse(plano);
    }

    public boolean atualizarStatus(EnumStatusPlano status, Long id) {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        Plano planoBanco = planoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        usuarioLogado.empresa_id(),
                        EnumStatusPlano.EXCLUIDO
                )
                .orElse(null);

        if (planoBanco != null) {
            planoBanco.setStatus(status);
            planoRepository.save(planoBanco);
            return true;
        }

        return false;
    }
}
