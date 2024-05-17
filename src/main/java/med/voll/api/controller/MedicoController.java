package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//notação para API REST
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping("/cadastro")
    @Transactional
    public void cadastroDeMedicos(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    // para controlar a quantidade de itens, no final do HTML
    //inserir "?size= numero de elementos desejado por pagina"
    // para ir para a próxima página, após o passo anterior
    //acrescentar no HTML "&page= numero da página"
    //para ordenar por um elemento, colocar no HTML
    //"?sort=elemento"
    //ordenação por padrão CRESCENTE (ASC), para decrescente
    //inserir ",desc"
    @GetMapping
    public Page<DadosListagemMedico> listaMedicos(@PageableDefault(size=10, sort = {"nome"}) Pageable pageable) {
        return repository.findAll(pageable)
                .map(DadosListagemMedico::new);
    }

    @PutMapping("/atualizar")
    @Transactional
    public void atualizarDados(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
}
