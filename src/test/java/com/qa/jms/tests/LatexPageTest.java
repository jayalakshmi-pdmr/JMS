package com.qa.jms.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.jms.base.BaseTest;

public class HomePageTest extends BaseTest {

    @Test(priority = 0)
    public void verifyHomeTitle() {
        String titlename = homepage.CheckTitle();
        Assert.assertEquals(titlename, "JMS", "Title is incorrect");
    }
    
    @Test(priority = 1)
    public void testclickEditorIcon() {
    	 // Step 1: Click on the editor icon and navigate to the EditorPage
        editorPage = homepage.clickEditorIcon();  // This clicks the editor icon and returns EditorPage

        // Step 2: Get the label text from the HomePage
        String editorLabel = homepage.getLabelText();  // Get the label text (the same label used in HomePage)

        // Step 3: Perform assertions to validate the label text
        Assert.assertNotNull(editorLabel, "Editor label is null");  
        Assert.assertFalse(editorLabel.isEmpty(), "Editor label is empty"); 
        Assert.assertEquals(editorLabel, "Pre-Edit Normalization Done!", "Unexpected label text");  // Validate label text
    }
}
