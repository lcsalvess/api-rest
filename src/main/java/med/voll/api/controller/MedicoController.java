package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @GetMapping
    public Page<DadosListagemMedico> listaMedicos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(DadosListagemMedico::new);
    }
}
