Feature: Check the process of booking room from API
  As user
  I need to check the process of booking of room
  thru the call of http methods was successfully

  Scenario: create an Auth token
    Given that an auth token is created
      | username | password    |
      | admin    | password123 |

  Scenario: See a specific reservation reservation
    Given that hotel requires to search a specific reservation
      | bookingnumber | search    |
      | 1             | firstname |
    Then the receptionist verifies if the user Jim appears in that search

  @test
  Scenario: create a new booking
    Given that an user made a new reservation
      | firstname | lastname | totalprice | deposit | checkin    | checkout   | needs  | path    | itemtosearch |
      | chelita   | perez    | 120        | true    | 2025-01-01 | 2025-01-01 | supper | booking | bookingid    |


