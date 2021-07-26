package fr.axa.formation.domain.comptes;

import fr.axa.formation.repository.comptes.CompteDao;
import fr.axa.formation.repository.comptes.OperationDao;
import fr.axa.formation.repository.comptes.OperationDaoBuilder;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OperationMapper")
@ExtendWith(SoftAssertionsExtension.class)
class OperationMapperTest {

    private OperationMapper cut;

    @BeforeEach
    void setUp() {
        this.cut = new OperationMapper();
    }

    @DisplayName("toDomain: doit retourner une liste vide quand la liste d'input est null")
    @Test
    void toDomain_shouldReturnEmptyList_whenInputListNull() {
        // Arrange
        final Set<OperationDao> input = null;

        // Act
        final Set<Operation> operations = this.cut.toDomain(input);

        // Assert
        assertThat(operations).isEmpty();
    }

    @DisplayName("toDomain: doit retourner une null quand l'input est null")
    @Test
    void toDomain_shouldReturnNull_whenInputNull() {
        // Arrange
        final OperationDao input = null;

        // Act
        final Operation operation = this.cut.toDomain(input);

        // Assert
        assertThat(operation).isNull();
    }

    @DisplayName("toDomain: doit mapper tous les champs")
    @Test
    void toDomain_shouldMapAllValues_whenCompteNull(SoftAssertions softly) {
        // Arrange
        final OperationDao input = new OperationDao.Builder()
                .withId(new BigInteger("1"))
                .withCompte(null)
                .withLabel("LABEL")
                .withMontant(BigInteger.valueOf(1000))
                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();

        // Act
        final Operation operation = this.cut.toDomain(input);

        // Assert
        softly.assertThat(operation.getId()).isEqualTo("1");
        softly.assertThat(operation.getLabel()).isEqualTo("LABEL");
        softly.assertThat(operation.getMontant()).isEqualTo(new BigDecimal("10.00"));
        softly.assertThat(operation.getDate()).isEqualTo(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0));
    }

    @DisplayName("toDomain: doit mapper l'ID quand il est null")
    @Test
    void toDomain_shouldMapAllValues_whenIdNull(SoftAssertions softly) {
        // Arrange
        final OperationDao input = new OperationDao.Builder()
                .withId(null)
                .build();

        // Act
        final Operation operation = this.cut.toDomain(input);

        // Assert
        softly.assertThat(operation.getId()).isNull();
    }

    @DisplayName("toDomain: doit mapper le compteId")
    @Test
    void toDomain_shouldMapAllValues_whenCompteSet(SoftAssertions softly) {
        // Arrange
        final OperationDao input = new OperationDao.Builder()
                .withCompte(new CompteDao.Builder().withNumero("12345").build())
                .build();

        // Act
        final Operation operation = this.cut.toDomain(input);

        // Assert
        softly.assertThat(operation.getCompteId()).isEqualTo("12345");
    }

    @DisplayName("toDomain: doit supprimer les valeurs null du Set")
    @Test
    void toDomain_shouldRemoveNull_whenListContainsNull(SoftAssertions softly) {
        // Arrange
        final Set<OperationDao> input = new HashSet<>();
        input.add(OperationDaoBuilder.credit());
        input.add(null);

        // Act
        final Set<Operation> comptes = this.cut.toDomain(input);

        // Assert
        softly.assertThat(comptes).hasSize(1);
    }

    @DisplayName("toRepository: doit mapper tous les champs")
    @Test
    void toRepository_shouldMapAllValues(SoftAssertions softly) {
        // Arrange
        final Operation operation = new Operation.Builder()
                .withLabel("LABEL")
                .withCompteId("12345")
                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();

        // Act
        final OperationDao operationDao = this.cut.toRepository(operation);

        // Assert
        softly.assertThat(operationDao.getLabel()).isEqualTo("LABEL");
        softly.assertThat(operationDao.getDate()).isEqualTo(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0));
        softly.assertThat(operationDao.getCompte()).isNotNull();
        softly.assertThat(operationDao.getCompte().getNumero()).isEqualTo("12345");
    }
}
