package fr.axa.formation.domain.comptes;

import fr.axa.formation.domain.errors.DomainException;
import fr.axa.formation.domain.AbstractQuery;
import fr.axa.formation.repository.comptes.CompteDao;
import fr.axa.formation.repository.comptes.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComptesQuery implements AbstractQuery<Void, List<Compte>> {

    private final CompteRepository compteRepository;
    private final CompteMapper mapper;

    @Autowired
    public GetComptesQuery(final CompteRepository compteRepository,
                           final CompteMapper mapper) {
        this.compteRepository = compteRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Compte> execute(final Void query) {
        try {
            final List<CompteDao> compteDaos = compteRepository.findAll();
            return mapper.toDomain(compteDaos);
        } catch (Exception e) {
            throw new DomainException("Impossible de récupérer la liste de comptes", e);
        }
    }
}
