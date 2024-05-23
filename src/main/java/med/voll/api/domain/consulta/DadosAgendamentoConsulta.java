package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(Long idMedico,
                                       @NotNull
                                       Long idPaciente,
                                       @NotNull
                                       //data no futuro
                                       @Future
                                       LocalDateTime dataConsulta) {}
