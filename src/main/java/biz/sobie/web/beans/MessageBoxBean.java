package biz.sobie.web.beans;

import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.SimpleGroupsModel;

public class MessageBoxBean {

	GroupsModel model;
	public static String[][] todayStrings = new String[][] { new String[] { "1", "[zk1 - Help] RE: Bandbox Autocomplete Problem", "2010/11/18 10:16:27", "16KB" },
        new String[] { "2", "[zk1 - Help] RE: Bandbox Autocomplete Problem", "2010/11/18 10:14:27", "18KB" },
        new String[] { "3", "[zk1 - Help] RE: Databinding with radiogroups", "2010/11/18 09:47:27", "12KB" },
        new String[] { "4", "[zk1 - Help] RE: It's not possible to navigate a listbox' ite", "2010/11/18 09:35:27", "12KB" },
        new String[] { "5", "[zk1 - Help] RE: ZK problem in dynamic menu", "2010/11/18 08:37:27", "12KB" }, new String[] { "6", "[zk1 - Help] RE: FileUpload", "2010/11/18 08:27:57", "14KB" },
        new String[] { "7", "[zk1 - Help] RE: Datebox format", "2010/11/18 06:27:31", "11KB" }, new String[] { "8", "[zk1 - Help] RE: Datebox format", "2010/11/18 06:17:22", "12KB" },
        new String[] { "9", "[zk1 - Help] FileUpload", "2010/11/18 05:07:25", "11KB" }, new String[] { "10", "[zk1 - Help] FileUpload", "2010/11/18 05:07:25", "11KB" },
        new String[] { "11", "[zk1 - Help] FileUpload", "2010/11/18 05:07:25", "11KB" },
        new String[] { "12", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/18 04:44:17", "12KB" },
        new String[] { "13", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/18 04:44:17", "12KB" },
        new String[] { "14", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/18 04:44:17", "12KB" },
        new String[] { "15", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/18 04:44:17", "12KB" },
        new String[] { "16", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/18 04:31:12", "14KB" } };
	public static String[][] yesterdayStrings = new String[][] { new String[] { "1", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/17 19:44:17", "39KB" },
        new String[] { "2", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/17 19:42:17", "35KB" },
        new String[] { "3", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/17 19:40:17", "26KB" },
        new String[] { "4", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/17 18:14:17", "27KB" },
        new String[] { "5", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/17 17:05:17", "18KB" },
        new String[] { "6", "[zk1 - General] RE: Opening more than one new browser window", "2010/11/17 16:44:17", "19KB" },
        new String[] { "7", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/17 13:31:12", "14KB" },
        new String[] { "8", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/17 13:31:12", "14KB" },
        new String[] { "9", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/17 13:31:12", "14KB" },
        new String[] { "10", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/17 13:31:12", "14KB" },
        new String[] { "11", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/17 13:26:37", "14KB" },
        new String[] { "12", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/17 13:26:37", "14KB" },
        new String[] { "13", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/17 13:26:37", "14KB" },
        new String[] { "14", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/17 10:41:33", "14KB" },
        new String[] { "15", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/17 10:14:27", "14KB" } };
	public static String[][] lastWeekStrings = new String[][] { new String[] { "1", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/15 04:31:12", "14KB" },
        new String[] { "2", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/15 04:31:12", "14KB" },
        new String[] { "3", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/15 04:31:12", "14KB" },
        new String[] { "4", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/15 03:26:37", "14KB" },
        new String[] { "5", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/15 03:26:37", "14KB" },
        new String[] { "6", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/15 03:26:37", "14KB" },
        new String[] { "7", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/14 12:41:33", "14KB" },
        new String[] { "8", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/14 02:41:33", "14KB" },
        new String[] { "9", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/14 02:41:33", "14KB" },
        new String[] { "10", "[zk1 - Help] RE: Times_Series Chart help", "2010/11/14 02:41:33", "14KB" },
        new String[] { "11", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/13 02:14:27", "14KB" },
        new String[] { "12", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/13 02:14:27", "14KB" },
        new String[] { "13", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/13 02:14:27", "14KB" },
        new String[] { "14", "[zk1 - Help] RE: SelectedItemConverter Question", "2010/11/13 02:14:27", "14KB" } };
	public static String[][][] allDataStrings = new String[][][] { todayStrings, yesterdayStrings, lastWeekStrings };
	public static String[] groups = new String[] {"Date: Today", "Date: Yesterday", "Date: Last Week"};
	
	public MessageBoxBean() {
		
		this.model = new SimpleGroupsModel(allDataStrings, groups);
	}

	public GroupsModel getModel() {
		return model;
	}

	public void setModel(GroupsModel model) {
		this.model = model;
	}
	
	
	
	
	
}
