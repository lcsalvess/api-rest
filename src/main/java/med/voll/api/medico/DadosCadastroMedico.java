package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome,
                                  String email,
                                  Integer crm,
                                  Especialidade especialidade,
                                  DadosEndereco endereco) {
}
