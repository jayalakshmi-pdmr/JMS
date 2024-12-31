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
import com.qa.jms.utils.ExcelReader;

public class AddPublisherPage {
	private Page page;
    private ExcelReader excelReader;
  
    private Map<String, Integer> columnHeaders;
  
    
	//Constructor
    public AddPublisherPage(Page page, String excelFilePath, String sheetName) {
        this.page = page;
        this.excelReader = new ExcelReader(excelFilePath, sheetName);
        this.columnHeaders = createColumnHeaderMap();
    }
	
	
	//Locators
	public String menuIcon = "//span[@aria-hidden='true']//img";
	public static String  addPubicon = ".svg-inline--fa.fa-book-open-reader";
	public String pubAcronym = "//input[contains(@data-testid,'publisher-acronym')]";
	public String pubName  = "//input[contains(@data-testid,'publisher-name')]";
	public String email = "//input[@type='email']";
	public String bccEmail = "//input[@data-testid='bcc-email-account']";
	public String description = "//textarea[@id='description']";
	public String date = "//input[@type='date']";
	public String location = "//input[@data-testid='publisher-location']";
	
	//FTP
	public String host = "//input[contains(@data-testid,'ftp-host')]";
	public String  ftpUname = "//input[contains(@data-testid,'ftp-user-name')]";
	public String ftpPwd = "//input[contains(@type,'password')]";
	public String directory = "//input[@data-testid='ftp-initial-directory']";
	
	//General TAT
	public String latexDate = "//div[@class='_main-container_4alud_1']//div[1]//div[1]//div[1]//div[1]//div[1]//input[1]";
	public String graphicsDate = "//div[@class='_main-container_4alud_1']//div[1]//div[1]//div[1]//div[2]//div[1]//input[1]";
	public String preDate = "//div[@class='_main-container_4alud_1']//div[1]//div[1]//div[1]//div[3]//div[1]//input[1]";
	public String ceDate = "//div[@class='_main-container_4alud_1']//div[1]//div[1]//div[1]//div[4]//div[1]//input[1]";
	public String tsDate = "//div[@class='_main-container_4alud_1']//div[1]//div[1]//div[1]//div[5]//div[1]//input[1]";
	public String qcDate = "//div[@class='_main-container_4alud_1']//div[1]//div[1]//div[1]//div[6]//div[1]//input[1]";
	
	//TAT AU
	public String  auPag = "//div[@class='_main-container_4alud_1']//div[1]//div[2]//div[1]//div[1]//div[1]//input[1]";
	public String auQC = "//div[@class='_main-container_4alud_1']//div[1]//div[2]//div[1]//div[2]//div[1]//input[1]";
	
	//PE TAT
	public String pePAg = "//div[@class='_main-container_4alud_1']//div[1]//div[3]//div[1]//div[1]//div[1]//input[1]";
	public String peQC = "//div[@class='_main-container_4alud_1']//div[1]//div[3]//div[1]//div[2]//div[1]//input[1]";
	
	//Online TAT
	public String onlinePag ="//div[@class='_main-container_4alud_1']//div[1]//div[4]//div[1]//div[1]//div[1]//input[1]";
	public String onlineQC = "//div[@class='_main-container_4alud_1']//div[1]//div[4]//div[1]//div[2]//div[1]//input[1]";
	public String onlineXML = "//div[@class='_main-container_4alud_1']//div[1]//div[4]//div[1]//div[3]//div[1]//input[1]";
	
	//Issue TAT
	public String issuePag = "//div[@class='_main-container_4alud_1']//div[1]//div[5]//div[1]//div[1]//div[1]//input[1]";
	public String issueQC = "//div[@class='_main-container_4alud_1']//div[1]//div[5]//div[1]//div[2]//div[1]//input[1]";
	
	//Print TAT
	public String printPag = "//div[@class='_main-container_4alud_1']//div[1]//div[6]//div[1]//div[1]//div[1]//input[1]";
	public String printQC = "//div[@class='_main-container_4alud_1']//div[1]//div[6]//div[1]//div[2]//div[1]//input[1]";
	public String printXML = "//div[@class='_main-container_4alud_1']//div[1]//div[6]//div[1]//div[3]//div[1]//input[1]";
	
	//replicate values
	public String fastTrack ="//img[@title='Replicate values from General to Fasttrack']";
	public String replicate = "//newbutton[@class='_successBtn_1gdpo_120 _btn_1gdpo_90']";
	
	//other
	public String pubLogo = "#real_input";
	public String pubStyle = "#styles-upload";
	public String pubGuideline = "#guidelines-upload";
	public String addpubButton = "//button[contains(text(),'Add Publisher')]";
	
	//confimation Message:
	public String pubAdded = "//div[contains(text(),'Publisher Added Successfully')]";
	
	
	
	
	
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

    
	public void navigateToAddPublisher() {
	    page.click(menuIcon); // Click on the menu icon
	    page.click(addPubicon); // Click on the add publisher icon
	}
	
