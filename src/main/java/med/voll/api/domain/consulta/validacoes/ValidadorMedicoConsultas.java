package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConsultas implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var possuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData
                (dados.idMedico(), dados.data());
        if (possuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("O horário selecionado não está disponível");
        }
    }
}
