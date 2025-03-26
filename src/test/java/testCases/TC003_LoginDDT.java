package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass{

	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class ,groups="Datadriven")
	public void verify_loginDDT(String email,String pwd, String exp)
	{
		
		logger.info("*******Starting TC003_LoginDDT *****");
		
		try
		{;
		//Home page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExists();
		
		
		if(exp.equalsIgnoreCase("valid"))
		{
			
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		 }
		
	     if(exp.equalsIgnoreCase("Invalid"))
	     {
	    	if(targetPage==true)
	    	{
	    		macc.clickLogout();
	    		Assert.assertTrue(false);
	    	}
	    	else
	    	{
	    		Assert.assertTrue(true);
	    	}
	    	 
	     }
		}catch(Exception e)
		{
			Assert.fail();
		}
	
	     logger.info("*******Finshed TC003_LoginDDT *****");
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
