package com.qa.jms.base;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Page;
import com.qa.jms.factory.PlaywrightFactory;
import com.qa.jms.pages.AddArticle;
import com.qa.jms.pages.EditorPage;
import com.qa.jms.pages.HomePage;

public class BaseTest {

    private PlaywrightFactory pf;
    protected Page page;
    protected HomePage homepage;
    protected Properties prop;
    protected EditorPage editorPage; 
    

    @BeforeTest
    public void setUp() throws IOException {
        pf = new PlaywrightFactory();
        prop = pf.initProp();
        page = pf.initBrowser(prop);
        homepage = new HomePage(page);
    }

  
}
