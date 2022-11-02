package cl.dcid.sumador.controller;

import cl.dcid.sumador.dto.SigninRequest;
import cl.dcid.sumador.dto.SigninResult;
import cl.dcid.sumador.dto.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SumControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void noCredentialsShouldReturnUnauthorized() throws Exception {

        MockHttpServletResponse response = mvc.perform(
                        get("/suma")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void withCredentialsShouldReturnOk() throws Exception {

        final String password = "12345678";
        final String email = "testemail@123.com";
        final String username = "testusername";

        ObjectMapper mapper = new ObjectMapper();

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);

        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setUsername(username);
        signinRequest.setPassword(password);

        MockHttpServletResponse signupResponse = mvc.perform(
                        post("/auth/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(signupRequest)))
                .andReturn().getResponse();

        MockHttpServletResponse signinResponse = mvc.perform(
                        post("/auth/signin")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(signinRequest)))
                .andReturn().getResponse();

        SigninResult signinResult = mapper.readValue(signinResponse.getContentAsString(), SigninResult.class);

        MockHttpServletResponse sumaResponse = mvc.perform(
                        get("/suma?sumando1=100&sumando2=200")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + signinResult.getAccessToken()))
                .andReturn().getResponse();

        assertThat(sumaResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


}
