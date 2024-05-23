package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicosInativos implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        //se não tiver informado médico de preferência
        if (dados.idMedico() == null) {
            return;
        }
        var medico = medicoRepository.findById(dados.idMedico()).get();
        var ativo = medico.getAtivo();
        if (!ativo) {
            throw new ValidacaoException("Médico inativo!");
        }
    }
}
