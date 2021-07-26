package fr.axa.formation.repository.comptes;

public class CompteDaoBuilder {

    public static CompteDao compteCourant() {
        return new CompteDao.Builder()
                .withNumero("123456789")
                .withLabel("Compte courant")
                .addOperation(OperationDaoBuilder.credit())
                .build();
    }
}
