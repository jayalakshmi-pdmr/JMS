package com.qa.jms.tests;

import java.io.IOException;

import org.testng.annotations.Test;
import com.qa.jms.pages.AddPublisherPage;
import com.qa.jms.base.BaseTest;

public class AddPublisherTest extends BaseTest {

    private AddPublisherPage addPublisherPage;

   

    // Set the Excel file path and sheet name
    String filePath = "C:\\Users\\sjayalakshmi\\eclipse-workspace\\Sample\\JMS\\src\\main\\resources\\files\\PublisherData.xlsx";  
    String sheetName = "PublisherData"; 
    
  


    @Test
    public void addPublisherTest()  {
    	
	    	
        // Initialize AddPublisherPage with the page object
        addPublisherPage = new AddPublisherPage(page, filePath, sheetName);
        
    	 addPublisherPage.navigateToAddPublisher();

        // Set the file path and sheet name for ExcelReader (this may be a utility or a method in AddPublisherPage)
        addPublisherPage.setExcelReader(filePath, sheetName);

        // Open the homepage and check the page title
        String title = homepage.CheckTitle();
        System.out.println("Page Title: " + title);

        // Enter publisher details from the Excel file
        addPublisherPage.enterPublisherDetails();
        
        //replicate values
        addPublisherPage.replicateValuesFastTrack();

        // Upload publisher files (logo, style, guidelines)
        addPublisherPage.uploadPublisherLogo("publisherLogo.jpg");
        addPublisherPage.uploadPublisherStyle("publisherStyle.sty");
        addPublisherPage.uploadPublisherGuideline("publisherGuideline.pdf");

        // Click the 'Add Publisher' button to add the publisher
        addPublisherPage.clickAddPublisherButton();

        
    }
}
