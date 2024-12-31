package com.qa.jms.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.qa.jms.base.BaseTest;
import com.qa.jms.pages.AddArticle;
import com.qa.jms.pages.AddJournalPage;

public class AddArticleTest extends BaseTest {

    private AddArticle addArticlePage;


    // Set the Excel file path and sheet name
    String filePath = "C:\\Users\\sjayalakshmi\\eclipse-workspace\\Sample\\JMS\\src\\main\\resources\\files\\ArticleDetails.xlsx";  
    String sheetName = "ArticleDetails"; 
    
    @Test(priority=0)
    public void addJournalTest() {
   
    	addArticlePage = new AddArticle(page, filePath, sheetName);
    	

    	addArticlePage.navigateToAddArticle();
    }
        
    @Test(priority=1)
    public void selectPublisherTest() {
    	// Set the file path and sheet name for ExcelReader (this may be a utility or a method in AddPublisherPage)
    	addArticlePage.setExcelReader(filePath, sheetName);
        String title = homepage.CheckTitle();
        System.out.println("Page Title: " + title);
        addArticlePage.selectPublisher();
    }
    
    @Test(priority=2)
    public void enterArticleDetailsTest() {
    	addArticlePage.enterMetaData();
    }
    
    @Test(priority=3)
    public void turnAroundDeatilsTest() {
    	addArticlePage.turnAroundDeatils();
    }
    
    @Test(priority=4)
    public void checklistSelectionTest() {
    	addArticlePage.checklistSelection();
    }
    
    @Test(priority=5)
    public void articleFileUplloadTest() {
    	addArticlePage.fileUpload();
    	}
    @Test(priority=6)
    public void notesTest() {
    	addArticlePage.notes();
    }
    
    @Test(priority=7)
    public void checkListTest() {
    	addArticlePage.checkList();
    	
    }
    
    @Test(priority=7)
    public void mailTest() {
    	addArticlePage.mail();
    }
    
    @Test(priority=9)
    public void validateArticleTest() {
    	String actual=addArticlePage.validateArticleAdded();
    	//validation
    	
    	Assert.assertEquals(actual,"J12356847","Validation failed: Text mismatch.");
    }
    
}
