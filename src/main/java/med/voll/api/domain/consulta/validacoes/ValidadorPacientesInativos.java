package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;

public class ValidadorPacientesInativos {
    private PacienteRepository pacienteRepository;

    public void pacienteInativo(DadosAgendamentoConsulta dados) {
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var ativo = paciente.getAtivo();
        if (!ativo) {
            throw new ValidacaoException("Paciente inativo!");
        }
    }
}
