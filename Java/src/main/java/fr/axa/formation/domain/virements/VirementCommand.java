package fr.axa.formation.domain.virements;

import fr.axa.formation.domain.AbstractCommand;
import fr.axa.formation.domain.comptes.AddOperationsCommand;
import fr.axa.formation.domain.comptes.Compte;
import fr.axa.formation.domain.comptes.GetCompteQuery;
import fr.axa.formation.domain.comptes.Operation;
import org.hibernate.bytecode.spi.NotInstrumentedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class VirementCommand implements AbstractCommand<VirementCommandRequest, VirementStatus> {

	final AddOperationsCommand addOperationsCommand;
	final GetCompteQuery getCompteQuery;

	@Autowired
	public VirementCommand(AddOperationsCommand addOperationsCommand, GetCompteQuery getCompteQuery) {
		this.addOperationsCommand = addOperationsCommand;
		this.getCompteQuery = getCompteQuery;
	}

	@Override
	public VirementStatus execute(final VirementCommandRequest query) {

		String depuisCompteId = query.getDepuisCompte();
		String versCompteId = query.getVersCompte();

		Optional<Compte> optionalCompteDepuis = getCompteQuery.execute(depuisCompteId);
		Optional<Compte> optionalCompteVers = getCompteQuery.execute(versCompteId);

		if (isMontantNegatif(query.getMontant())){
			return VirementStatus.REJETE_MONTANT_NEGATIF;
		}
		if (!optionalCompteDepuis.isPresent()){
			return VirementStatus.REJETE_COMPTE_SOURCE_INEXISTANT;
		}
		if (!optionalCompteVers.isPresent()){
			return VirementStatus.REJETE_COMPTE_CIBLE_INEXISTANT;
		}
		if (Objects.equals(depuisCompteId, versCompteId)){
			return VirementStatus.REJETE_COMPTES_SOURCE_ET_CIBLE_IDENTIQUES;
		}
		if (hasMontantLowerThan(optionalCompteDepuis.get(), query.getMontant())){
			return VirementStatus.REJETE_SOLDE_INSUFFISANT;
		}
		if (isPlafondVirementReached(optionalCompteDepuis.get(), query.getMontant())){
			return VirementStatus.REJETE_PLAFOND_VIREMENT_COMPTE_SOURCE_DEPASSE;
		}

		List<Operation> operationList = new ArrayList<>(2);
		operationList.add(Operation.debit(depuisCompteId, "débit", query.getMontant()));
		operationList.add(Operation.credit(versCompteId, "crédit", query.getMontant()));
		addOperationsCommand.execute(operationList);

		return VirementStatus.CONFIRME;
	}

	private static boolean isMontantNegatif(BigDecimal montant) {
		return (montant != null) && (montant.compareTo(BigDecimal.ZERO) < 0);
	}

	public static boolean hasMontantLowerThan(Compte compte, BigDecimal amount){
		return compte.getMontant().compareTo(amount) < 0;
	}

	public static boolean isPlafondVirementReached(Compte compte, BigDecimal amount){
		return compte.hasPlafondVirement() && (compte.getPlafondVirement().compareTo(amount) < 0);
	}

}
