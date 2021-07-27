package fr.axa.formation.domain.comptes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests Unitaires - Compte")
class CompteTest {

    @DisplayName("Deux comptes doivent être égaux quand ils ont le même ID")
    @Test
    void shouldBeEquals_whenHavingSameId() {
        // Arrange
        Compte x = new Compte.Builder()
                .withId("12345")
                .build();
        Compte y = new Compte.Builder()
                .withId("12345")
                .build();

        // Act
        final boolean equals = Objects.equals(x, y);

        // Assert
        assertThat(equals).isTrue();
    }

    @DisplayName("Deux comptes ne doivent pas être égaux quand ils ont un ID différent")
    @Test
    void shouldNotBeEquals_whenHavingDifferentId() {
        // Arrange
        Compte x = new Compte.Builder()
                .withId("12345")
                .build();
        Compte y = new Compte.Builder()
                .withId("54321")
                .build();

        // Act
        final boolean equals = Objects.equals(x, y);

        // Assert
        assertThat(equals).isFalse();
    }

    @DisplayName("getOperations: doit retourner une copie")
    @Test
    void getOperations_shouldReturnCopyOfOperations() {
        // Arrange
        final Compte compte = new Compte.Builder()
                .addOperation(OperationBuilder.credit())
                .build();

        // Act
        compte.getOperations().add(OperationBuilder.credit());

        // Assert
        assertThat(compte.getOperations()).hasSize(1);
    }

    @DisplayName("getMontant: doit retourner 0 quand il n'y a pas d'opérations")
    @Test
    void getMontant_shouldReturn0_whenNoOperations() {
        // Arrange
        final Compte compte = new Compte.Builder()
                .withOperations(new HashSet<>())
                .build();

        // Act
        final BigDecimal amount = compte.getMontant();

        // Assert
        assertThat(amount).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("getMontant: doit retourner 0 quand les opérations sont null")
    @Test
    void getMontant_shouldReturn0_whenOperationsNull() {
        // Arrange
        final Compte compte = new Compte.Builder()
                .withOperations(null)
                .build();
        // Act
        final BigDecimal amount = compte.getMontant();
        // Assert
        assertThat(amount).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("getMontant: doit retourner la somme des montants des opérations")
    @Test
    void getMontant_shouldSumMontantOfOperations() {
        // Arrange
        final Compte compte = new Compte.Builder()
                .addOperation(
                        new Operation.Builder()
                                .withId("1")
                                .withMontant(BigDecimal.valueOf(50))
                                .build()
                )
                .addOperation(
                        new Operation.Builder()
                                .withId("2")
                                .withMontant(BigDecimal.valueOf(-20))
                                .build()
                )
                .build();
        // Act
        final BigDecimal amount = compte.getMontant();
        // Assert
        assertThat(amount).isEqualTo(BigDecimal.valueOf(30));
    }

    @DisplayName("hasPlafondVirement: doit retourner vrai quand le compte a un plafond de virement")
    @Test
    void hasPlafondVirement_shouldReturnTrue_whenPlanfondVirementPresent() {
        // Arrange
        final Compte compte = new Compte.Builder()
                .withPlafondVirement(BigDecimal.ONE)
                .build();
        // Act
        final boolean hasPlafondVirement = compte.hasPlafondVirement();
        // Assert
        assertThat(hasPlafondVirement).isTrue();
    }

    @DisplayName("hasPlafondVirement: doit retourner faux quand le compte n'a pas de plafond de virement")
    @Test
    void hasPlafondVirement_shouldReturnFalse_whenPlanfondVirementNotPresent() {
        // Arrange
        final Compte compte = new Compte.Builder()
                .withPlafondVirement(null)
                .build();
        // Act
        final boolean hasPlafondVirement = compte.hasPlafondVirement();
        // Assert
        assertThat(hasPlafondVirement).isFalse();
    }
}
