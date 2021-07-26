package fr.axa.formation.domain.comptes;

import fr.axa.formation.domain.AbstractQuery;
import fr.axa.formation.domain.errors.DomainException;
import fr.axa.formation.repository.comptes.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class GetCompteQuery extends AbstractQuery<String, Optional<Compte>> {

	private final CompteRepository compteRepository;
	private final CompteMapper mapper;

	@Autowired
	public GetCompteQuery(final CompteRepository compteRepository,
	                      final CompteMapper mapper) {
		this.compteRepository = compteRepository;
		this.mapper = mapper;
	}

	@Override
	public Optional<Compte> execute(final String query) {
		if (StringUtils.isEmpty(query)) {
			throw new DomainException(String.format("%s n'est pas un ID de compte valide", query));
		}
		try {
			return compteRepository.findById(query).map(mapper::toDomain);
		} catch (Exception e) {
			throw new DomainException(String.format("Impossible de récupérer le compte %s", query), e);
		}
	}
}
