package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) 
	{
		super(driver);
	}

	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement linkMyaccount;
	
	
	@FindBy(xpath="//a[contains(text(),'Register')]")
	WebElement registerLink;
	
	@FindBy(linkText="Login") //login link added in step 5  
	WebElement linkLogin;
	
	
	public void clickMyAccount()
	{
		linkMyaccount.click();
	}
	
	public void clickRegister()
	{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    int attempts = 0;
    
    while (attempts < 3) { // Try clicking 3 times in case of StaleElementException
        try {
            WebElement registerLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Register')]")));
            registerLink.click();
            return; // Exit if click is successful
        } catch (StaleElementReferenceException e) {
            System.out.println("Attempt " + (attempts + 1) + " - StaleElementReferenceException encountered. Retrying...");
        }
        attempts++;
    }
    throw new RuntimeException("Failed to click Register after multiple attempts");
}

	
	
	
	public void clickLogin()
	{
		linkLogin.click();
	}
		
}
