package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoConsultas {
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var possuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataConsulta
                (dados.idMedico(), dados.dataConsulta());
        if (possuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("O horário selecionado não está disponível");
        }
    }
}
