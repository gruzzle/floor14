package windowpls.floor14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class Mission {
	private static final String TAG = "Mission";
	
	public static DatabaseHelper db;
	private int missionId;
	private int time;
	private int damage;
	
	private final  Person[] people;
	private final Location[] locations;
	private final Group[] groups;		
	private final Flag[] flags;
	
	public Person[] getPeople() { return people; }
	public Location[] getLocations() { return locations; }
	public Group[] getGroups() { return groups; }
	public Flag[] getFlags() { return flags; }
	public int getTime() { return time; }
	public int getDamage() { return damage; }
		
	public Mission(int missionId) {
		this.missionId = missionId;
		time = 0;
		damage = 0;
		
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
	
	public List<Actionable> getActive() {
		List<Actionable> active = new ArrayList<Actionable>();
		
		for (Person p : people) {
			if (p.isActive()) {
				active.add(p);
			}
		}
		for (Location l : locations) {
			if (l.isActive()) {
				active.add(l);
			}
		}
		for (Group g : groups) {
			if (g.isActive()) {
				active.add(g);
			}
		}
		
		return active;
	}
	
	public void incTime() {
		time += 1;
	}
	
	public void addDamage(int damage) {
		this.damage += damage;
	}
	
	public void setFlag(String flagName, boolean newValue) {
		for (Flag f : flags) {
			if (f.name.equals(flagName)) {
				f.setValue(newValue);
			}
		}
	}
}
