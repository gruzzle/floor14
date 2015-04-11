package windowpls.floor14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import windowpls.floor14.Person.ActionResult;
import android.util.Log;

public class Mission {
	private static final String TAG = "Mission";
	
	public static DatabaseHelper db;
	private int missionId;
	
	private final  Person[] people;
	private final Location[] locations;
	private final Group[] groups;		
	private final Flag[] flags;
	
	public Mission(int missionId) {
		this.missionId = missionId;
		
		people = db.getPeopleByMission(missionId).toArray(new Person[0]);
		locations = db.getLocationsByMission(missionId).toArray(new Location[0]);
		groups = db.getGroupsByMission(missionId).toArray(new Group[0]);
		flags = db.getMissionFlags(missionId).toArray(new Flag[0]);
		
		people[0].setActive(true);
		
		// print stuff
		Log.d(TAG, "People:");
		for (Person p : people) {
			Log.d(TAG, String.format("%s, %s", p.getName(), p.getTitle()));
		}
		Log.d(TAG, "Locations:");		
		for (Location l : locations) {
			Log.d(TAG, l.getName());
		}
		Log.d(TAG, "Groups:");
		for (Group g : groups) {
			Log.d(TAG, g.getName());
		}
		Log.d(TAG, "Flags:");		
		for (Flag f : flags) {
			Log.d(TAG, String.format("%s: %s", f.name, f.value));
		}
		
		Log.d(TAG, " ");	
		Log.d(TAG, String.format("Available people: "));		
		for (Person p : people) {
			if (p.isActive()) {
				Log.d(TAG, String.format("%s, %s", p.getName(), p.getTitle()));
			}
		}
		
	}
}
