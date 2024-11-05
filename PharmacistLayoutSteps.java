package com.arine.automation.glue;

import com.arine.automation.exception.AutomationException;
import com.arine.automation.pages.PageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import static com.arine.automation.glue.CommonSteps.takeScreenshot;

public class PharmacistLayoutSteps {
    @Given("^User clicks on user's initials dropdown$")
    public void userClicksOnUserSIntialsDropdown() throws AutomationException {
        PageFactory.pharmacistLayoutPage().clickOnDropDown();
    }

    @And("^User perform mouse hovering action on \"([^\"]*)\"$")
    public void userPerformMouseHoveringActionOn(String option) throws AutomationException {
        PageFactory.pharmacistLayoutPage().hoverOnOption(option);
    }

    @When("^User clicks on \"([^\"]*)\" option$")
    public void userClicksOnOption(String option) throws AutomationException {
        PageFactory.pharmacistLayoutPage().clickOnOption(option);
    }

    @Then("^The layout should not be movable when Locked$")
    public void theLayoutShouldNotBeMovableWhenLocked() throws AutomationException {
        boolean isMovable = PageFactory.pharmacistLayoutPage().isLayoutMovable();
        takeScreenshot();
        Assert.assertFalse(isMovable, "The layout is movable when Locked.");
    }

    @When("^User checks for initial layout position$")
    public void userChecksForInitialLayoutPosition() throws AutomationException {
        PageFactory.pharmacistLayoutPage().captureInitialLayoutPosition();
    }

    @Then("^User moves the layout$")
    public void userMovesTheLayout() throws AutomationException {
        boolean isMoved = PageFactory.pharmacistLayoutPage().moveLayout();
        Assert.assertTrue(isMoved, "The layout did not move.");
        takeScreenshot();
    }

    @Then("^Verify Layout is reset to initial layout position$")
    public void verifyLayoutIsResetToInitialLayoutPosition() throws AutomationException {
        boolean isReset = PageFactory.pharmacistLayoutPage().isLayoutReset();
        takeScreenshot();
        Assert.assertTrue(isReset, "The layout position did not reset to the initial layout position.");
    }

    @Then("^Verify layout is in saved position$")
    public void verifyLayoutIsInSavedPosition() throws AutomationException {
        boolean isInSavedPosition = PageFactory.pharmacistLayoutPage().isLayoutInSavedPosition();
        takeScreenshot();
        Assert.assertTrue(isInSavedPosition, "The layout did not load in the saved position on re-login.");
    }


}
