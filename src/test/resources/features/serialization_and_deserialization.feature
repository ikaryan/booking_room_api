Feature: Serialization and Deserialization in API
  As user
  I need to serialize and deserialize

  Scenario: store record in json file - serialization
    When a java object is stored in json file
      | firstname | lastname | totalprice | deposit | checkin    | checkout   | needs                   |
      | pepito    | perez    | 120        | true    | 2025-01-01 | 2025-01-01 | super bowls & Breakfast |

  Scenario: store json file in record - deserialization
    Given user have the following data
      | firstname | lastname | totalprice | deposit | checkin    | checkout   | needs     |
      | jaimito   | olaya    | 200        | false   | 2026-01-01 | 2026-01-08 | Breakfast |
    When user deserialize the JSON string
    Then user should see the User object
