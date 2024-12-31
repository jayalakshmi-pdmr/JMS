package com.qa.jms.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.qa.jms.base.BaseTest;
import com.qa.jms.pages.AddJournalPage;

public class AddJournalTest extends BaseTest {

    private AddJournalPage addJournalPage;


    // Set the Excel file path and sheet name
    String filePath = "C:\\Users\\sjayalakshmi\\eclipse-workspace\\Sample\\JMS\\src\\main\\resources\\files\\JournalData.xlsx";  
    String sheetName = "JournalData";
    
    @Test(priority=0)
    public void addJournalTest() {
   
    addJournalPage = new AddJournalPage(page, filePath, sheetName);
    	

        addJournalPage.navigateToAddJournalPage();
    }
        
    @Test(priority=1)
    public void selectPublisherTest() {
    	// Set the file path and sheet name for ExcelReader (this may be a utility or a method in AddPublisherPage)
        addJournalPage.setExcelReader(filePath, sheetName);
        String title = homepage.CheckTitle();
        System.out.println("Page Title: " + title);
        addJournalPage.selectPublisher();
    }
    
    @Test(priority=2)
    public void journalDetailTest() {
        addJournalPage.enterJournalDetails();
    }
    
    @Test(priority=3)
    public void selectJournalWorkTest() {
        addJournalPage.selectJournalWork();
    }
    
    @Test(priority=4)
    public void selectPubTypeTest()
    {
        addJournalPage.selectPubType();
    }
    
    @Test(priority=5)
    public void dateImportTest() {
    	addJournalPage.dateImport();
    }
    
    @Test(priority=6)
    public void uploadJournalStyleTest() {
    	addJournalPage.uploadJournalStyle("journalStyle.sty");
    }
    
    @Test(priority=7)
    public void uploadJournalgudelinesTest() {
    	addJournalPage.uploadJournalGuideline("journalGuideline.pdf");
    }
    @Test(priority=8)
    public void clickButtonsTest() {
    	
        String actual =addJournalPage.clickButtons();
        
        //validate where the journal is added or not with text
        Assert.assertEquals(actual, "Journal Added Successfully", "Validation failed: Text mismatch.");

        }
}
