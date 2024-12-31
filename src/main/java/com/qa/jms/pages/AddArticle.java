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

public class AddArticle {
	private Page page;
    private ExcelReader excelReader;
  
    private Map<String, Integer> columnHeaders;
  
    
	//Constructor
    public AddArticle(Page page, String excelFilePath, String sheetName) {
        this.page = page;
        this.excelReader = new ExcelReader(excelFilePath, sheetName);
        this.columnHeaders = createColumnHeaderMap();
    }
	
	
  //Locators
    public String menuIcon = "//span[@aria-hidden='true']//img";
    public String articleBtn = ".svg-inline--fa.fa-newspaper";
    public String form = "//img[@alt='Option 2 (Form)']";
    public String selectPublisher = "//input[@id='publisher']";
    public String ulList = "//li[@class='_dropdownList_1syn6_240']";
    public String articleID = "//input[@id='articleID']";
    public String authorMail = "//input[@id='authorMail']";
    public String authorName = "div[class='_second-row_1av2b_90'] div[class='_inputDiv_1av2b_62'] div div[class='ql-editor ql-blank']";
    public String articleName = "div[class='_third-row_1av2b_170'] div[class='_inputDiv_1av2b_62'] div div[class='ql-editor ql-blank']";
    public String priority = "//input[@id='priority']";
    public String selectpriority = "//p[contains(text(),'Low')]";
    public String recvDate = "//input[@id='receivedDate']";
    public String doi = "//input[@id='doi']";
    public String workFlow  = "//p[@title='Assign Workflow']";
   // public String selectworkFlow = "//div[@id='overlay-root']//div[5]//div[2]";
    public String sWorkFlow = "//div[@id='overlay-root']//div[5]//div[2]//img";
    public String assignWorkFlow = "//button[normalize-space()='Assign']";
    public String articleType = "//input[@placeholder='Enter Article Type...']";
    public String pages = "//input[@id='numberOfPages']";
    public String turnAroundTime = "//p[normalize-space()='Turn Around Time']";
    public String trackType = "//input[@id='trackType']";
    public String general = "//p[@for='General']";
    public String importTime = "//img[@title='Import TATs from Journal']";
    public String yesButton  = "//newbutton[@class='_successBtn_1gdpo_120 _btn_1gdpo_90']";
    public String startDate = "//input[@id='startDate']";
    public String checklistSelection = "//p[@title='Show']";
    public String supplement = "//button[@id='supplement']//div[@class='_thumb_1av2b_214']";
    public String aopFree = "//button[@id='acpFree']";
    public String openAccess = "//button[@id='acpOpenAccess']";
    public String tables = "//button[@id='tables']";
    public String displayFigure = "//button[@id='displayFigures']";
    public String inlineFigure = "//button[@id='inlineFigures']";
    public String fileUpload = "//label[normalize-space()='File Upload']";
    public String drag = "//input[@type='file']";
    public String zipFile = "//div[@class='_split-1_1av2b_282']";
    public String addNotes = "//p[contains(text(),'Add Notes')]";
    public String writeNotes = "//div[@class='ql-editor ql-blank']";
    public String addNoteBtn = "//button[normalize-space()='Add Note']";
    public String closeNotes = "//span[contains(text(),'×')]";
    public String checkList  = "//label[normalize-space()='Check Lists']";
    public String figures  = "//input[@id='figures']";
    public String table = "//input[@id='tables']";
    public String supplementary = "//input[@id='supplementary']";
    public String submitChecklist = "//button[normalize-space()='Submit CheckList']";
    public String selectPreview = "//label[normalize-space()='Preview']";
    public String notificationCC = "//body/div[@id='root']/div[1]/div[1]/main[1]/section[1]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/input[1]";
    public String selectPM = "//p[text()='pm@gmail.com']";
    public String To = "//body/div[@id='root']/div[1]/div[1]/main[1]/section[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/input[1]";
    public String nComp = "//p[contains(text(),'compuscriptrep@gmail.com')]";
    public String aSelect = "//body/div[@id='root']/div[1]/div[1]/main[1]/section[1]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/input[1]";
    public String selectPM2 = "//p[text()='pm2@gmail.com']";
    public String nToSelect = "//body/div[@id='root']/div[1]/div[1]/main[1]/section[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/input[1]";
    public String nselectpm = "//li[@class='_multi-option-items_1dsei_175']//p[text()='pm@gmal.com']";
    public String saveMail = "//button[contains(text(),'Save Mail')]";
    public  String yes = "//newbutton[contains(text(),'Yes')]";
    public String addArticleBtn = "//div[contains(text(),'Add Article')]";
    public static String articleText = "//p[contains(text(),'J12356847')]";
    
	
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

    
	public void navigateToAddArticle() {
	    page.click(menuIcon); // Click on the menu icon
	    page.click(articleBtn); // Click on the add publisher icon
	    page.click(form);
	}
	
	
	public void selectPublisher() {
	    // Click on the initial publisher name (if needed)
	    page.locator(selectPublisher).click();

	    // Get the publisher list
	    Locator pubList = page.locator(ulList);
	    int publcount = pubList.count();

	    // Iterate through the list and find the publisher "SLR"
	    for (int i = 0; i < publcount; i++) {
	        String publisherName = pubList.nth(i).textContent().trim();  // Get the text of the individual item and trim it to avoid leading/trailing spaces
	        if (publisherName.equalsIgnoreCase("BLR(SLR)")) {
	            pubList.nth(i).click();  // Click on the correct publisher
	            return;  // Exit the method after finding and clicking the publisher
	        }
	    }

	    // If we get here, the publisher was not found in the list
	    System.out.println("Publisher 'BLR(SLR)' not found");
	}

	
	
