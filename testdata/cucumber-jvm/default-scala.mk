include $(dir $(lastword $(MAKEFILE_LIST)))/../default.mk

testdata/%.ndjson: ../../features/%.feature src/main/scala/io/cucumber/jsonformatter/StepDefinitions.scala pom.xml
	mkdir -p $(@D)
	mvn compile
	mvn exec:java -Dexec.mainClass="io.cucumber.core.cli.Main" -Dexec.args="$< --plugin json:$@.jvm.json --plugin message:$@ --plugin pretty --no-summary" || true
