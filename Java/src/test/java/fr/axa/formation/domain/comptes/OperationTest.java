package fr.axa.formation.domain.comptes;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests Unitaires - Operation")
@ExtendWith(SoftAssertionsExtension.class)
class OperationTest {

    @DisplayName("debit: doit créer une opération avec un montant négatif")
    @Test
    void testMontantIsNegativWhenOperationIsDebit() {
        // Arrange
        final Operation operation = Operation.debit("ID", "LABEL", BigDecimal.TEN);
        // Act
        final BigDecimal montant = operation.getMontant();
        // Assert
        assertThat(montant).isNegative();
    }

    @DisplayName("credit: doit créer une opération avec un montant positif")
    @Test
    void testMontantIsPositivWhenOperationIsCredit() {
        // Arrange
        final Operation operation = Operation.credit("ID", "LABEL", BigDecimal.TEN);
        // Act
        final BigDecimal montant = operation.getMontant();
        // Assert
        assertThat(montant).isPositive();
    }

    @DisplayName("deux opérations doivent être égales quand elles ont le même ID")
    @Test
    void testEqualityIsTrueWhenSameIds() {
        // Arrange
        Operation x = new Operation.Builder()
                .withId("12345")
                .build();
        Operation y = new Operation.Builder()
                .withId("12345")
                .build();
        // Act
        final boolean equals = Objects.equals(x, y);
        // Assert
        assertThat(equals).isTrue();
    }

    @DisplayName("deux opérations ne doivent pas être égales quand elles ont un ID différent")
    @Test
    void testEqualityIsFalseWhenDiffrentIds() {
        // Arrange
        Operation x = new Operation.Builder().withId("12345").build();
        Operation y = new Operation.Builder().withId("54321").build();
        // Act
        final boolean equals = Objects.equals(x, y);
        // Assert
        assertThat(equals).isFalse();
    }

    @DisplayName("Builder: doit mapper tous les champs")
    @Test
    void testBuilderMapsAllFields(SoftAssertions softly) {
        // Arrange
        final String id = "ID";
        final String compteId = "COMPTE_ID";
        final String label = "LABEL";
        final BigDecimal montant = BigDecimal.TEN;
        final LocalDateTime date = LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0);
        // Act
        final Operation operation = new Operation.Builder()
                .withId(id)
                .withCompteId(compteId)
                .withLabel(label)
                .withMontant(montant)
                .withDate(date)
                .build();
        // Assert
        softly.assertThat(operation.getId()).isEqualTo(id);
        softly.assertThat(operation.getCompteId()).isEqualTo(compteId);
        softly.assertThat(operation.getLabel()).isEqualTo(label);
        softly.assertThat(operation.getMontant()).isEqualTo(montant);
        softly.assertThat(operation.getDate()).isEqualTo(date);
    }
}
