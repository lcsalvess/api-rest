package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.pacientes.DadosCadastroPaciente;
import med.voll.api.domain.pacientes.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

//A anotação @DataJpaTest é utilizada
//para testar uma interface Repository
@DataJpaTest
//Não substituir para não utilizar um banco de dados
//em memória (H2)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//para ler o application-test
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = cadastrarMedico("Robson Sardinha Mineiro","rsmineiro@uol.com.br", "105457", Especialidade.ORTOPEDIA);
        //when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAs10);
        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = cadastrarMedico("Robson Sardinha Mineiro","rsmineiro@uol.com.br", "105457", Especialidade.ORTOPEDIA);
        var paciente = cadastrarPaciente("Giulia Alves do Vale", "giuliavalves@outlook.com", "12345678998");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        entityManager.persist(new Consulta(null,medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome,email,crm,especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome,email,cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "11987245864",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
            return new DadosCadastroPaciente(
                nome,
                email,
                "11987245864",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "Rua Desidério Jorge",
                "Vila Natal",
                "08795070",
                "Mogi das Cruzes",
                "SP",
                "281",
                null
        );
    }
}