package fr.axa.formation.api.comptes;

import fr.axa.formation.api.comptes.ComptesController;
import fr.axa.formation.api.comptes.ComptesViewModelMapper;
import fr.axa.formation.api.core.ControllerExceptionHandler;
import fr.axa.formation.domain.comptes.Compte;
import fr.axa.formation.domain.comptes.GetCompteQuery;
import fr.axa.formation.domain.comptes.GetComptesQuery;
import fr.axa.formation.domain.comptes.Operation;
import fr.axa.formation.domain.errors.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("Tests Unitaires - ComptesController")
@ExtendWith(MockitoExtension.class)
class ComptesControllerTest {

    @Mock
    private GetComptesQuery getComptesQueryMock;

    @Mock
    private GetCompteQuery getCompteQueryMock;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        final ComptesController controller = new ComptesController(getComptesQueryMock, getCompteQueryMock, new ComptesViewModelMapper());
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    private ResultActions performGetComptes() throws Throwable {
        return this.mvc.perform(get(ComptesController.GET_COMPTES_URL));
    }

    @DisplayName("/api/comptes doit retourner une liste de comptes avec les opérations")
    @Test
    void testGetComptesReturnsAListOfSerializedComptes() throws Throwable {
        // Arrange
        when(getComptesQueryMock.execute(any()))
                .thenReturn(Arrays.asList(
                        new Compte.Builder()
                                .withId("ID")
                                .withLabel("LABEL")
                                .withMontant(new BigDecimal("123.45"))
                                .addOperation(
                                        new Operation.Builder()
                                                .withId("ID_OPERATION")
                                                .withLabel("LABEL_OPERATION")
                                                .withMontant(new BigDecimal("543.21"))
                                                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                                        .build()
                                )
                                .build()
                ));
        // Act
        final ResultActions resultActions = performGetComptes();
        // Assert
        resultActions
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id").value("ID"))
                .andExpect(jsonPath("$.data[0].label").value("LABEL"))
                .andExpect(jsonPath("$.data[0].montant").value("123.45"))
                .andExpect(jsonPath("$.data[0].operations", hasSize(1)))
                .andExpect(jsonPath("$.data[0].operations[0].id").value("ID_OPERATION"))
                .andExpect(jsonPath("$.data[0].operations[0].label").value("LABEL_OPERATION"))
                .andExpect(jsonPath("$.data[0].operations[0].montant").value("543.21"))
                .andExpect(jsonPath("$.data[0].operations[0].date").value("2020-01-01T00:00:00.000"));
    }

    @DisplayName("/api/comptes/{id} doit renvoyer un code http 400 si aucun compte n'est trouvé")
    @Test
    public void testGetCompteReturnsHttpCode400WhenNoDataFound() throws Exception {
        // Arrange
        String simulatedCompteId = "invalid id";
        when(getCompteQueryMock.execute(eq(simulatedCompteId))).thenReturn(Optional.empty());
        // Act
        final ResultActions resultActions = mvc.perform(get(ComptesController.GET_COMPTES_URL + "/" + simulatedCompteId));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Aucun(e) compte trouvé(e) avec l'identifiant " + simulatedCompteId));
    }

    @DisplayName("/api/comptes/{id} doit renvoyer un code http 500 en cas de DomainException lancée par la couche service")
    @Test
    public void testGetCompteReturnsHttpCode500WhenDomainExceptionRaisedByService() throws Exception {
        // Arrange
        String simulatedCompteId = "any id";
        String simulatedErrorMessage = "Simulated error message";
        when(getCompteQueryMock.execute(anyString())).thenThrow(new DomainException(simulatedErrorMessage));
        // Act
        final ResultActions resultActions = mvc.perform(get(ComptesController.GET_COMPTES_URL + "/" + simulatedCompteId));
        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(jsonPath("$.message").value(simulatedErrorMessage));
    }
}
