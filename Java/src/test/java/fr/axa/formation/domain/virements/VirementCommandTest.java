package fr.axa.formation.domain.virements;

import fr.axa.formation.domain.comptes.AddOperationsCommand;
import fr.axa.formation.domain.comptes.Compte;
import fr.axa.formation.domain.comptes.GetCompteQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Tests Unitaires - VirementCommand")
@ExtendWith(MockitoExtension.class)
class VirementCommandTest {

	@Mock
	AddOperationsCommand addOperationsCommand;

	@Mock
	GetCompteQuery getCompteQuery;

	VirementCommand virementCommand;

	@BeforeEach
	public void setUp() {
		virementCommand = new VirementCommand(addOperationsCommand, getCompteQuery);
	}

	@Test
	public void testExecuteReturnsRejetWhenMontantCompteSourceInsuffisant() {
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(100000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(50000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).build());
		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		BigDecimal montantCompteCible = BigDecimal.valueOf(8000, 2);
		Optional<Compte> optionalCompteCible = Optional.of(new Compte.Builder().withMontant(montantCompteCible).build());
		when(getCompteQuery.execute(compteCibleId)).thenReturn(optionalCompteCible);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_SOLDE_INSUFFISANT, virementStatus, "Le virement est rejeté");
	}

	@Test
	public void testExecuteReturnsConfirmeWhenMontantCompteSourceSuffisant() {
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(100000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).build());
		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		BigDecimal montantCompteCible = BigDecimal.valueOf(8000, 2);
		Optional<Compte> optionalCompteCible = Optional.of(new Compte.Builder().withMontant(montantCompteCible).build());
		when(getCompteQuery.execute(compteCibleId)).thenReturn(optionalCompteCible);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.CONFIRME, virementStatus, "Le virement est confirmé");
	}

	@Test
	public void testExecuteReturnsRejetWhenPlafondTransfertDepasse() {
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(100000, 2);
		BigDecimal plafondVirement = BigDecimal.valueOf(40000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).withPlafondVirement(plafondVirement).build());
		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		BigDecimal montantCompteCible = BigDecimal.valueOf(8000, 2);
		Optional<Compte> optionalCompteCible = Optional.of(new Compte.Builder().withMontant(montantCompteCible).build());
		when(getCompteQuery.execute(compteCibleId)).thenReturn(optionalCompteCible);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_PLAFOND_VIREMENT_COMPTE_SOURCE_DEPASSE, virementStatus, "Le virement est rejeté (plafond virement dépassé)");
	}

	@Test
	public void testExecuteReturnsRejetWhenMontantTransfertNegatif() {
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(-50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(100000, 2);
		BigDecimal plafondVirement = BigDecimal.valueOf(60000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).withPlafondVirement(plafondVirement).build());
		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		BigDecimal montantCompteCible = BigDecimal.valueOf(8000, 2);
		Optional<Compte> optionalCompteCible = Optional.of(new Compte.Builder().withMontant(montantCompteCible).build());
		when(getCompteQuery.execute(compteCibleId)).thenReturn(optionalCompteCible);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_MONTANT_NEGATIF, virementStatus, "Le virement est rejeté (montant négatif)");
	}

	@Test
	public void testExecuteReturnsRejetWhenCompteSourceInexistant() {
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		when(getCompteQuery.execute(compteSourceId)).thenReturn(Optional.empty());

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_COMPTE_SOURCE_INEXISTANT, virementStatus, "Le virement est rejeté (compte source inexistant)");
	}

	@Test
	public void testExecuteReturnsRejetWhenCompteDestinataireInexistant() {
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(100000, 2);
		BigDecimal plafondVirement = BigDecimal.valueOf(60000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).withPlafondVirement(plafondVirement).build());
		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		when(getCompteQuery.execute(compteCibleId)).thenReturn(Optional.empty());

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_COMPTE_CIBLE_INEXISTANT, virementStatus, "Le virement est rejeté (compte destinataire inexistant)");
	}

	@Test
	public void testExecuteReturnsRejetWhenCompteSourceEtCompteDestinataireIdentique() {
		// Arrange
		String compteSourceId = "compte id";
		String compteCibleId = "compte id";
		BigDecimal montantTransfert = BigDecimal.valueOf(50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(100000, 2);
		BigDecimal plafondVirement = BigDecimal.valueOf(60000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).withPlafondVirement(plafondVirement).build());
		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		when(getCompteQuery.execute(compteCibleId)).thenReturn(optionalCompteSource);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_COMPTES_SOURCE_ET_CIBLE_IDENTIQUES, virementStatus, "Le virement est rejeté (compte source et destinataire identiques)");
	}

}