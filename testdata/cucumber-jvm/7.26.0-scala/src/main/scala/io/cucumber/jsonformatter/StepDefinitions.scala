package io.cucumber.jsonformatter

import io.cucumber.scala.{EN, ScalaDsl, Scenario, PendingException}

private object StepDefinitions {
  var decay = 0;
}

class StepDefinitions extends ScalaDsl with EN {

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

  Given("^I have (\\d+) cukes in my (.*)$") { (count: Integer, something: String) =>

  }
}
