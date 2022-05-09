package tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.MegaDesktop;

public class MegaDesktopTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private MegaDesktop md;
	private HttpURLConnection huc;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\admin\\Downloads\\binaries\\chromedriver.exe");

		// Open browser
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	@Test
	public void launchMegaApplication() {
		md = new MegaDesktop(driver, wait);
		md.get();
	}

	@Test(dependsOnMethods = "launchMegaApplication")
	public void shouldClickOnLinux() {
		assertTrue(md.clickOnLinuxPlatform());
	}

	@Test(dependsOnMethods = "shouldClickOnLinux")
	public void shouldClickOnLinuxDistribution() {
		assertTrue(md.clickOnDistributionDropDown());
	}

	@Test(dependsOnMethods = "shouldClickOnLinuxDistribution")
	public void testLinks() throws IOException {
		List<WebElement> links = md.getAllOptions();

		for (WebElement link : links) {
			String url = link.getAttribute("data-link");
			assertTrue(isLinkValid(url));
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	private boolean isLinkValid(String url) throws IOException {
		huc = (HttpURLConnection) new URL(url).openConnection();
		huc.setRequestMethod("HEAD");
		huc.connect();
		if (huc.getResponseCode() >= 400) {
			return false;
		} else
			return true;
	}
}
