package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedico;
import org.springframework.web.bind.annotation.*;

//notação para API REST
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @PostMapping
    public void cadastroDeMedicos(@RequestBody DadosCadastroMedico dados) {
        System.out.println(dados);
    }

}
