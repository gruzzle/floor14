package windowpls.floor14;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Action {
	private static final Random random = new Random();
	
	private String type;
	private String text;
	private int chance;
	private List<ActionUnlocks> results;

	public String getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public int getChance() {
		return chance;
	}

	public List<ActionUnlocks> getResults() {
		return results;
	}
	
	public void setResults(List<ActionUnlocks> results) {
		this.results = results;
	}

	public Action(String type, String text, int chance,
			List<ActionUnlocks> results) {
		this.type = type;
		this.text = text;
		this.chance = chance;
		this.results = results;
	}
	
	public static Action getDefaultAction(String actionType) {
		return new Action(actionType, getDefaultActionText(actionType), 100, new ArrayList<ActionUnlocks>());
	}
	
	private static String getDefaultActionText(String actionType) {
		switch(actionType) {
		case "Surveillance":
			String[] surveillanceText = new String[] { "The target didn't do anything interesting." };
			return surveillanceText[random.nextInt(surveillanceText.length)];
		case "Pursuit":
			String[] pursuitText = new String[] { "The target shook off our agents." };
			return pursuitText[random.nextInt(pursuitText.length)];			
		case "Interrogate":
			String[] interrogateText = new String[] { "We were unable to abduct the target." };
			return interrogateText[random.nextInt(interrogateText.length)];			
		case "Removal":
			String[] removalText = new String[] { "The target has been taken care of." };
			return removalText[random.nextInt(removalText.length)];			
		case "Smear":
			String[] smearText = new String[] { "We were unable to smear the target." };
			return smearText[random.nextInt(smearText.length)];
		default:
			return "Unknown action type";
		}
	}
	
	public class ActionUnlocks {
		public String unlockType; // person, location, flag etc.
		public int id; // index of unlocked thing 
		public boolean newValue; // if a flag is being changed
		
		public ActionUnlocks (String unlockType, int id) {
			this.unlockType = unlockType;
			this.id = id;
		}
		
		public ActionUnlocks (String unlockType, int id, boolean newValue) {
			this.unlockType = unlockType;
			this.id = id;
			this.newValue = newValue;
		}
	}
}
