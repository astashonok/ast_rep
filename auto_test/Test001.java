package tests;




import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;


public class Test001 {
	private String eventName = "Test_Event";
	private boolean firstTime = true;
	private String email = "ser_george005@mail.ru";
	private String pass = "test001";
	private String organizationName = "Test";
	private String phone = "33333333333";
	private Venue venue = new Venue("Venue_Test", "Street", "Street2", "New York", "New York","200100", "US",false, 1);
	private static ArrayList<String> dates = new ArrayList<String>();
static 
{
	dates.add("03/04/2014#06:12 PM-03/18/2014#05:47 PM");
}
	
	private static WebDriver driver;
	private static String mainWindow;
	   @Before
	   public void setUp() throws Exception {
		   driver = new FirefoxDriver();
	   }
	 
	  @Test
	   public void main() throws Exception {
		
	    String url = "";//set url here
	    driver.get(url);
	    waitDocumentReady(60000);
	   if (firstTime)
	   {
		   register();
	   }
	   else login();
	   
	  
	   createEvent(firstTime);
	   fillEventInfo(eventName, venue, "(UTC-10:00) Hawaii",dates, "HEADER", "Description");
	   driver.findElement(By.id("save-event-button")).click();
	   waitDocumentReady(60000);  
	   Assert.assertTrue("Event is not created", driver.findElement(By.className("page-title")).getText().equalsIgnoreCase("UPDATE "+eventName));
	  
	    
	   }


	
	@After
	   public void tearDown() throws Exception {
	     driver.quit();
	   }
	   
	   
	private void createEvent(boolean firstTme) {
		  if (firstTme)
		  {
			  driver.findElement(By.linkText("CREATE EVENT")).click();
			  waitDocumentReady(60000);
		  }
		  else
		  {
			  driver.findElement(By.xpath("//h5[text()='EVENTS']")).click();
			    waitDocumentReady(60000);
			    driver.findElement(By.linkText("CREATE EVENT")).click();
			    waitDocumentReady(60000);
		  }
	}   
	   
	
	private void login() {
		   driver.findElement(By.linkText("Log In")).click();
		   waitDocumentReady(60000);
		   driver.findElement(By.id("Username")).sendKeys(email);
		   driver.findElement(By.id("Password")).sendKeys(pass);
		   driver.findElement(By.className("btn-primary")).click();
		   waitDocumentReady(60000);
	}

	private void register() {
		  driver.findElement(By.linkText("SIGN UP FOR FREE")).click();
		    waitDocumentReady(60000);
		    driver.findElement(By.id("Email")).sendKeys(email);
		    driver.findElement(By.id("Password")).sendKeys(pass);
		    driver.findElement(By.id("OrganizationName")).sendKeys(organizationName);
		    driver.findElement(By.id("Phone")).sendKeys(phone);
		    driver.findElement(By.className("btn-success")).click();
		    waitDocumentReady(60000);
		
	}
	private void fillEventInfo(String name, Venue venue, String timeZone,
			ArrayList<String> dates, String header, String desc) {
		
		driver.findElement(By.id("Title")).sendKeys(name);
		driver.findElement(By.linkText("Create New Venue")).click();
		selectChildWindow();
		driver.findElement(By.id("Name")).sendKeys(venue.getName());
		driver.findElement(By.id("Street")).sendKeys(venue.getStreet());
		driver.findElement(By.id("Street2")).sendKeys(venue.getStreet2());
		driver.findElement(By.id("City")).sendKeys(venue.getCity());
		select(By.id("StateId_chzn"),venue.getState());
		
		driver.findElement(By.id("Zip")).sendKeys(venue.getZip());
		select(By.id("CountryId_chzn"),venue.getCountry());
		
		if ( !driver.findElement(By.id("hasReservedSeating")).isSelected() && venue.isHasReservedSeating() )
			{
				     driver.findElement(By.id("hasReservedSeating")).click();
			}
		driver.findElement(By.id("MaxCapacity")).clear();
		driver.findElement(By.id("MaxCapacity")).sendKeys(Integer.toString(venue.getMaxCapacity()));
		driver.findElement(By.id("edit-venue-btn")).click();
		selectMainWindow();
		waitDocumentReady(60000);
		
		select(By.id("timezone_select_chzn"),timeZone);
		
		setDate(dates);
		
		setHeader(header);
		setDesc(desc);
		
	}

	


	public static void waitDocumentReady(long timeout) {
			String state;

			Wait<JavascriptExecutor> wait = new FluentWait<JavascriptExecutor>((JavascriptExecutor)driver)
			  .withTimeout(timeout, TimeUnit.MILLISECONDS)
			  .ignoring(WebDriverException.class)
			  .pollingEvery(100, TimeUnit.MILLISECONDS);

			try{
				wait.until(new Function<JavascriptExecutor, Boolean>() {
					@Override
					public Boolean apply(JavascriptExecutor js) {
						return ((String)js.executeScript("return document.readyState;")).equals("complete");
					}
				});
			}
			catch(TimeoutException e){
				state = (String)((JavascriptExecutor)driver).executeScript("return document.readyState;");
				if(!state.equals("interactive")){
					throw new TimeoutException("Document state is " + state + ". Original message is: " + e.getMessage());
				}
			}
		}
	
		public static String[] getWindowHandles() {
			Set<String> handles = driver.getWindowHandles();
			return handles.toArray(new String[handles.size()]);
		}

		/**
		 * Selects main window.
		 * @throws Exception
		 */
		public static void selectMainWindow(){
			mainWindow = getWindowHandles()[0];
			driver.switchTo().window(mainWindow);
		}

		/**
		 * Selects second window.
		 */
		public static void selectChildWindow(){
			Assert.assertTrue("There is only one window. No any child window.", driver.getWindowHandles().size()>1);
			String secondHwnd = getWindowHandles()[1];
			driver.switchTo().window(secondHwnd);
		}

	   
		public void select(By locator, String value)
		{
			driver.findElement(locator).findElement(By.cssSelector("a")).click();
			driver.findElement(By.xpath("//li[text()='"+value+"']")).click();
			
			
		}
		
		   private void setDate(ArrayList<String> dates) {
			   String date = dates.get(0);
				String date1 = date.split("-")[0];
				String date2 = date.split("-")[1];
				
				driver.findElement(By.id("EventInstances_0__StartDateTime")).sendKeys(date1.split("#")[0]);
				driver.findElement(By.id("EventInstances_0__StartTime")).sendKeys(date1.split("#")[1]);
				
				driver.findElement(By.id("EventInstances_0__EndDateTime")).sendKeys(date2.split("#")[0]);
				driver.findElement(By.id("EventInstances_0__EndTime")).sendKeys(date2.split("#")[1]);	
				
			
		}
		   
		   private void setHeader(String text)
		   {
			   String script = "$('#cke_1_contents').find('iframe').contents().find('body').text('"+text+"')";
			   ((JavascriptExecutor)driver).executeScript(script);
			   
		   }
		   private void setDesc(String text)
		   {
			   String script = "$('#cke_2_contents').find('iframe').contents().find('body').text('"+text+"')";
			   ((JavascriptExecutor)driver).executeScript(script);
			   
		   }
		   
}
