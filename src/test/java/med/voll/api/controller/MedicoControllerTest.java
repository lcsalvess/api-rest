package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> jsonCadastroMedico;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> jsonDetalhamentoMedico;

    @MockBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando informações forem inválidas")
    @WithMockUser
    void cadastrarMedico_cenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos/cadastro"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 200 quando informações forem válidas")
    @WithMockUser
    void cadastrarMedico_cenario2() throws Exception {
        var especialidade = Especialidade.DERMATOLOGIA;
        var dadosCadastro = new DadosCadastroMedico
                ("Lucas Alves Sardinha Mineiro", "lucas0asm@outlook.com",
                        "11942473295", "942473", especialidade, dadosEndereco());
        when(medicoRepository.save(any()))
                .thenReturn(new Medico(dadosCadastro));
        var response = mvc
                .perform(
                        post("/medicos/cadastro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonCadastroMedico.write(dadosCadastro).getJson()))
                .andReturn().getResponse();
        var dadosDetalhamento = new DadosDetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = jsonDetalhamentoMedico.write(dadosDetalhamento).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "Rua Jose Moretti",
                "Vila Natal",
                "08795080",
                "Mogi das Cruzes",
                "SP",
                "356",
                null);
    }

}