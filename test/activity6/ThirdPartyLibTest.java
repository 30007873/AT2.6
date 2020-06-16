package activity6;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import activity6.ThirdPartyLib.CSVFile;
import activity6.ThirdPartyLib.CSVDataModel;

public class ThirdPartyLibTest {

	private static final Logger log = Logger.getLogger(ThirdPartyLibTest.class.getName());

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup() {
		log.info("Setting up...");

	}

	@After
	public void tearDown() {
		log.info("Tearing down...");

	}

	@BeforeClass
	public static void before() {
		log.info("Running JUnit test cases from class: " + ThirdPartyLibTest.class);

	}

	@AfterClass
	public static void after() {
		log.info("Testing class " + ThirdPartyLibTest.class + " has completed.");

	}

	public void reset() {
		tearDown();
		setup();
	}

//	@Test
//	public void ThirdPartyLibTest() {
//		// requires Mockito and Powermock to test a constructor
//		reset();
//	}

	@Test
	public void readSpreadsheetTest() throws Exception {
		// load spreadsheet by filename
		ThirdPartyLib thirdPartyLib = new ThirdPartyLib("test.csv");
		// get headers
		List<String> headers = CSVFile.headers;
		// get data
		List<String[]> excelData = thirdPartyLib.excelData;
		// get rows
		List<String[]> rowsList = CSVFile.rowsList;
		String[] row = CSVFile.row;
		// validate data
		for (int i = 0; i < headers.size(); i++) {
			assertEquals("test" + (i + 1), headers.get(i));
		}
		// get specific row
		String[] firstRow = excelData.get(0);
		String[] secondRow = excelData.get(1);
		String[] thirdRow = excelData.get(2);
		// asserts for data
		assertEquals(String.valueOf(1), firstRow[0]);
		assertEquals(String.valueOf(4), firstRow[1]);
		assertEquals(String.valueOf(7), firstRow[2]);
		assertEquals(String.valueOf(2), secondRow[0]);
		assertEquals(String.valueOf(5), secondRow[1]);
		assertEquals(String.valueOf(8), secondRow[2]);
		assertEquals(String.valueOf(3), thirdRow[0]);
		assertEquals(String.valueOf(6), thirdRow[1]);
		assertEquals(String.valueOf(9), thirdRow[2]);
		// asserts for header and lengths
		assertEquals(3, headers.size());
		assertEquals(3, excelData.size());
		assertEquals(3, rowsList.size());
		assertEquals(3, row.length);
		assertEquals(3, CSVFile.count);

		reset();
	}

//	@Test
//	public void createAndShowGUITest() {
//		// requires Mockito and Powermock because createAndShowGUI(String string) is a
//		// static method
//		reset();
//	}

//	@Test
//	public void addCSVDataTest() {
//	// requires Mockito and Powermock because the method addCSVData is a
//	// private members of the class
//		reset();
//	}

	@Test
	public void getColumnCountTest() {
		// create model
		CSVDataModel csvDataModel = new CSVDataModel();
		// create columns
		csvDataModel.columnNames = new String[2];
		// assert for column count
		assertEquals(2, csvDataModel.getColumnCount());

		reset();
	}

	@Test
	public void getRowCountTest() {
		// create model
		CSVDataModel csvDataModel = new CSVDataModel();
		// create rows
		String[] row0 = {"data0", "data1"};
		String[] row1 = {"data2", "data3"};
		// add row to data
		csvDataModel.data.add(row0);
		csvDataModel.data.add(row1);
		// assert for rows count
		assertEquals(2, csvDataModel.getRowCount());

		reset();
	}

	@Test
	public void getColumnNameTest() {
		// create model
		CSVDataModel csvDataModel = new CSVDataModel();
		// create columns with names
		csvDataModel.columnNames = new String[2];
		csvDataModel.columnNames[0] = "sample0";
		csvDataModel.columnNames[1] = "sample1";
		// assert for column names
		assertEquals("sample0", csvDataModel.getColumnName(0));
		assertEquals("sample1", csvDataModel.getColumnName(1));

		reset();
	}

	@Test
	public void getValueAtTest() {
		// create model
		CSVDataModel csvDataModel = new CSVDataModel();
		// create rows
		String[] row0 = {"data0", "data1"};
		String[] row1 = {"data2", "data3"};
		// add rows to data
		csvDataModel.data = new ArrayList<String[]>();
		csvDataModel.data.add(row0);
		csvDataModel.data.add(row1);
		// assert for cell values
		assertEquals("data0", csvDataModel.getValueAt(0, 0));
		assertEquals("data1", csvDataModel.getValueAt(0, 1));
		assertEquals("data2", csvDataModel.getValueAt(1, 0));
		assertEquals("data3", csvDataModel.getValueAt(1, 1));

		reset();
	}
}
