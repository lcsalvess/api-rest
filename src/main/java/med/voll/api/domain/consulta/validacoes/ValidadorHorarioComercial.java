package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

public class ValidadorHorarioComercial {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.dataConsulta();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAberturaClinica = dataConsulta.getHour() < 7;
        var horarioFechamentoClinica = dataConsulta.getHour() > 18;
        if (domingo||horarioAberturaClinica||horarioFechamentoClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento!");
        }
    }
}