	//Function to enter publisher details
    public void enterPublisherDetails() {
        List<String> dataMap = excelReader.getRowData(1); // Assuming the second row contains the necessary data

        // Wait and fill Publisher Details
        waitForElement(pubAcronym);
        page.fill(pubAcronym, getDataByColumnName(dataMap, "Publisher Acronym"));

        waitForElement(pubName);
        page.fill(pubName, getDataByColumnName(dataMap, "Publisher Name"));

        waitForElement(email);
        page.fill(email, getDataByColumnName(dataMap, "Email"));

        waitForElement(bccEmail);
        page.fill(bccEmail, getDataByColumnName(dataMap, "BccEmail"));

        waitForElement(description);
        page.fill(description, getDataByColumnName(dataMap, "Description"));

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

        waitForElement(date);
        page.fill(date, isoDate); // Fill the date field

        waitForElement(location);
        page.fill(location, getDataByColumnName(dataMap, "Location"));

        // Fill FTP details
        waitForElement(host);
        page.fill(host, getDataByColumnName(dataMap, "FTP Host"));

        waitForElement(ftpUname);
        page.fill(ftpUname, getDataByColumnName(dataMap, "FTP Username"));

        waitForElement(ftpPwd);
        page.fill(ftpPwd, getDataByColumnName(dataMap, "FTP Password"));

        waitForElement(directory);
        page.fill(directory, getDataByColumnName(dataMap, "FTP Directory"));

        // Fill TAT details dynamically
        waitForElement(latexDate);
        page.fill(latexDate, getDataByColumnName(dataMap, "Latex Date"));

        waitForElement(graphicsDate);
        page.fill(graphicsDate, getDataByColumnName(dataMap, "Graphics Date"));

        waitForElement(preDate);
        page.fill(preDate, getDataByColumnName(dataMap, "Pre Date"));

        waitForElement(ceDate);
        page.fill(ceDate, getDataByColumnName(dataMap, "CE Date"));

        waitForElement(tsDate);
        page.fill(tsDate, getDataByColumnName(dataMap, "TS Date"));

        waitForElement(qcDate);
        page.fill(qcDate, getDataByColumnName(dataMap, "QC Date"));

        // Continue filling other fields dynamically
        waitForElement(auPag);
        page.fill(auPag, getDataByColumnName(dataMap, "AU Pag"));

        waitForElement(auQC);
        page.fill(auQC, getDataByColumnName(dataMap, "AU QC"));

        waitForElement(pePAg);
        page.fill(pePAg, getDataByColumnName(dataMap, "PE Pag"));

        waitForElement(peQC);
        page.fill(peQC, getDataByColumnName(dataMap, "PE QC"));

        waitForElement(onlinePag);
        page.fill(onlinePag, getDataByColumnName(dataMap, "Online Pag"));

        waitForElement(onlineQC);
        page.fill(onlineQC, getDataByColumnName(dataMap, "Online QC"));

        waitForElement(onlineXML);
        page.fill(onlineXML, getDataByColumnName(dataMap, "Online XML"));

        waitForElement(issuePag);
        page.fill(issuePag, getDataByColumnName(dataMap, "Issue Pag"));

        waitForElement(issueQC);
        page.fill(issueQC, getDataByColumnName(dataMap, "Issue QC"));

        waitForElement(printPag);
        page.fill(printPag, getDataByColumnName(dataMap, "Print Pag"));

        waitForElement(printQC);
        page.fill(printQC, getDataByColumnName(dataMap, "Print QC"));

        waitForElement(printXML);
        page.fill(printXML, getDataByColumnName(dataMap, "Print XML"));
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
       
 // Method to replicate values on FastTrack
    public void replicateValuesFastTrack() {
        // Wait for FastTrack button to be visible before clicking
        waitForElement(fastTrack);
        page.click(fastTrack);

        // Wait for Replicate button to be visible before clicking
        waitForElement(replicate);
        page.click(replicate);
    }
    
    

    public void uploadPublisherLogo(String fileName) {
    	page.setInputFiles(pubLogo,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\publisherLogo.jpg"));
        
    }

    public void uploadPublisherStyle(String fileName) {
        // Wait for Publisher Style input field to be ready before uploading the file
    	page.setInputFiles(pubStyle,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\publisherStyle.sty"));
    }

    public void uploadPublisherGuideline(String fileName) {
    	page.setInputFiles(pubGuideline,Paths.get("C:\\Users\\sjayalakshmi\\eclipse-workspace\\jmsUploadFiles\\publisherGuideline.pdf"));
    }
    

    // Method to click the Add Publisher button
    public void clickAddPublisherButton() {
        // Wait for Add Publisher button to be visible before clicking
        waitForElement(addpubButton);
        page.click(addpubButton);
    }
	

}
