# Acceptance test data

The cucumber json formatter uses three different testing strategies.

## Validation against the CCK

The examples from the [cucumber compatibility kit](https://github.com/cucumber/compatibility-kit)
are used for acceptance testing. These examples consist of `.ndjson` files created by
the [`fake-cucumber` reference implementation](https://github.com/cucumber/fake-cucumber).

The `.njdon` files are copied in by running `npm install`.  We ensure the
`.ndjson` files stay up to date by running `npm install` in CI  and verifying
nothing changed.

Should there be changes, these tests can be used to update the expected data for
each implementation:
 * Java: `MessagesToJsonWriterAcceptanceTest.updateExpectedFiles`

## Validation against the Json Schema

The schemas from the [cucumber-json-schema](https://github.com/cucumber/cucumber-json-schema)
are copied in by running `npm install`.  We ensure the `.ndjson` files stay up
to date by running `npm install` in CI  and verifying  nothing changed.

## Validation against the previous implementation

To ensure that this json formatter implementation is identical to the existing
one we test against each Cucumber implementation against an older version of
itself that does not yet use the message based json formatter.

This test data is not expected to change, but should it change it can be updated
by running `make clean && make` in the `testdata` folder.

Note: Renovate [must be configured to ignore these folders](../.github/renovate.json).