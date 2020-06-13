package org.qa.rediffmail.kyd.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.maven.kyd.frameowrk.testbase.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class KydEngine {
	public WebElement element;
	public WebDriver driver;
	public Properties prop;
	public static Workbook workbook;
	public static Sheet sheet;
	public TestBase base;
	public final String SCENARIO_SHEET_PATH = "C:\\Users\\My Pc\\eclipse-workspace\\org.maven.kyd.frameowrk\\src\\main\\java"
			+ "\\org\\qa\\reiffmail\\kyd\\scenarios\\data.xls";

	public void startExecution(String sheetName) {
		String locatorName = null;
		String locatorValue = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sheet = workbook.getSheet(sheetName);
		int rows = sheet.getLastRowNum();
		int column = 0;
		for (int i = 0; i < rows; i++) {
			try {
				String locatorColValue = sheet.getRow(i + 1).getCell(column + 1).toString().trim();
				if (!locatorColValue.equalsIgnoreCase("NA")) {
					locatorName = locatorColValue.split("=")[0].trim();
					locatorValue = locatorColValue.split("=")[1].trim();
				}
				String action = sheet.getRow(i + 1).getCell(column + 2).toString().trim();
				String value = sheet.getRow(i + 1).getCell(column + 3).toString().trim();
				switch (action) {
				case "open browser":
					base = new TestBase();
					prop = base.init_properties();

					if (value.equalsIgnoreCase("NA") || value.isEmpty()) {
						driver = base.init_driver(prop.getProperty("browser"));
					} else {
						driver = base.init_driver(value);
					}

					break;
				case "enter url":
					if (value.equalsIgnoreCase("NA") || value.isEmpty()) {
						driver.get(prop.getProperty("url"));
					} else {
						driver.get(value);
					}
					break;
				case "quit":
					driver.quit();
					break;
				default:
					break;
				}

				switch (locatorName) {
				case "class":
					element = driver.findElement(By.className(locatorValue));
					if (action.equalsIgnoreCase("click")) {
						element.click();

					} else if (action.equalsIgnoreCase("sendKeys")) {
						element.clear();
						element.sendKeys(value);
					}
					locatorName = null;
					break;
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("click")) {
						element.click();

					} else if (action.equalsIgnoreCase("sendKeys")) {
						element.clear();
						element.sendKeys(value);
					}
					locatorName = null;
					break;
				case "name":
					element = driver.findElement(By.name(locatorValue));
					if (action.equalsIgnoreCase("click")) {
						element.click();

					} else if (action.equalsIgnoreCase("sendKeys")) {
						element.clear();
						element.sendKeys(value);
					}
					locatorName = null;
					break;
				default:
					break;
				}
			} catch (Exception e) {
				//
			}
		}
	}
}
