package io.cucumber.jsonformatter;

import io.cucumber.messages.types.TestStepFinished;

import java.util.List;
import java.util.Map;

final class TestStepData {
    final List<TestStepFinished> beforeTestCaseSteps;
    final List<TestStepFinished> afterTestCaseSteps;
    final Map<TestStepFinished, List<TestStepFinished>> beforeStepStepsByStep;
    final Map<TestStepFinished, List<TestStepFinished>> afterStepStepsByStep;

    TestStepData(
            List<TestStepFinished> beforeTestCaseSteps,
            List<TestStepFinished> afterTestCaseSteps,
            Map<TestStepFinished, List<TestStepFinished>> beforeStepStepsByStep,
            Map<TestStepFinished, List<TestStepFinished>> afterStepStepsByStep
    ) {
        this.beforeTestCaseSteps = beforeTestCaseSteps;
        this.afterTestCaseSteps = afterTestCaseSteps;
        this.beforeStepStepsByStep = beforeStepStepsByStep;
        this.afterStepStepsByStep = afterStepStepsByStep;
    }
}
