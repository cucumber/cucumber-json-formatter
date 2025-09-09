package io.cucumber.jsonformatter

import io.cucumber.docstring.DocString
import io.cucumber.datatable.DataTable
import io.cucumber.scala.{EN, PendingException, ScalaDsl, Scenario}
import java.nio.charset.StandardCharsets.UTF_8;

private object StepDefinitions {
  var decay = 0;
  var scenario: Scenario = null;
}

class StepDefinitions extends ScalaDsl with EN {

  Before("@attachments") { scenario: Scenario =>
    StepDefinitions.scenario = scenario;
  }

  Before("@failing_before") { scenario: Scenario =>
    throw new RuntimeException("failing before hook")
  }
  
  After("@failing_after") { scenario: Scenario =>
    throw new RuntimeException("failing after hook")
  }
  
  BeforeStep("@failing_before_step") { scenario: Scenario =>
    throw new RuntimeException("failing before step hook")
  }

  AfterStep("@failing_after_step") { scenario: Scenario =>
    throw new RuntimeException("failing after step hook")
  }

  Given("^.*pass.*$") { () =>

  }

  Given("^.*docstring.*$") { (string: DocString) =>

  }

  Given("^.*datatable.*$") { (table: DataTable) =>

  }

  Given("^.*pending.*$") { () => {
    throw new PendingException();
  }}

  Given("^.*fail.*$") { () => {
    throw new RuntimeException("this step failed")
  }}

  Given("^.*decaying.*$") { () =>
    val failing = StepDefinitions.decay > 0;
    StepDefinitions.decay += 1;
    if (failing) throw new RuntimeException("Decayed")
  }

  Given("^I have (\\d+) cukes(?: in my (.*))?$") { (count: Integer, something: String) =>

  }

  Given("^.*attach.*$") { () =>
    StepDefinitions.scenario.log("Hello world");
    StepDefinitions.scenario.attach("Hello world", "application/plain", "hello.txt");
    StepDefinitions.scenario.attach("Hello world".getBytes(UTF_8), "application/plain", "hello.bin");
  }
}
