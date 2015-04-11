package windowpls.floor14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Person implements Actionable {
	public static final Random random = new Random();
	
	private String name;
	private String title;
	private boolean isActive;
	
	public String getName() { return name; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public boolean isActive() { return isActive; }
	public void setActive(boolean active) { isActive = active; }
	public void addActionOverride(Action override) { actionOverrides.add(override); } // TODO check for duplicates
	
	private Map<String, Action> defaultActions; // maybe should be static	
	private List<Action> actionOverrides; // could instead have a map of action name strings to Actions, with custom actions overwritten
	
	private static final String[] firstNames = {"Jana", "Boggs", "Stripes", "Jewf", "Bindu", "Les", "Scott", "Mrs. T.R."};
    private static final String[] lastNames = {"Dobson", "Suggins", "Bean", "Rams"};
    
    public Person() {
    	defaultActions = new HashMap<String, Action>();    	
    	defaultActions.put("Surveillance", new Action("Surveillance", "No luck", 100, null));
    	defaultActions.put("Pursuit", new Action("Pursuit", "No luck", 100, null));
    	defaultActions.put("Interrogate", new Action("Interrogate", "No luck", 100, null));
    	defaultActions.put("Removal", new Action("Removal", "No luck", 100, null));
    	defaultActions.put("Smear", new Action("Smear", "No luck", 100, null));
    	
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
			actions.add(defaultActions.get(actionType));
		}		
	
		return actions;
	}
	
	public Action getDefaultAction(String actionType) { 
		return defaultActions.get(actionType); 
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", name, title);
	}
}
