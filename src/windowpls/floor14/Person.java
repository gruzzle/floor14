package windowpls.floor14;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person implements Unlockable {
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
	
	private List<Action> actionOverrides; // or maybe have a map of action name strings to Actions, with custom actions overwritten
	
	private static final String[] firstNames = {"Jana", "Boggs", "Stripes", "Jewf", "Bindu", "Les", "Scott", "Mrs. T.R."};
    private static final String[] lastNames = {"Dobson", "Suggins", "Bean", "Rams"};
    
    public Person() {
    	actionOverrides = new ArrayList<Action>();
    	name = String.format("%s %s", firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);
    }
	
	public ActionResult doAction(String actionType) {
		// search overrides for actions of the specified type
		List<Action> actions = new ArrayList<Action>();
		for (Action overrideAction : actionOverrides) {
			if (overrideAction.getType().equals(actionType)) {
				actions.add(overrideAction);
			}
		}
		
		// if there are override actions, do them
		if (actions.size() > 0) {
			
		}		
		// otherwise, do the default action of this type
		else {
			switch(actionType) {
			case "Surveillance":
				break;
			case "Pursuit":
				break;
			case "Interrogate":
				break;
			case "Removal":
				break;
			case "Smear":
				break;
			}
		}
		
		return null;
	}
	
	public class Action {
		private String type;
		private String text;
		private int chance;
		private List<ActionResult> results; 
		
		public String getType() { return type; }
		public String getText() { return text; }
		public int getChance() { return chance; }
		public List<ActionResult> getResults() { return results; }
		
		public Action(String type, String text, int chance, List<ActionResult> results) {
			this.type = type;
			this.text = text;
			this.chance = chance;
			this.results = results;
		}
	}
	
	public class ActionResult {
		public String unlockType; // person, location, flag etc.
		public int id; // index of unlocked thing 
		public boolean newValue; // if a flag is being changed
		
		public ActionResult (String unlockType, int id) {
			this.unlockType = unlockType;
			this.id = id;
		}
		
		public ActionResult (String unlockType, int id, boolean newValue) {
			this.unlockType = unlockType;
			this.id = id;
			this.newValue = newValue;
		}
	}
}
