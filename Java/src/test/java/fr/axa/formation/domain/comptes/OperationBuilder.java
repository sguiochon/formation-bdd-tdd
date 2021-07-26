package fr.axa.formation.domain.comptes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class OperationBuilder {

    public static Operation credit() {
        return new Operation.Builder()
                .withId("1")
                .withMontant(new BigDecimal("987.65"))
                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .withLabel("Crédit")
                .build();
    }

    public static Operation debit() {
        return new Operation.Builder()
                .withId("2")
                .withMontant(new BigDecimal("-123.45"))
                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .withLabel("Débit")
                .build();
    }
}
