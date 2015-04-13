package windowpls.floor14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {	
	private static final String TAG = "MainActivity";
	
	private static final String[] actionTypes = { "Surveillance", "Pursuit", "Interrogate", "Removal", "Smear" };    
	private DatabaseHelper db;
	private List<Mission> missions;
	private Mission selectedMission;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseHelper.forceDatabaseReload(this);
		Mission.db = new DatabaseHelper(this);
		
		missions = new ArrayList<Mission>();
		missions.add(new Mission(1));
		selectedMission = missions.get(0);
		
		updateUI();	}
	
	private void updateUI() {
		Spinner personSpinner = (Spinner) findViewById(R.id.spinner_person);
		personSpinner.setAdapter(new ArrayAdapter<Actionable>(this, android.R.layout.simple_spinner_dropdown_item, selectedMission.getActive()));
		
		// don't really need to update this one after first time
		Spinner actionSpinner = (Spinner) findViewById(R.id.spinner_action);
		actionSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, actionTypes));
		
		String flagText = String.format("Turns elapsed: %s\nDamage taken: %s\n\nFlags:\n", selectedMission.getTime(), selectedMission.getDamage());
		for (Flag f : selectedMission.getFlags()) {
			flagText += String.format("%s: %s\n", f.name, f.value);
		}
		TextView flagTextView = (TextView) findViewById(R.id.text_flagbox);
		flagTextView.setText(flagText);
	}
	
	public void onClickAction(View view) {
		selectedMission.incTime();
		
		Spinner personSpinner = (Spinner) findViewById(R.id.spinner_person);		
		Actionable target = (Actionable) personSpinner.getSelectedItem();

		Spinner actionSpinner = (Spinner) findViewById(R.id.spinner_action);		
		String actionType = (String) actionSpinner.getSelectedItem();
		
		writeToConsole(String.format("Attempting to %s %s...", actionType.toLowerCase(Locale.US), target.getName()));
		
		// process only first action
		if (target.getAction(actionType).get(0) != null) {
			processAction(target, target.getAction(actionType).get(0));
		}
	}
	
	private void writeToConsole(String string) {
		TextView console = (TextView) findViewById(R.id.text_console);
		console.append(string + "\n");
	}
	
	private void showAlertDialog(String title, String text) {
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(text)
	    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        }
	     })
	    .show();		
	}
	
	private void processAction(Actionable target, Action action) {		
		String unlockText = "";
		
		List<Action.ActionUnlocks> unlocks = action.getResults();
		for (Action.ActionUnlocks unlock : unlocks) {
			writeToConsole("unlockType: " + unlock.unlockType);	
			switch(unlock.unlockType) {
			case "Person":
				Person newPerson = selectedMission.getPeople()[unlock.id];
				newPerson.setActive(true);
				unlockText = String.format("Unlocked %s the %s.", newPerson.getName(), newPerson.getTitle());
				writeToConsole(unlockText);				
				break;
			case "Location":
				Location newLocation = selectedMission.getLocations()[unlock.id];
				newLocation.setActive(true);
				unlockText = String.format("Unlocked %s.", newLocation.getName());
				writeToConsole(unlockText);				
				break;
			case "Group":
				Group newGroup = selectedMission.getGroups()[unlock.id];
				newGroup.setActive(true);
				unlockText = String.format("Unlocked %s.", newGroup.getName());
				writeToConsole(unlockText);				
				break;
			case "Flag":
				Flag updatedFlag = selectedMission.getFlags()[unlock.id];
				selectedMission.setFlag(updatedFlag.name, unlock.newValue);
				unlockText = String.format("Set flag %s to %s.", updatedFlag.name, unlock.newValue);
				writeToConsole(unlockText);				
				break;
			}
		}

		String alertText;
		if (target instanceof Person) {
			alertText = String.format("You %s %s the %s.\n", action.getType().toLowerCase(Locale.US), target.getName(), ((Person)target).getTitle());			
		}
		else {
			alertText = String.format("You %s the %s.\n q", action.getType().toLowerCase(Locale.US), target.getName());			
		}
		alertText = String.format("%s\n%s\n\n%s", alertText, action.getText(), unlockText);
		
		updateUI();		
		showAlertDialog(String.format("%s on %s", action.getType(), target.getName()), alertText);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
