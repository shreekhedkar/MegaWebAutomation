package pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MegaDesktop extends LoadableComponent<MegaDesktop> {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "(//*[@data-os='linux'])[1]")
	WebElement linux;

	@FindBy(xpath = "(//*[@class='mega-input dropdown-input box-style inline megasync-dropdown'])[1]/i")
	WebElement dropdown;

	@FindBy(xpath = "//*[@class='mega-input-dropdown']//div[@class='option']")
	List<WebElement> options;

	public MegaDesktop(final WebDriver driver, final WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	public boolean clickOnLinuxPlatform() {
		linux.click();
		wait.until(ExpectedConditions.elementToBeClickable(dropdown));
		return true;
	}

	public boolean clickOnDistributionDropDown() {
		dropdown.click();
		return true;
	}

	public List<WebElement> getAllOptions() {
		return options;
	}

	@Override
	protected void load() {
		driver.get("https://mega.io/desktop");
		wait.until(ExpectedConditions.elementToBeClickable(linux));
	}

	@Override
	protected void isLoaded() throws Error {
		// Validations
		final String actual = driver.getTitle();
		final String expected = "Desktop App - MEGA";

		assertEquals(actual, expected);

	}

}
