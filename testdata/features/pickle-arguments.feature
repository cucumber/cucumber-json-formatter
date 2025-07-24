Feature: pickle-arguments

  Scenario: docstring
    Given this step accepts a docstring
      """application/json
      { "hello": "world" }
      """

  Scenario: datatable
    Given this step accepts a datatable
      | hello | cya later |
      | world | world     | 
