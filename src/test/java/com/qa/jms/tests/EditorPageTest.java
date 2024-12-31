package com.qa.jms.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.jms.base.BaseTest;
import com.qa.jms.pages.EditorPage;
import com.qa.jms.pages.QueryPage;

public class EditorPageTest extends BaseTest {
	
	@Test(priority = 0)
    public void verifyQueryCreation() {
        // Step 1: Navigate to Editor Page by clicking the editor icon
        editorPage = homepage.clickEditorIcon();

        // Step 2: Create a new query
        QueryPage queryPage = editorPage.createQuery();

        // Step 3: Validate the query creation (ensure it redirects to the QueryPage)
        Assert.assertNotNull(queryPage, "Failed to navigate to the Query Page after query creation");

        
    }

    @Test(priority = 1)
    public void verifyCreatedAtFunctionality() {
        // Step 1: Navigate to Editor Page by clicking the editor icon
        editorPage = homepage.clickEditorIcon();

        // Step 2: Call the `createdAt` method
        editorPage.createdAt();
        
        //Validate the emp id 
        
        String actualId=page.locator(EditorPage.empId).textContent();
        String expectedId = "8003";
        
        Assert.assertEquals(actualId, expectedId);
        
    }

}
