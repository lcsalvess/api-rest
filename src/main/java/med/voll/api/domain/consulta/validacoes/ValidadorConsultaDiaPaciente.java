package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorConsultaDiaPaciente {

    private ConsultaRepository consultaRepository;

    public ValidadorConsultaDiaPaciente(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.dataConsulta().withHour(7);
        var ultimoHorario = dados.dataConsulta().withHour(18);
        var pacientePossuiOutraConsulta = consultaRepository.existsByPacienteIdAndDataConsultaBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsulta == true) {
            throw new ValidacaoException("Paciente possui outra consulta");
        }
    }
}
