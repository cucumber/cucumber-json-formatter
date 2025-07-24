package io.cucumber.jsonformatter;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.PendingException;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;

public class StepDefinitions {
    private static int decay = 0;

    @Before("@failing_before")
    public void tagged_failing_before_hook() {
        throw new RuntimeException("failing before step hook");
    }

    @After("@failing_after")
    public void tagged_failing_after_hook() {
        throw new RuntimeException("failing after step hook");
    }

    @BeforeStep("@failing_before_step")
    public void tagged_failing_before_step_hook() {
        throw new RuntimeException("failing before hook");
    }

    @AfterStep("@failing_after_step")
    public void tagged_failing_after_step_hook() {
        throw new RuntimeException("failing after hook");
    }

    @Given("^.*pass.*$")
    public void passing_step() {
    }
    
    @Given("^.*docstring.*$")
    public void passing_step(DocString string) {
    }
    
    @Given("^.*datatable.*$")
    public void passing_step(DataTable table) {
    }

    @Given("^.*pending.*$")
    public void pending_step() {
        throw new PendingException();
    }

    @Given("^.*fail.*$")
    public void failing_step() {
        throw new RuntimeException("this step failed");
    }

    @Given("^.*decaying.*$")
    public void decaying_step() {
        boolean failing = decay > 0;
        decay++;
        if (failing) throw new RuntimeException("Decayed");
    }

    @Given("^I have (\\d+) cukes in my (.*)$")
    public void cukes_in_something(int count, String something) {

    }
}
