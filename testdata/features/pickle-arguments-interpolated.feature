Feature: pickle-arguments-interpolated

  Scenario Outline: docstring
    Given this step accepts a docstring
      """application/json
      { "hello": "<key>" }
      """

    Examples:
      | key   |
      | word |

  Scenario Outline: datatable
    Given this step accepts a datatable
      | hello |
      | <key> |

    Examples:
      | key   |
      | world |