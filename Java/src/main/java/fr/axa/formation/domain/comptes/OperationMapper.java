package fr.axa.formation.domain.comptes;

import fr.axa.formation.core.CurrencyUtils;
import fr.axa.formation.repository.comptes.CompteDao;
import fr.axa.formation.repository.comptes.OperationDao;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationMapper {

    public Set<Operation> toDomain(final Set<OperationDao> from) {
        if (from == null) {
            return new HashSet<>();
        }

        return from
                .stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Operation toDomain(final OperationDao from) {
        if (from == null) {
            return null;
        }

        final Operation.Builder builder = new Operation.Builder();

        if(from.getId() != null) {
            builder.withId(from.getId().toString());
        }

        if (from.getCompte() != null) {
            builder.withCompteId(from.getCompte().getNumero());
        }

        builder.withLabel(from.getLabel());
        builder.withMontant(CurrencyUtils.toUnit(from.getMontant()));
        builder.withDate(from.getDate());

        return builder.build();
    }

    public OperationDao toRepository(final Operation from) {
        final OperationDao.Builder builder = new OperationDao.Builder();

        builder.withCompte(new CompteDao.Builder().withNumero(from.getCompteId()).build());
        builder.withLabel(from.getLabel());
        builder.withMontant(CurrencyUtils.toCents(from.getMontant()));
        builder.withDate(from.getDate());

        return builder.build();
    }

}
