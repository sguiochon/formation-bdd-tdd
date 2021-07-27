package fr.axa.formation.domain.comptes;

import fr.axa.formation.repository.comptes.CompteRepository;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests Unitaires - GetComptesQuery")
@ExtendWith(MockitoExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class GetComptesQueryTest {

    @Mock
    private CompteRepository compteRepositoryMock;

    private GetComptesQuery cut;

    @BeforeEach
    void setUp() {
        this.cut = new GetComptesQuery(
                compteRepositoryMock,
                new CompteMapper(new OperationMapper())
        );
    }

    @DisplayName("doit appeler le repository")
    @Test
    void shouldCallRepository() {
        // Arrange
        when(compteRepositoryMock.findAll())
                .thenReturn(new ArrayList<>());

        // Act
        final List<Compte> comptes = this.cut.execute(null);

        // Assert
        verify(compteRepositoryMock).findAll();
        assertThat(comptes).isNotNull();
    }
}
