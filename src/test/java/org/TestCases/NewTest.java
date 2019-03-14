package org.TestCases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.read.biff.BiffException;

public class NewTest {

	public WebDriver driver;
	ArrayList<String> str = new ArrayList<String>();

	@BeforeClass
	public void setUp() throws IOException 
	{
		Runtime.getRuntime().exec("taskkill /f /im chrome.exe");

		String browser = System.getProperty("user.dir")+"/resources/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", browser);

		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		driver.get("http://www.way2automation.com/angularjs-protractor/webtables");

	}


	@Test(priority=1)
	public void validateUserListTable()
	{
		boolean flag = driver.findElement(By.xpath("//button[@class='btn btn-link pull-right']")).isDisplayed();
		Assert.assertTrue(flag);
	}

	@DataProvider
	public Iterator<Object[]> getTestData() throws BiffException, IOException
	{
		ArrayList<Object[]> testData = getExcelData.getExcelData();
		return testData.iterator();
	}


	@Test(dataProvider = "getTestData")
	public void addUsers(String fname, String lname, String uname, String pass, String comp, String role, String email, String phone)
	{

		if(!str.contains(uname))
		{		

			str.add(uname);

			driver.findElement(By.xpath("//button[@class='btn btn-link pull-right']")).click();


			driver.findElement(By.xpath("//input[@name='FirstName']")).clear();
			driver.findElement(By.xpath("//input[@name='FirstName']")).sendKeys(fname);

			driver.findElement(By.xpath("//input[@name='LastName']")).clear();
			driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys(lname);

			driver.findElement(By.xpath("//input[@name='UserName']")).clear();
			driver.findElement(By.xpath("//input[@name='UserName']")).sendKeys(uname);

			driver.findElement(By.xpath("//input[@name='Password']")).clear();
			driver.findElement(By.xpath("//input[@name='Password']")).sendKeys(pass);

			if(comp.equals("Company AAA"))
			{

				driver.findElement(By.xpath("//input[@type='radio'  and @value='15']")).click();
			}

			else
			{

				driver.findElement(By.xpath("//input[@type='radio'  and @value='16']")).click();
			}  


			WebElement roles = driver.findElement(By.xpath("//select[@name='RoleId']"));
			Select s = new Select(roles);

			if(role.contains("Sales"))
				s.selectByIndex(1);
			else if(role.contains("Customer"))
				s.selectByIndex(2);
			else
				s.selectByIndex(3);

			driver.findElement(By.xpath("//input[@name='Email']")).clear();
			driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);

			driver.findElement(By.xpath("//input[@name='Mobilephone']")).clear();
			driver.findElement(By.xpath("//input[@name='Mobilephone']")).sendKeys(phone);

			driver.findElement(By.xpath("//button[text()='Save']")).click();

			System.out.println("User Added Succesfully... ");
		}
		else
		{
			System.out.println("User Already Exist... ");
		}
	}



	@AfterClass
	public void tearDown() throws IOException
	{
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir")+"/Screenshot/table.png");

		FileUtils.copyFile(src, dest);

		driver.quit();
	}
}
