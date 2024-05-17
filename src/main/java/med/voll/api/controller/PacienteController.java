package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import med.voll.api.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//notação para API REST
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping("/cadastro")
    @Transactional
    public void cadastroDePaciente(@RequestBody @Valid DadosCadastroPaciente dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listaPacientes(@PageableDefault(size=10, sort = {"nome"}) Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable)
                .map(DadosListagemPaciente::new);
    }

    @PutMapping("/atualizar")
    @Transactional
    public void atualizarDados(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }
}
