Feature: Check the process of booking room from API suc
  As user
  I need to check the process of booking of room
  thru the call of http methods was unsuccessfully

  Scenario: create an Auth token with invalid path - error 403
    Given that an auth token attempts to be created with invalid path
      | username | password    | invalidpath |
      | admin    | password123 | auth1   |

  Scenario: Try to see a specific reservation with invalid verification - 200 OK but fail
    Given that hotel requires to search a specific reservation
      | bookingnumber | search    |
      | 1             | firstname |
    Then the receptionist verifies that the user Sally doesn't appear in that search