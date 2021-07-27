package fr.axa.formation.domain.comptes;

import fr.axa.formation.domain.errors.DomainException;
import fr.axa.formation.repository.comptes.CompteDaoBuilder;
import fr.axa.formation.repository.comptes.CompteRepository;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests Unitaires - GetCompteQuery")
@ExtendWith(MockitoExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class GetCompteQueryTest {

    @Mock
    private CompteRepository compteRepositoryMock;

    private GetCompteQuery getCompteQuery;

    @BeforeEach
    void setUp() {
        this.getCompteQuery = new GetCompteQuery(
                compteRepositoryMock,
                new CompteMapper(new OperationMapper())
        );
    }

    @DisplayName("doit appeler le repository")
    @Test
    void shouldCallRepository() {
        // Arrange
        final String query = "12345";
        when(compteRepositoryMock.findById(anyString()))
                .thenReturn(Optional.of(CompteDaoBuilder.compteCourant()));

        // Act
        final Optional<Compte> maybeCompte = this.getCompteQuery.execute(query);

        // Assert
        verify(compteRepositoryMock).findById(eq(query));
        assertThat(maybeCompte).isNotNull();
    }

    @DisplayName("doit déclencher une DomainException quand la query n'est pas valide")
    @Test
    void shouldThrowDomainException_whenQueryNotValid() {
        // Arrange
        final String query = null;
        // Act
        final Throwable throwable = catchThrowable(() -> this.getCompteQuery.execute(query));
        // Assert
        assertThat(throwable)
                .isInstanceOf(DomainException.class)
                .hasMessage("null n'est pas un ID de compte valide");
    }

}
