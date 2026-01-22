package io.cucumber.jsonformatter;

import io.cucumber.messages.LocationComparator;
import io.cucumber.messages.types.Location;
import io.cucumber.messages.types.Pickle;
import io.cucumber.messages.types.TestCaseStarted;
import io.cucumber.query.Lineage;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

final class JvmElementData {
    static final Comparator<JvmElementData> comparator = Comparator
            .comparing((JvmElementData data) -> data.pickle.getUri())
            // We can't use the location from the pickle, it wasn't present in Cucumber-JVM 7.26
            .thenComparing(data -> data.location, new LocationComparator());

    final TestCaseStarted testCaseStarted;
    final Lineage lineage;
    final Pickle pickle;
    final Location location;
    final TestStepData testStepData;

    JvmElementData(
            TestCaseStarted testCaseStarted, Lineage lineage, Pickle pickle, Location location,
            TestStepData testStepData
    ) {
        this.testCaseStarted = requireNonNull(testCaseStarted);
        this.lineage = requireNonNull(lineage);
        this.pickle = requireNonNull(pickle);
        this.location = requireNonNull(location);
        this.testStepData = requireNonNull(testStepData);
    }
}
