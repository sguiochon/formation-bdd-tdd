package fr.axa.formation.domain.comptes;

import fr.axa.formation.core.CurrencyUtils;
import fr.axa.formation.repository.comptes.CompteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompteMapper {

    private final OperationMapper operationMapper;

    @Autowired
    public CompteMapper(OperationMapper operationMapper) {
        this.operationMapper = operationMapper;
    }

    public List<Compte> toDomain(List<CompteDao> from) {
        if (from == null) {
            return new ArrayList<>();
        }

        return from
                .stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Compte toDomain(final CompteDao from) {
        if (from == null) {
            return null;
        }

        return new Compte(
                from.getNumero(),
                from.getLabel(),
                operationMapper.toDomain(from.getOperations()),
                CurrencyUtils.toUnit(from.getPlafondVirement()),
                CurrencyUtils.toUnit(from.getPlafondCompte())
        );
    }

}
