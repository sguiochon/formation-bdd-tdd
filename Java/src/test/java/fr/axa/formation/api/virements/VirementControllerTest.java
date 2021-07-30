package fr.axa.formation.api.virements;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axa.formation.api.core.ControllerExceptionHandler;
import fr.axa.formation.domain.virements.VirementCommand;
import fr.axa.formation.domain.virements.VirementStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("Tests Unitaires - VirementController")
@ExtendWith(MockitoExtension.class)
class VirementControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

    private MockMvc mvc;

    VirementRequestMapper virementRequestMapper;

    @Mock
    VirementCommand virementCommand;

    @BeforeEach
    void beforeEach() {
        virementRequestMapper = new VirementRequestMapper();
        VirementController controller = new VirementController(virementRequestMapper, virementCommand);
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void testPostReturnsCodeHttp200WhenVirementCommandReturnsVirementCONFIRME() throws Throwable {
        // Arrange
        String compteSourceId = "compte source ID";
        String compteCibleId = "compte cile ID";
        BigDecimal montant = BigDecimal.valueOf(50000, 2);
        VirementRequest virementRequest = new VirementRequest(compteSourceId, compteCibleId, montant);

        when(virementCommand.execute(any())).thenReturn(VirementStatus.CONFIRME);

        // Act
        final ResultActions resultActions = performPostVirement(virementRequest);
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
