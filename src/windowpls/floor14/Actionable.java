package windowpls.floor14;

import java.util.List;

public interface Actionable {
	
	public String getName();
	public boolean isActive();
	public void setActive(boolean active);	
	public String toString();
	public List<Action> getAction(String actionType);
	
}
