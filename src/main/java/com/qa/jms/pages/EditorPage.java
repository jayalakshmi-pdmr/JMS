package com.qa.jms.pages;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;

public class EditorPage {
	
	private Page page;
	
	//Locators
	private String menu = "//div[@class='_layer1_qz1od_637']//div[1]//label[1]//div[1]";
	private String ace = "//div[@class='ace_content']";
	private String query = "//p[text()='Add Query']";
	private String textFieldQuery = "//textarea[@class='_textarea_14djl_185']";
	private String queryOk = "//newbutton[text()='Add']";
	private String moreOptions = "//p[contains(text(),'More Options')]";
	private String editorQueryPanel ="//span[@class='_suggestion-panel-name_zv3j7_217'][normalize-space()='Query']";	
	private String arrow = "//img[@class='_goToLine_1hjrm_139']";
	public static String empId = "//span[@class='_raised_1hjrm_149']//span[contains(text(),'8003')]";
	
	//constructor
	public EditorPage(Page page) {
		this.page =  page;
	}
	
	
	
	//Page Actions
	public QueryPage createQuery() {
		
		page.click(menu);
		page.locator(ace);
		
		// Wait for a short moment to ensure focus
        page.waitForTimeout(500);

        // Move to the desired line (e.g., 10th line)
        for (int i = 0; i < 10; i++) {
            page.keyboard().press("ArrowDown");
        }
        Locator aceLocator = page.locator(ace);
        
        //Right click on the editor
        aceLocator.click(new ClickOptions().setButton(MouseButton.RIGHT));
        
       //Add Query
        page.locator(query);
        page.locator(textFieldQuery).fill("Check the query i have added");
        page.click(queryOk);
        return new QueryPage(page);	
	}
	
	public void createdAt() {
		
		page.click(moreOptions);
		page.click(editorQueryPanel);
		
	}

}
