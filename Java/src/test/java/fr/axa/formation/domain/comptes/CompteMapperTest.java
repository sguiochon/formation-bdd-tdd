package fr.axa.formation.domain.comptes;

import fr.axa.formation.repository.comptes.CompteDao;
import fr.axa.formation.repository.comptes.CompteDaoBuilder;
import fr.axa.formation.repository.comptes.OperationDao;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests Unitaires - CompteMapper")
@ExtendWith(SoftAssertionsExtension.class)
class CompteMapperTest {

    private CompteMapper cut;

    @BeforeEach
    void setUp() {
        this.cut = new CompteMapper(new OperationMapper());
    }

    @DisplayName("toDomain: doit retourner une liste vide quand la liste d'input est null")
    @Test
    void toDomain_shouldReturnEmptyList_whenInputListNull() {
        // Arrange
        final List<CompteDao> input = null;
        // Act
        final List<Compte> comptes = this.cut.toDomain(input);
        // Assert
        assertThat(comptes).isEmpty();
    }

    @DisplayName("toDomain: doit retourner une null quand l'input est null")
    @Test
    void toDomain_shouldReturnNull_whenInputNull() {
        // Arrange
        final CompteDao input = null;
        // Act
        final Compte compte = this.cut.toDomain(input);
        // Assert
        assertThat(compte).isNull();
    }

    @DisplayName("toDomain: doit mapper tous les champs")
    @Test
    void toDomain_shouldMapAllValues(SoftAssertions softly) {
        // Arrange
        final CompteDao input = new CompteDao.Builder()
                .withNumero("ID")
                .withLabel("LABEL")
                .withPlafondCompte(new BigInteger("12345"))
                .withPlafondVirement(new BigInteger("12345"))
                .addOperation(
                        new OperationDao.Builder()
                                .withMontant(new BigInteger("12345"))
                                .build()
                )
                .build();

        // Act
        final Compte compte = this.cut.toDomain(input);
        // Assert
        softly.assertThat(compte.getId()).isEqualTo("ID");
        softly.assertThat(compte.getLabel()).isEqualTo("LABEL");
        softly.assertThat(compte.getPlafondCompte()).isEqualTo("123.45");
        softly.assertThat(compte.getPlafondVirement()).isEqualTo("123.45");
        softly.assertThat(compte.getOperations()).hasSize(1);
    }

    @DisplayName("toDomain: doit mapper plafondCompte et plafondVirement")
    @Test
    void toDomain_shouldMapNull_whenLimitNull(SoftAssertions softly) {
        // Arrange
        final CompteDao input = new CompteDao.Builder()
                .withPlafondCompte(null)
                .withPlafondVirement(null)
                .build();
        // Act
        final Compte compte = this.cut.toDomain(input);
        // Assert
        softly.assertThat(compte.getPlafondCompte()).isNull();
        softly.assertThat(compte.getPlafondVirement()).isNull();
    }

    @DisplayName("toDomain: doit supprimer les valeurs null de la List")
    @Test
    void toDomain_shouldRemoveNull_whenListContainsNull(SoftAssertions softly) {
        // Arrange
        final List<CompteDao> input = Arrays.asList(
                CompteDaoBuilder.compteCourant(),
                null
        );
        // Act
        final List<Compte> comptes = this.cut.toDomain(input);
        // Assert
        softly.assertThat(comptes).hasSize(1);
    }

}
