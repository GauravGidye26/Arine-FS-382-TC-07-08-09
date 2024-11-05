package com.arine.automation.pages;

import com.arine.automation.drivers.DriverFactory;
import com.arine.automation.exception.AutomationException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.arine.automation.pages.BasePage.driverUtil;

public class PharmacistLayoutPage {
    private static final String INITIAL_DROPDOWN = "//*[text()='(DD)']/..";
    private static final String LAYOUT_LOCK_UNLOCK_STATE = "//div[contains(@class,'react-grid-item react-draggable react-resizable')]";
    private static final String LAYOUT_OPTION_TEXT = "//div[contains(@class,'node_modules-react-multilevel-dropdown-__menu-left___2rT6Q')]//li[contains(text(),'%s')]";
    private static final String LAYOUT_TOP_VALUE = "(//div[contains(@class,'react-grid-item')])[1]";

    private int initialTopPosition;
    private int savedTopPosition;

    public void clickOnDropDown() throws AutomationException {
        driverUtil.getWebElement(INITIAL_DROPDOWN).click();
    }

    public void hoverOnOption(String option) throws AutomationException {
        WebElement optionElement = driverUtil.getWebElement("//li[text()='" + option + "']");
        new Actions(DriverFactory.drivers.get()).moveToElement(optionElement).perform();
    }

    public void clickOnOption(String option) throws AutomationException {
        WebElement optionButton = driverUtil.getWebElement(String.format(LAYOUT_OPTION_TEXT, option));
        WebElement layoutElement = driverUtil.getWebElement(LAYOUT_LOCK_UNLOCK_STATE);

        if ((option.equalsIgnoreCase("Unlock Layout") && layoutElement == null) ||
                (option.equalsIgnoreCase("Lock Layout") && layoutElement != null) ||
                option.equalsIgnoreCase("Reset Layout")) {
            optionButton.click();

        }
    }

    private int extractTopPosition(String style) {
        for (String property : style.split(";")) {
            if (property.trim().startsWith("top:")) {
                return Integer.parseInt(property.split(":")[1].trim().replace("px", ""));
            }
        }
        throw new IllegalArgumentException("Top position not found in style attribute.");
    }

    public boolean isLayoutMovable() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        int initialY = layout.getLocation().getY();

        actions.dragAndDropBy(layout, 0, 400).perform();
        return initialY != layout.getLocation().getY();
    }

    public void captureInitialLayoutPosition() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        initialTopPosition = extractTopPosition(layout.getAttribute("style"));
        savedTopPosition = initialTopPosition;
        System.out.println("Captured Initial Position: Top = " + initialTopPosition);
    }

    public boolean moveLayout() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        Actions actions = new Actions(DriverFactory.drivers.get());
        actions.dragAndDropBy(layout, 0, 400).perform();

        savedTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Moved Layout Position: Top = " + savedTopPosition);

        return true;
    }

    public boolean isLayoutInSavedPosition() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        int currentTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Current Top Position: " + currentTopPosition);
        return savedTopPosition == currentTopPosition;
    }

    public boolean isLayoutReset() throws AutomationException {
        WebElement layout = driverUtil.getWebElement(LAYOUT_TOP_VALUE);
        if (layout == null) throw new AutomationException("Layout element not found.");

        int currentTopPosition = extractTopPosition(layout.getAttribute("style"));
        System.out.println("Current Top Position for Reset Check: " + currentTopPosition);

        return initialTopPosition == currentTopPosition;
    }
}

