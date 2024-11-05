@PharmacistPortal @VerifyPharmacistLayout
Feature: Verify Discussed Practitioner Report Feature

  @Setup @Regression @Smoke
  Scenario Outline: SETUP: Launch Browser and go to application
    Given User launched "chrome"
    Then User go to application "$pharmacist_portal_url"
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    Examples:
      | username                                            | password                                            | message |
      | $patient-discussedPractitionerReport.user1.username | $patient-discussedPractitionerReport.user1.password | success |

  @Regression @Smoke
  Scenario: Verify_Layout_is_locked_and_not_editable
    Given User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    When User clicks on "Lock Layout" option
    Then The layout should not be movable when Locked

  @Regression @Smoke
  Scenario: Verify_Layout_elements_resets_when_reset_option_is_clicked
    Given User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    When User clicks on "Unlock Layout" option
    And User checks for initial layout position
    Then User moves the layout
    Then User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    And User clicks on "Reset Layout" option
    Then Verify Layout is reset to initial layout position

  @Regression @Smoke
  Scenario Outline: Verify_users_layout_changes_are_saved
    Given User select organization: "<Organization>"
    And Click on Tasks Tab
    And User clicks on user's initials dropdown
    And User perform mouse hovering action on "Layout"
    And User clicks on "Unlock Layout" option
    And User checks for initial layout position
    Then User moves the layout
    Then User logout from the application
    When User login with "<username>" and "<password>"
    And Verify Login message: "<message>"
    And User select organization: "<Organization>"
    And Click on Tasks Tab
    And Wait to page load
    Then Verify layout is in saved position
    Examples:
      | username                                            | password                                            | message | Organization |
      | $patient-discussedPractitionerReport.user1.username | $patient-discussedPractitionerReport.user1.password | success | ENG TEST     |


  @Setup @Regression @Smoke
  Scenario: SETUP: Logout and Close Browser
    When User logout from the application
    Then User close browser