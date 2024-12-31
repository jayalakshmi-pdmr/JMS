package com.qa.jms.pages;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.qa.jms.utils.ExcelReader;

public class AddJournalPage {
	private Page page;
    private ExcelReader excelReader;
  
    private Map<String, Integer> columnHeaders;
  
    
	//Constructor
    public AddJournalPage(Page page, String excelFilePath, String sheetName) {
        this.page = page;
        this.excelReader = new ExcelReader(excelFilePath, sheetName);
        this.columnHeaders = createColumnHeaderMap();
    }
	
	
	//Locators
    public String menuIcon = "//span[@aria-hidden='true']//img";
	public String addjournal =".svg-inline--fa.fa-book";
	public String pubName = "#publisher";
	public String selectPub = "//li[@class='_dropdownList_1syn6_240']";
	public String journalAcro ="//input[@data-testid='journal-acronym ']";
	public String journalRecDate = "input[placeholder='dd-mm-yyyy']";
	public String journalName = "//div[@class='ql-editor ql-blank']";
	public String remarks  = "#remarks";
	public String journalWorkDetails = "div.no-select div._main-container_4alud_1 main._main-content-container_4alud_23:nth-child(4) section._main-content_4alud_23:nth-child(1) div._journal-work-details-container_1mgi3_108 div._text-inputs-container_1mgi3_128 div._text-input-container_1mgi3_245:nth-child(2) div._input-multi-container_1dsei_8 div._multi-select-form-controls_1dsei_1 > input._multi-select-input-box_1dsei_14:nth-child(1)";
	//public String journalStage = "\\_multi-option-items_1dsei_175:nth-child(1) > .\\_checkbox-selection_1dsei_186";
	public String checkBox = "//p[normalize-space()='Typesetting']";
	public String pubType = "//*[@id=\"root\"]/div/div/main/section/main/form/div[2]/div/div[3]/div/div[2]/input";
	public String online = "//body/div[@id='root']/div[1]/div[1]/main[1]/section[1]/main[1]/form[1]/div[2]/div[1]/div[3]/div[1]/div[2]/div[1]/ul[1]/li[1]/input[1]";
	public String importDate = "//div[@title='Import tats from Publisher']//img";
	public String importStyle = "//div[@class='_import-icon-container_1mgi3_488']//img";
	public String yesBtn = "//newbutton[@class='_successBtn_1gdpo_120 _btn_1gdpo_90']";
	public String importGuidline = "div[class='_import-icon-container_1mgi3_488'] img[class='_import-icon_1mgi3_488']";
	public String addJournalbtn = "button[class='_btn_1deo3_1 _submit-btn_1mgi3_850']";
	public String journalStyle = "//input[@id='styles-upload']";
	public String journalGuideline = "//input[@id='guidelines-upload']";
	public static String journalText = "//div[contains(text(),'Journal Added Successfully')]";
	
	////input[@value='Typesetting']
	
	
	public void setExcelReader(String filePath, String sheetName) {
        this.excelReader = new ExcelReader(filePath, sheetName);
    }
	
	
	
	// Methods to interact with Excel
    private Map<String, Integer> createColumnHeaderMap() {
        List<String> headers = excelReader.getHeaders();
        return IntStream.range(0, headers.size())
                .boxed()
                .collect(Collectors.toMap(headers::get, i -> i));
    }

    private String getDataByColumnName(List<String> rowData, String columnName) {
        Integer colIndex = columnHeaders.get(columnName);
        if (colIndex != null && colIndex < rowData.size()) {
            return rowData.get(colIndex);
        }
        return "";
    }

    
	public void navigateToAddJournalPage() {
	    page.click(menuIcon); // Click on the menu icon
	    page.click(addjournal); // Click on the add publisher icon
	}
	
	
	public void selectPublisher() {
	    // Click on the initial publisher name (if needed)
	    page.locator(pubName).click();

	    // Get the publisher list
	    Locator pubList = page.locator(selectPub);
	    int publcount = pubList.count();

	    // Iterate through the list and find the publisher "SLR"
	    for (int i = 0; i < publcount; i++) {
	        String publisherName = pubList.nth(i).textContent().trim();  // Get the text of the individual item and trim it to avoid leading/trailing spaces
	        if (publisherName.equalsIgnoreCase("SLR")) {
	            pubList.nth(i).click();  // Click on the correct publisher
	            return;  // Exit the method after finding and clicking the publisher
	        }
	    }

	    // If we get here, the publisher was not found in the list
	    System.out.println("Publisher 'SLR' not found");
	}

	
	
	//Function to enter publisher details
    public void enterJournalDetails() {
        List<String> dataMap = excelReader.getRowData(1); // Assuming the second row contains the necessary data
        page.fill(journalAcro, getDataByColumnName(dataMap, "JouralAcronym"));
        

        // Convert date format dynamically with additional validation
        String inputDate = getDataByColumnName(dataMap, "Date"); // Assuming "Date" is the header name

        String isoDate = null;
        if (isValidDate(inputDate)) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            isoDate = LocalDate.parse(inputDate, inputFormatter).format(outputFormatter);
        } else {
            System.out.println("Invalid date format: " + inputDate);
            // Set a default date if the provided date is invalid
            isoDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Use today's date as fallback
       
        }
        
        waitForElement(journalRecDate);
        page.fill(journalRecDate, isoDate); // Fill the date field
        
        page.fill(journalName, getDataByColumnName(dataMap, "JournalName"));
        page.fill(remarks, getDataByColumnName(dataMap, "Remarks"));
        }

      

   // Helper method to validate the date format
    private boolean isValidDate(String dateStr) {
        try {
            // Try parsing the date in the expected format
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(dateStr, inputFormatter);
            return true;
        } catch (Exception e) {
            return false; // Return false if the date format is invalid
        }
    }

    // Helper method to wait for an element to be visible

    private void waitForElement(String selector) {
        Locator locator = page.locator(selector); // Create a locator object
        locator.waitFor(); // Wait for the element to be visible (default timeout is 30 seconds)
    }
       


    public void selectJournalWork() {
        // Wait for the dropdown to be visible and clickable
        Locator dropdown = page.locator(journalWorkDetails);
        dropdown.waitFor(new Locator.WaitForOptions().setTimeout(10000));  // Wait for the element to appear with a timeout
        dropdown.click();
        
        /* Wait for the journal stage items to be available
        waitForElement(journalStage);
       page.locator(journalStage).click();*/
       
       waitForElement(checkBox);
       Locator check = page.locator(checkBox);
       
       check.evaluate("element => element.click()");

       
    }

	
	
	public void selectPubType() {
		waitForElement(pubType);
		page.locator(pubType).click();
		waitForElement(online);
		page.locator(online).click();
		}
	
	public void dateImport() {
		page.locator(importDate).click();
		page.locator(yesBtn).click();
		}
	
	  public void uploadJournalStyle(String fileName) {
	        // Wait for Publisher Style input field to be ready before uploading the file
	    	page.setInputFiles(journalStyle,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\publisherStyle.sty"));
	    }

	    public void uploadJournalGuideline(String fileName) {
	    	page.setInputFiles(journalGuideline,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\publisherGuideline.pdf"));
	    }
	    
	public String clickButtons() {
		page.locator(addJournalbtn).click();
		String text =page.locator(journalText).textContent();
		return text;
	}
    
}
