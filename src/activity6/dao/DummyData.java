package activity6.dao;

import activity6.formatters.UserFileFormatter;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<UserFileFormatter> USER_DUMMY_DATA = new ArrayList<UserFileFormatter>();
    // sample dataset
    public static List<UserFileFormatter> getUserDummyData(){
        USER_DUMMY_DATA.add(new UserFileFormatter("Jai", "30007873@tafe.wa.edu.au", "30007873"));
        USER_DUMMY_DATA.add(new UserFileFormatter("Oz", "1234567@tafe.wa.edu.au", "1234567"));
        return USER_DUMMY_DATA;
    }
}
