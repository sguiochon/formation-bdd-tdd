package fr.axa.formation.api.virements;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axa.formation.api.core.ControllerExceptionHandler;
import fr.axa.formation.domain.virements.VirementCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("Virement API")
@ExtendWith(MockitoExtension.class)
class VirementControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

    private MockMvc mvc;

    @BeforeEach
    void beforeEach() {
        VirementController controller = new VirementController();
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void testPostReturnsCodeHttp200Anytime() throws Throwable {
        // Arrange
        // Act
        final ResultActions resultActions = performPostVirement(null);
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private ResultActions performPostVirement(final VirementRequest request) throws Throwable {
        return this.mvc.perform(
                post(VirementController.POST_VIREMENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request))
        );
    }

}
