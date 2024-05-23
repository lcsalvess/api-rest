package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorTempoAgendamento implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var horarioAgora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(horarioAgora, dataConsulta).toMinutes();
        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consultar deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
