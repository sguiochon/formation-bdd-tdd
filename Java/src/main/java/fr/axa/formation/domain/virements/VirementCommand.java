package fr.axa.formation.domain.virements;

import fr.axa.formation.domain.AbstractCommand;
import org.hibernate.bytecode.spi.NotInstrumentedException;
import org.springframework.stereotype.Service;

@Service
public class VirementCommand extends AbstractCommand<VirementCommandRequest, VirementStatus> {


	public VirementCommand() {
		// Ce constructeur sera enrichi ultérieurement...
	}

	@Override
	public VirementStatus execute(final VirementCommandRequest query) {
		throw new NotInstrumentedException("je ne sais pas quoi faire!");
	}
}
