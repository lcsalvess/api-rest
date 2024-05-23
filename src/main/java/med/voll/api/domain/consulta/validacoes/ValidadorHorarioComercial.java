package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioComercial implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAberturaClinica = dataConsulta.getHour() < 7;
        var horarioFechamentoClinica = dataConsulta.getHour() > 18;
        if (domingo||horarioAberturaClinica||horarioFechamentoClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento!");
        }
    }
}
