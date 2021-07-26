package fr.axa.formation.domain;

public abstract class AbstractCommand<Input, Output> {

    public abstract Output execute(Input query);

}
