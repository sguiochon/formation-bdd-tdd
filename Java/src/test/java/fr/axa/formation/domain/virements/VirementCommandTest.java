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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
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
	public void setUp(){
		virementCommand = new VirementCommand(addOperationsCommand, getCompteQuery);
	}

	@Test
	public void testExecuteReturnsRejetWhenMontantCompteSourceInsuffisant(){
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(100000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(50000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).build());

		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.REJETE_SOLDE_INSUFFISANT, virementStatus, "Le virement est rejeté");
	}

	@Test
	public void testExecuteReturnsConfirmeWhenMontantCompteSourceSuffisant(){
		// Arrange
		String compteSourceId = "compte source id";
		String compteCibleId = "compte cible id";
		BigDecimal montantTransfert = BigDecimal.valueOf(50000, 2);
		VirementCommandRequest virementCommandRequest = new VirementCommandRequest(compteSourceId, compteCibleId, montantTransfert);

		BigDecimal montantCompteSource = BigDecimal.valueOf(100000, 2);
		Optional<Compte> optionalCompteSource = Optional.of(new Compte.Builder().withMontant(montantCompteSource).build());

		when(getCompteQuery.execute(compteSourceId)).thenReturn(optionalCompteSource);

		// Act
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);

		// Assert
		assertEquals(VirementStatus.CONFIRME, virementStatus, "Le virement est rejeté");
	}

}