package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorTempoAgendamento {

    public void validarHorarioAgendamento(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.dataConsulta();
        var horarioAgora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(horarioAgora, dataConsulta).toMinutes();
        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consultar deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
