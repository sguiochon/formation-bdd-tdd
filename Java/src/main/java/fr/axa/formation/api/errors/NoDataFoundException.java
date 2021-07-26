package fr.axa.formation.api.errors;

public class NoDataFoundException extends Exception{

	public NoDataFoundException(String dataType, String id) {
		super(String.format("Aucun(e) %s trouvé(e) avec l'identifiant %s", dataType, id));
	}
}
