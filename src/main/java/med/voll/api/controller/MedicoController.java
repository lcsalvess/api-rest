package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.domain.pacientes.DadosDetalhamentoPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

//notação para API REST
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    //devolver Código 201
    //devolve no corpo da resposta os dados do novo
    //recurso/registro criado
    //devolve também um cabeçalho do protocolo HTTP(Location)
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastroDeMedicos(@RequestBody @Valid DadosCadastroMedico dados,
                                            UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/cadastro/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
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
    public ResponseEntity<Page<DadosListagemMedico>> listaMedicos(@PageableDefault(size=10, sort = {"nome"}) Pageable pageable) {
        var page = repository.findAllByAtivoTrue(pageable)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizarDados(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
//        para deletar o registro do banco de dados
//        repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
