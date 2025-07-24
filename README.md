⚠️ This is an internal package; you don't need to install it in order to use the JSON Formatter.

[![Maven Central](https://img.shields.io/maven-central/v/io.cucumber/cucumber-json-formatter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:io.cucumber%20AND%20a:cucumber-json-formatter)

# Cucumber JSON Formatter
Writes Cucumber message into the legacy Cucumber JSON format

The Cucumber JSON report is a de facto standard without specification. The standard
also differs per Cucumber implementation.

For each language we validate this implementation against the implementation
specific variants of the [cucumber-json-schema](https://github.com/cucumber/cucumber-json-schema)
as well as the original implementation. So  there should be a good chance your
tooling will understand it.

## Features

Given a passing feature file:

```gherkin
Feature: minimal
  
  Scenario: cukes
    Given I have 42 cukes in my belly
```

Cucumber reports the result in json as:

```json
[
  {
    "line": 1,
    "uri": "samples/minimal/minimal.feature",
    "id": "minimal",
    "keyword": "Feature",
    "name": "minimal",
    "description": "",
    "elements": [
      {
        "start_timestamp": "1970-01-01T00:00:00.001Z",
        "line": 9,
        "id": "minimal;cukes",
        "type": "scenario",
        "keyword": "Scenario",
        "name": "cukes",
        "description": "",
        "steps": [
          {
            "keyword": "Given ",
            "line": 10,
            "match": {
              "location": "samples/minimal/minimal.feature.ts:3",
              "arguments": [
                {
                  "val": "42",
                  "offset": 7
                }
              ]
            },
            "name": "I have 42 cukes in my belly",
            "result": {
              "duration": 1000000,
              "status": "passed"
            }
          }
        ]
      }
    ],
    "tags": [ ]
  }
]
```

## Maintenance mode

The Cucumber JSON format and formatter are in maintenance mode. For the new
unified message format, see [cucumber-messages](https://github.com/cucumber/messages).

## Contributing

Each language implementation validates itself against the examples in the
`testdata` folder. See the [testdata/README.md](testdata/README.md) for more
information.
