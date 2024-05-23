package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

public class ValidadorMedicosInativos {
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
