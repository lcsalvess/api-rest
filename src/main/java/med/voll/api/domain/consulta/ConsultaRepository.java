package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndDataConsultaBetween(Long idPaciente, LocalDateTime dataInicio, LocalDateTime dataFim);

    boolean existsByMedicoIdAndDataConsulta(Long idMedico, LocalDateTime dataConsulta);
}
