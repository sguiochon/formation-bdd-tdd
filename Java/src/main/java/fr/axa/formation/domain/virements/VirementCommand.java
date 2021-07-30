package fr.axa.formation.domain.virements;

import fr.axa.formation.domain.AbstractCommand;
import org.hibernate.bytecode.spi.NotInstrumentedException;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class VirementCommand implements AbstractCommand<VirementCommandRequest, VirementStatus> {


	public VirementCommand() {
		// Ce constructeur sera enrichi ultérieurement...
	}

	@Override
	public VirementStatus execute(final VirementCommandRequest query) {
		return VirementStatus.CONFIRME;
	}
}
