package io.cucumber.jsonschema;


import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
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
        Before("@failing_before_step", () -> {
            throw new RuntimeException("failing before step hook");
        });

        After("@failing_after_step", () -> {
            throw new RuntimeException("failing after step hook");

        });

        Given("^.*pass.*$", () -> {

        });

        Given("^.*docstring.*$", (DocString string) -> {

        });

        Given("^.*datatable.*$", (DataTable table) -> {

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
