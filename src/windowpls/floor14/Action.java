package windowpls.floor14;

import java.util.List;

public class Action {
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
