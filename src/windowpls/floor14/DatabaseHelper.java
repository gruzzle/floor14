package windowpls.floor14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
	private static final String TAG = "DatabaseHelper";

	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// reload db from asset directory
	public static void forceDatabaseReload(Context context){
	    DatabaseHelper dbHelper = new DatabaseHelper(context);
	    dbHelper.setForcedUpgradeVersion(DATABASE_VERSION);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    db.setVersion(-1);
	    db.close();
	    db = dbHelper.getWritableDatabase();
	}
	
	public List<Person> getPeopleByMission(int missionNumber) {
		List<Person> people = new ArrayList<Person>();
		
		SQLiteDatabase db = getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT * FROM People WHERE mission_id = " + missionNumber, null);	
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			people.add(readPerson(c));
		}
		
		return people;
	}
	
	public List<Location> getLocationsByMission(int missionNumber) {
		List<Location> locations = new ArrayList<Location>();
		
		SQLiteDatabase db = getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT * FROM Locations WHERE mission_id = " + missionNumber, null);	
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			locations.add(readLocation(c));
		}
		
		return locations;
	}
	
	public List<Group> getGroupsByMission(int missionNumber) {
		List<Group> groups = new ArrayList<Group>();
		
		SQLiteDatabase db = getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT * FROM Groups WHERE mission_id = " + missionNumber, null);	
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			groups.add(readGroup(c));
		}
		
		return groups;
	}
	
	public Person getPersonById(int id) {
		// TODO use parameterized input?
		SQLiteDatabase db = getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT * FROM People WHERE id = " + id, null);		
		c.moveToFirst();
		
		return readPerson(c);
	}
	
	// returns the Person described at the Cursor position
	private Person readPerson(Cursor c) {
		Person person = new Person();
		
		person.setTitle(c.getString(c.getColumnIndexOrThrow("title")));
		
		// setup override actions
		int personId = c.getInt(c.getColumnIndexOrThrow("id"));
		Cursor overrideCursor = getReadableDatabase().rawQuery("SELECT * FROM Action_Overrides WHERE person_id = " + personId, null);		
		overrideCursor.moveToFirst();
		
		for (int i = 0; i < overrideCursor.getCount(); i++) {
			String type = overrideCursor.getString(overrideCursor.getColumnIndexOrThrow("action_type"));
			String text = overrideCursor.getString(overrideCursor.getColumnIndexOrThrow("text"));
			int chance = overrideCursor.getInt(overrideCursor.getColumnIndexOrThrow("chance"));
			
			// get unlocks
			int actionId = overrideCursor.getInt(overrideCursor.getColumnIndexOrThrow("actionId"));
			Cursor unlockCursor = getReadableDatabase().rawQuery("SELECT * FROM Action2Unlocks WHERE action_id = " + actionId, null);
			for (int j = 0; j < unlockCursor.getCount(); j++) {
				String unlockType = unlockCursor.getString(unlockCursor.getColumnIndexOrThrow("unlock_type"));			
				int unlockId = unlockCursor.getInt(unlockCursor.getColumnIndexOrThrow("unlock_id")) % 1000;
				unlockCursor.moveToNext();
			}
			
			person.addActionOverride(person.new Action(type, text, chance, null));		
		}
				
		return person;
	}
	
	private Location readLocation(Cursor c) {
		Location location = new Location();
		
		location.setName(c.getString(c.getColumnIndexOrThrow("name")));
				
		return location;
	}

	private Group readGroup(Cursor c) {
		Group group = new Group();
		
		group.setName(c.getString(c.getColumnIndexOrThrow("name")));
				
		return group;
	}
	
	public List<Flag> getMissionFlags(int id) {
		List<Flag> flags = new ArrayList<Flag>();
		
		SQLiteDatabase db = getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT * FROM mission_flags WHERE mission_id = " + id, null);		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			String flagName = c.getString(c.getColumnIndexOrThrow("name"));
			Boolean value = c.getInt(c.getColumnIndexOrThrow("starting_value")) == 0 ? false : true;
			flags.add(new Flag(flagName, value));
			c.moveToNext();
		}
		
		return flags;
	}
}