package windowpls.floor14;

public class Flag {
	public String name;
	public boolean value;
	
	public void setValue(boolean newValue) { value = newValue; }
	
	public Flag(String name, boolean value) {
		this.name = name;
		this.value = value;
	}
}
