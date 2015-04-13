package windowpls.floor14;

import java.util.ArrayList;
import java.util.List;

public class Event {
	private String type;				// eg. article	
	private String text;				// body text
	private int time; 					// time after which event will occur
	private int damage;					// damage caused by event triggering
	private List<Condition> conditions;	// flag conditions that must be met
	
	public String getText() { return text; }
	public String getType() { return type; }
	public int getDamage() { return damage; }
	
	public Event(String type, String text, int damage, int time, List<Condition> conditions) {
		this.type = type;		
		this.text = text;
		this.damage = damage;
		this.time = time;
		this.conditions = conditions;
	}
	
	public boolean triggered(int timeElapsed, Flag[] flags) {
		for (Condition c : conditions) {
			for (Flag f : flags) {
				if (f.name.equals(c.flagName) && f.value != c.requiredValue) {
					return false;
				}
			}
		}		
		return timeElapsed >= time ? true : false;
	}
	
	public class Condition {
		public String flagName;
		public boolean requiredValue;
		
		public Condition(String flagName, boolean requiredValue) {
			this.flagName = flagName;
			this.requiredValue = requiredValue;
		}
	}
}