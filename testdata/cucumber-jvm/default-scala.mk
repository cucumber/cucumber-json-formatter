include $(dir $(lastword $(MAKEFILE_LIST)))/../default.mk

testdata/%.ndjson: ../../features/%.feature src/main/scala/io/cucumber/jsonformatter/StepDefinitions.scala pom.xml
	mkdir -p $(@D)
	mvn compile
	mvn exec:java -Dexec.mainClass="io.cucumber.core.cli.Main" -Dexec.args="$< --plugin json:$@.jvm.json --plugin message:$@ --plugin pretty --no-summary" || true
	# Workaround for https://github.com/cucumber/cucumber-jvm-scala/issues/401
	sed -E -i".tmp" 's/(StepDefinitions\.scala:[0-9]+)/io.cucumber.jsonschema.StepDefinitions.\<init\>(\1)/g' $@.jvm.json 
	rm $@.jvm.json.tmp
