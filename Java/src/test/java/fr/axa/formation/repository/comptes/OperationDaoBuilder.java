package fr.axa.formation.repository.comptes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;

public class OperationDaoBuilder {

    public static OperationDao credit() {
        return new OperationDao.Builder()
                .withId(BigInteger.ONE)
                .withMontant(new BigInteger("98765"))
                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .withLabel("Crédit")
                .build();
    }

    public static OperationDao debit() {
        return new OperationDao.Builder()
                .withId(BigInteger.ONE)
                .withMontant(new BigInteger("-12345"))
                .withDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .withLabel("Débit")
                .build();
    }
}
