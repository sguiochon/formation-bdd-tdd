package fr.axa.formation.repository.comptes;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;

@DisplayName("OperationDao")
@ExtendWith(SoftAssertionsExtension.class)
class OperationDaoTest {

    @DisplayName("Builder: doit mapper tous les champs")
    @Test
    void builder_shouldMapFields(SoftAssertions softly) {
        // Arrange
        final BigInteger id = BigInteger.ONE;
        final String label = "LABEL";
        final BigInteger montant = BigInteger.TEN;
        final LocalDateTime date = LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0);
        final CompteDao compte = CompteDaoBuilder.compteCourant();

        // Act
        final OperationDao operationDao = new OperationDao.Builder()
                .withId(id)
                .withLabel(label)
                .withMontant(montant)
                .withDate(date)
                .withCompte(compte)
                .build();

        // Assert
        softly.assertThat(operationDao.getId()).isEqualTo(id);
        softly.assertThat(operationDao.getLabel()).isEqualTo(label);
        softly.assertThat(operationDao.getMontant()).isEqualTo(montant);
        softly.assertThat(operationDao.getDate()).isEqualTo(date);
        softly.assertThat(operationDao.getCompte()).isEqualTo(compte);
    }
}
