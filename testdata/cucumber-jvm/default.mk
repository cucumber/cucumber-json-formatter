include $(dir $(lastword $(MAKEFILE_LIST)))/../default.mk

testdata/%.ndjson: ../../features/%.feature src/main/java/io/cucumber/jsonformatter/StepDefinitions.java
	mkdir -p $(@D)
	mvn compile
	mvn exec:java -Dexec.mainClass="io.cucumber.core.cli.Main" -Dexec.args="$< --plugin json:$@.jvm.json --plugin message:$@ --plugin pretty --no-summary" || true