	//Function to enter publisher details
    public void enterMetaData() {
        List<String> dataMap = excelReader.getRowData(1); // Assuming the second row contains the necessary data
        page.fill(articleID, getDataByColumnName(dataMap, "ArticleId"));
        page.fill(authorMail, getDataByColumnName(dataMap, "AuthorMail"));
        page.fill(authorName,getDataByColumnName(dataMap, "AuthorName"));
        page.fill(articleName,getDataByColumnName(dataMap,"ArticleName"));
        page.click(priority);
        page.click(selectpriority);
        page.click(checklistSelection);
        // Convert the date to ISO format and fill it
        String inputDate = getDataByColumnName(dataMap, "Date"); // Assuming "Date" is the header name
        String isoDate = convertDate(inputDate);
        page.fill(recvDate, isoDate);
        page.fill(doi, getDataByColumnName(dataMap,"DOI"));
        page.click(workFlow);
        page.click(sWorkFlow);
        page.click(assignWorkFlow);
        page.fill(articleType, getDataByColumnName(dataMap,"ArticleType"));
        page.fill(pages, getDataByColumnName(dataMap,"Pages"));
        }
    
    public void turnAroundDeatils() {
    	page.click(turnAroundTime);
    	//page.click(general);
    	page.click(importTime);
    	page.click(yesButton);
    	
    	// Step 1: Get today's date in yyyy-MM-dd format
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Step 2: Locate the calendar input field using the provided XPath
        Locator calendarInput = page.locator(startDate);

        // Step 3: Wait for the calendar input to be visible
        calendarInput.waitFor();

        // Step 4: Fill the input field with today's date
        calendarInput.fill(todayDate);

        // Step 5: Optional: Verify the value after filling
        String selectedDate = calendarInput.inputValue();
        if (selectedDate.equals(todayDate)) {
            System.out.println("Date selected successfully: " + todayDate);
        } else {
            System.out.println("Error: The selected date is incorrect. Expected: " + todayDate + ", but got: " + selectedDate);
        }
    }
    
    public void checklistSelection() {
    	page.click(checklistSelection);
    	page.click(supplement);
    	page.click(displayFigure);
    	page.click(inlineFigure);
    }
    
    public void fileUpload() {
    	page.click(fileUpload);
    	page.setInputFiles(drag,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\chapter1.docx"));
    	page.click(zipFile);
    	page.setInputFiles(drag,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\GGD-804.zip"));
    }
    
    public void notes() {
    	page.click(addNotes);
    	page.fill(writeNotes, "check the notes given");
    	page.click(addNoteBtn);
    	page.click(closeNotes);
    }
    
    public void checkList() {
    	page.click(checkList);
    	page.click(figures);
    	page.click(table);
    	page.click(supplementary);
    	page.click(submitChecklist);
    	page.click(yesButton);
    	page.click(closeNotes);
    	
    }
    
    public void mail() {
    	page.click(selectPreview);
    	page.click(notificationCC);
    	page.click(selectPM);
    	page.click(To);
    	page.click(nComp);
    	page.click(saveMail);
    	page.click(yesButton);
    	page.click(closeNotes);
    	page.click(aSelect);
    	page.click(selectPM2);
    	page.click(nToSelect);
    	page.click(nselectpm);
    	page.click(saveMail);
    	page.click(yes);
    	page.click(closeNotes);
    }
    
    public String validateArticleAdded() {
    	page.click(addArticleBtn);
    	page.click(closeNotes);
    	String text=page.locator(articleText).textContent();
    	
    	return text;
 
    }
    
    private String convertDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, inputFormatter).format(outputFormatter);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + date);
            // Return today's date in ISO format as fallback
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
