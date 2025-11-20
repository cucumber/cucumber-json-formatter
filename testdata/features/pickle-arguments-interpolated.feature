Feature: pickle-arguments-interpolated

  Scenario Outline: docstring
    Given this step accepts an docstring
      """application/json
      { "key": "<value>" }
      """

    Examples:
      | key   |
      | value |

  Scenario Outline: datatable
    Given this step accepts an datatable
      | key     |
      | <value> |

    Examples:
      | key   |
      | value |