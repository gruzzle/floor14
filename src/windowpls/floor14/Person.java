package windowpls.floor14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import android.util.Log;

public class Person implements Actionable {
	private static final String TAG = "Person";
	public static final Random random = new Random();
	
	private String name;
	private String title;
	private boolean isActive;

	public String getName() { return name; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public boolean isActive() { return isActive; }
	public void setActive(boolean active) { isActive = active; }
	public void addActionOverride(Action override) { 
		actionOverrides.add(override); 

		Log.d(TAG, String.format("Person %s:", name));    	
		for (Action a : actionOverrides) {
			for (Action.ActionUnlocks unlock : a.getResults()) {
				Log.d(TAG, String.format("\t%s unlocks a %s.", a.getType(), unlock.unlockType));
			}			       	
		}
		Log.d(TAG, " "); 
	} // TODO check for duplicates
	public List<Action> getActionOverrides() { return actionOverrides; } // temp 

	private List<Action> actionOverrides; // could instead have a map of action name strings to Actions, with custom actions overwritten

	private static final String[] firstNames = {"Jana", "Boggs", "Stripes", "Jewf", "Bindu", "Les", "Scott", "Mrs. T.R."};
    private static final String[] lastNames = {"Dobson", "Suggins", "Bean", "Rams"};
    
    public Person() {
    	actionOverrides = new ArrayList<Action>();
    	name = String.format("%s %s", firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);    	
    }
	
	public List<Action> getAction(String actionType) {
		// search overrides for actions of the specified type
		List<Action> actions = new ArrayList<Action>();
		for (Action overrideAction : actionOverrides) {
			if (overrideAction.getType().equals(actionType)) {
				actions.add(overrideAction);
			}
		}
		
		// if no override actions were found, use default action
		if (actions.size() == 0) {
			actions.add(Action.getDefaultAction(actionType));
		}		
	
		return actions;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", name, title);
	}
}
