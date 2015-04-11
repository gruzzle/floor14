package windowpls.floor14;

public class Group implements Unlockable {
	private String name;
	private boolean isActive;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public boolean isActive() { return isActive; }
	public void setActive(boolean active) { isActive = active; }
	
	public Group() {
		isActive = false;
	}
	
	public Group(String name) {
		this.name = name;
		isActive = false;
	}
}
