package io.cucumber.jsonschema;


import io.cucumber.java8.En;
import io.cucumber.java8.PendingException;

public class StepDefinitions implements En {
    private static int decay = 0;

    public StepDefinitions() {
        Before("@failing_before", () -> {
            throw new RuntimeException("failing before hook");
        });

        After("@failing_after", () -> {
            throw new RuntimeException("failing after hook");

        });

        Given("^.*pass.*$", () -> {

        });

        Given("^.*pending.*$", () -> {
            throw new PendingException();
        });

        Given("^.*fail.*$", () -> {
            throw new RuntimeException("this step failed");
        });

        Given("^.*decaying.*$", () -> {
            boolean failing = decay > 0;
            decay++;
            if (failing) throw new RuntimeException("Decayed");
        });

        Given("^I have (\\d+) cukes in my (.*)$", (Integer count, String something) -> {
            
        });
    }
}
