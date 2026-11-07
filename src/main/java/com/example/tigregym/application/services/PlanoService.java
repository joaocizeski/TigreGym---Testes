package com.example.tigregym.application.services;

import com.example.tigregym.application.DTOs.PlanoRequest;
import com.example.tigregym.application.DTOs.PlanoResponse;
import com.example.tigregym.application.ports.PlanoServicePort;
import com.example.tigregym.domain.entities.Empresa;
import com.example.tigregym.domain.entities.EnumStatusPlano;
import com.example.tigregym.domain.entities.Plano;
import com.example.tigregym.domain.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoService implements PlanoServicePort {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public List<PlanoResponse> listarTodosPlanoTable() {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        return planoRepository
                .findByEmpresa_IdOrderByStatusAsc(empresa.getId())
                .stream()
                .map(PlanoResponse::new)
                .toList();
    }

    @Override
    public PlanoResponse salvarPlano(PlanoRequest plano, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        if (id == 0L) {
            Plano planoBanco = new Plano();
            planoBanco.setNome(plano.nome());
            planoBanco.setDescricao(plano.descricao());
            planoBanco.setValor(plano.valor());
            planoBanco.setDuracaoEmMeses(plano.duracaoEmMeses());
            planoBanco.setEmpresa(empresa);

            planoRepository.save(planoBanco);
            return new PlanoResponse(planoBanco);
        }

        Plano planoBanco = planoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
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

    @Override
    public PlanoResponse buscarPorId(Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Plano plano = planoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
                        EnumStatusPlano.EXCLUIDO
                )
                .orElse(null);

        if (plano == null) {
            return null;
        }

        return new PlanoResponse(plano);
    }

    @Override
    public boolean atualizarStatus(EnumStatusPlano status, Long id) {

        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();

        Plano planoBanco = planoRepository
                .findByIdAndEmpresa_IdAndStatusNot(
                        id,
                        empresa.getId(),
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
