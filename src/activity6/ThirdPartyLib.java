
package activity6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import activity6.dao.DummyData;
import activity6.formatters.UserFileFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ThirdPartyLib extends JPanel {

    private static final long serialVersionUID = 651272650776645711L;

    private final JTable jTable;

    public ArrayList<String[]> excelData = null;

    //This is the file path provided through VM arguments of run configuration
    // This must have read, write and executable access (Example: **/downloads folder)
    private static final String FILE_DIRECTORY = System.getProperty("java.io.tmpdir");

    /**
     *
     * @param filename
     * @throws Exception
     */
    public ThirdPartyLib(String filename) throws Exception {
        // calling extended class's constructor
        super(new BorderLayout(5, 5));
        // creating object of type CSVDataModel
        CSVDataModel csvDataModel = new CSVDataModel();
        // read spreadsheet by filename
        excelData = CSVFile.readSpreadsheet(filename);

        int columnCount = excelData.get(0).length;
        // populate excel data
        csvDataModel.populateData(excelData, columnCount);
        this.jTable = new JTable(csvDataModel);
        // set table dimensions
        this.jTable.setPreferredScrollableViewportSize(new Dimension(700, 70));
        this.jTable.setFillsViewportHeight(true);
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(button, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(jTable);

        add(scrollPane, BorderLayout.CENTER);

        setBorder(new EmptyBorder(7, 7, 7, 7));

        System.out.println("# of rows: " + csvDataModel.getRowCount());
        System.out.println("# of columns: " + csvDataModel.getColumnCount());
    }

    public static class CSVFile {
        public final static ArrayList<String[]> rowsList = new ArrayList<String[]>();
        public static String[] row;
        public static int count;
        public static List<String> headers = null;

        public static ArrayList<String[]> readSpreadsheet(String fileName) {
            try {
                // parse csv file
                CSVParser parser = new CSVParser(new FileReader(fileName), CSVFormat.DEFAULT.withHeader());
                // get records
                List<CSVRecord> csvRecords = parser.getRecords();
                int totalRecords = csvRecords.size();
                // get headers from CSV
                headers = parser.getHeaderNames();
                String[] headersArray = new String[headers.size()];
                for (int i = 0; i < headers.size(); i++) {
                    headersArray[i] = headers.get(i);
                }
                // add header row
                rowsList.add(headersArray);
                // add rows from CSV
                for (int i = 0; i < totalRecords; i++) {
                    CSVRecord record = csvRecords.get(i);
                    row = new String[record.size()];
                    count = 0;
                    record.forEach(e -> {
                        row[count] = e;
                        count++;
                    });

                    rowsList.add(row);
                    System.out.println(Arrays.toString(row));
                }
            } catch (Exception e) {
                System.out.println("File not found:" + e.getMessage());
            }
            // return list
            return rowsList;
        }
    }

    public static void createAndShowGUI(String filename) throws Exception {
        JFrame frame = new JFrame("Spreadsheet Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create object by filename
        ThirdPartyLib thirdPartyLib = new ThirdPartyLib(filename);
        // add object to frame content
        frame.setContentPane(thirdPartyLib);

        frame.pack();
        frame.setVisible(true);
    }

    static class CSVDataModel extends AbstractTableModel {
        public static String[] columnNames;
        public static ArrayList<String[]> data = new ArrayList<String[]>();

        private void populateData(ArrayList<String[]> input, int columnCount) {
            this.data = input;
            this.fireTableDataChanged();
            columnNames = input.get(0);
            input.remove(0);
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int row, int column) {
            return data.get(row)[column];
        }
    }

    /**
     * This method makes use of reflections to get headers and values from an object to fill up the spreadsheet
     * Here we make use of the class to get the field values
     * @param list
     * @param object
     * @param <E>
     * @return
     * @throws Exception
     */
    //The object of type Class<? extends Object> is the second input argument
    //Using this, headers to csv file are provided by the fields of the class (Field[] headers = object.getDeclaredFields();)
    //The values are provided by (String value = (String) fields[j].get(e);)
    public static <E> Class<?> createAndWriteCSV(List<E> list, Class<? extends Object> object) throws Exception {
        String COMMA_DELIMITER = ",";
        String NEW_LINE = "\n";
        // get class fields as headers
        Field[] headers = object.getDeclaredFields();
        // get directory using relative path
        File tempDirectory = new File(FILE_DIRECTORY);
        // get file by absolute path
        File file = new File(tempDirectory.getAbsolutePath() + "/" + object.getSimpleName() + ".csv");
        if (file.exists()) {
            System.out.println("File already exists.");
        }
        // on new file creation
        if (file.createNewFile()) {
            System.out.println("File created at: " + tempDirectory);
            // create file writer to populate csv
            FileWriter fileWriter = new FileWriter(file);
            // add headers
            for (int i = 0; i < headers.length; i++) {
                String header = headers[i].getName().toUpperCase();
                fileWriter.append(header);
                if (i != headers.length - 1) {
                    // set delimiter
                    fileWriter.append(COMMA_DELIMITER);
                } else {
                    // set new line
                    fileWriter.append(NEW_LINE);
                }
            }
            // add data
            for (int i = 0; i < list.size(); i++) {
                // Generic type E is of type UserFileFormatter in this case
                E e = list.get(i);
                Field[] fields = e.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    // get field values
                    String value = (String) fields[j].get(e);
                    fileWriter.append(value);
                    if (j != fields.length - 1) {
                        fileWriter.append(COMMA_DELIMITER);
                    } else {
                        fileWriter.append(NEW_LINE);
                    }
                }
            }
            // flush and close on file
            fileWriter.flush();
            fileWriter.close();
            return object;
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println("Choose \n 1: To perform Read from a .csv file\n 2: To Write to a .csv file");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        // invoke GUI thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            // start the thread
            @Override
            public void run() {
                try {
                    switch (input) {
                        // file read
                        case 1: {
                            // process and display file by filename
                            createAndShowGUI("Movies.csv");
                            break;
                        }
                        // file write and read
                        case 2: {
                            // write dummy data to a file that will be created
                            createAndWriteCSV(DummyData.getUserDummyData(), UserFileFormatter.class);
                            // process and display file by filename
                            createAndShowGUI(FILE_DIRECTORY + "/" + UserFileFormatter.class.getSimpleName() + ".csv");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}