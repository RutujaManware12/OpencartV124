package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
 
	  @Test(groups={"Regression","Master"})
	  public void verify_account_registration()
	  {
		 
		  logger.info("******Starting TC001_AccountRegistrationTest ******");
		  
		  
		  try
		  {
		  HomePage hp=new HomePage(driver);
		  hp.clickMyAccount();
		  logger.info("Clicked on MyAccount Link..");
		  Thread.sleep(2000);		  
		  hp.clickRegister();
		  logger.info("Clicked on Register Link..");
		  
		  AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		  
		  logger.info("Providing customer details....");
		  regpage.setFirstName(randomeString().toUpperCase());
		  regpage.setLastName(randomeString().toUpperCase());
		  regpage.setEmail(randomeString()+"@gmail.com"); //randomly generated the email
		  regpage.setTelephone(randomeNumber());
		  
		  String password=randomeAlphaNumberic();
		  regpage.setPassword(password);
		  regpage.setConfirmPassword(password);
		  
		  regpage.setPrivacyPolicy();
		  regpage.clickContinue();
		  
		  logger.info("Validating expected message...");
		  String confirms=regpage.getConfirmationMsg();
		  if(confirms.equals("Your Account Has Been Created!"))
		  {
			  Assert.assertTrue(true);
		  }
		  else
		  {
			  logger.error("Test failed...");
			  logger.debug("Debug logs...");
			  Assert.assertTrue(false);
		  }
		   
	     }
		 catch(Exception e)
		 {
			 logger.error("Test failed due to exception: " + e.getMessage());
			 e.printStackTrace();
			  Assert.fail();
		 }
	
		  logger.info("********Finished TC001_AccountRegistrationTest ****");
		  System.out.println(Thread.currentThread().getContextClassLoader().getResource("log4j2.xml"));

}
}
