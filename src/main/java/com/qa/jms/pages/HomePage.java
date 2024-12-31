package com.qa.jms.pages;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;

public class HomePage {

    private Page page;
    private String baseicon = "//img[@src='/jms/src/assets/GeneralIcons/shortcuts.svg']";
    public String addpubicon = "id=add_publisher";
    private String addjournalicon = "id=add_journal";
    private String label = "//label[text()='Pre-Edit Normalization Done!']";
    private String editorlink = "http://192.168.1.39:3002/jms/v1/home/editor";

    // String locators
    private String editor = "//div[@class='_action-image-container_13gt4_247 false']//img[@title='editor']";
    private String queryIcon = "//tbody/tr[3]/td[6]/div[4]/img[1]";

    // Constructor to initialize the page
    public HomePage(Page page) {
        this.page = page;
    }

   
    //TitleValidation
    public String CheckTitle() {
        return page.title();
    }
    
   
   // Click on the editor icon and validate the label
   public EditorPage clickEditorIcon() {
       // Click on the editor icon
       page.locator(editor).click();
       System.out.println("Editor icon clicked");

       // Validate that the editor page has opened by checking the label
       String editorLabel = page.locator(label).textContent();
       System.out.println("Editor label: " + editorLabel);

       // Ensure the label is not empty or null
       if (editorLabel == null || editorLabel.isEmpty()) {
           throw new RuntimeException("Editor label is empty or not found!");
       }
       
       // Ensure the label contains expected text (optional, modify as needed)
       if (!editorLabel.equals("Pre-Edit Normalization Done!")) {
           throw new RuntimeException("Unexpected label text: " + editorLabel);
       }

       // Return the EditorPage object to continue interacting with it
       return new EditorPage(page);
   }
   
   //Method to get the label text
   public String getLabelText() {
       return page.locator(label).textContent();
   }

}
