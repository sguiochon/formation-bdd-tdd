package fr.axa.formation.repository.comptes;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests Unitaires - CompteDao")
@ExtendWith(SoftAssertionsExtension.class)
class CompteDaoTest {

    @DisplayName("Builder: doit mapper tous les champs en utilisant addOperation")
    @Test
    void builder_shouldMapFields_whenUsingAddOperation(SoftAssertions softly) {
        // Arrange
        final String numero = "1234567890";
        final String label = "LABEL";
        final BigInteger plafondCompte = BigInteger.TEN;
        final BigInteger plafondVirement = BigInteger.ONE;
        final OperationDao credit = OperationDaoBuilder.credit();
        final OperationDao debit = OperationDaoBuilder.debit();

        // Act
        final CompteDao compteDao = new CompteDao.Builder()
                .withNumero(numero)
                .withLabel(label)
                .withPlafondCompte(plafondCompte)
                .withPlafondVirement(plafondVirement)
                .addOperation(credit)
                .addOperation(debit)
                .build();

        // Assert
        softly.assertThat(compteDao.getNumero()).isEqualTo(numero);
        softly.assertThat(compteDao.getLabel()).isEqualTo(label);
        softly.assertThat(compteDao.getPlafondCompte()).isEqualTo(plafondCompte);
        softly.assertThat(compteDao.getPlafondVirement()).isEqualTo(plafondVirement);
        softly.assertThat(compteDao.getOperations()).containsExactlyInAnyOrder(credit, debit);
    }

    @DisplayName("Builder: doit mapper tous les champs en utilisant withOperations")
    @Test
    void builder_shouldMapFields_whenUsingWithOperations(SoftAssertions softly) {
        // Arrange
        final Set<OperationDao> operations = new HashSet<>();
        final OperationDao credit = OperationDaoBuilder.credit();
        final OperationDao debit = OperationDaoBuilder.debit();
        operations.add(credit);
        operations.add(debit);

        // Act
        final CompteDao compteDao = new CompteDao.Builder()
                .withOperations(operations)
                .build();

        // Assert
        softly.assertThat(compteDao.getOperations()).containsExactlyInAnyOrder(credit, debit);
    }

    @DisplayName("getOperations: doit retourner une copie")
    @Test
    void getOperations_shouldReturnCopyOfOperations() {
        // Arrange
        final CompteDao compteDao = new CompteDao.Builder()
                .addOperation(OperationDaoBuilder.credit())
                .build();

        // Act
        compteDao.getOperations().add(OperationDaoBuilder.credit());

        // Assert
        assertThat(compteDao.getOperations()).hasSize(1);
    }
}
