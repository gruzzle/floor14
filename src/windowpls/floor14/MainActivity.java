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
	private Mission mission;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseHelper.forceDatabaseReload(this);
		db = new DatabaseHelper(this);

		Mission.db = db;		
		mission = new Mission(1);
		
		updateSpinners();
		
		//Person initialPerson = mission.getPeople().get(0);		
						
		//TextView personTextView = (TextView) findViewById(R.id.person_text);
		//personTextView.setText(initialPerson.getName());		
	}
	
	private void updateSpinners() {
		Spinner personSpinner = (Spinner)findViewById(R.id.spinner_person);
		personSpinner.setAdapter(new ArrayAdapter<Actionable>(this, android.R.layout.simple_spinner_dropdown_item, mission.getActive()));
		
		// don't really need to update this one
		Spinner actionSpinner = (Spinner)findViewById(R.id.spinner_action);
		actionSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, actionTypes));				
	}
	
	public void onClickAction(View view) {
		Spinner personSpinner = (Spinner) findViewById(R.id.spinner_person);		
		Actionable target = (Actionable) personSpinner.getSelectedItem();

		Spinner actionSpinner = (Spinner) findViewById(R.id.spinner_action);		
		String actionType = (String) actionSpinner.getSelectedItem();
		
		writeToConsole(String.format("Attempting to %s %s...\n", actionType.toLowerCase(Locale.US), target.getName()));
		
		// assuming person
		Person person = (Person) target;
		// process only first action
		processAction(person, person.getAction(actionType).get(0));
	}
	
	private void writeToConsole(String string) {
		TextView console = (TextView) findViewById(R.id.text_console);
		console.append(string);
		
	}
	
	private void processAction(Person person, Action action) {
		new AlertDialog.Builder(this)
	    .setTitle(String.format("%s on %s", action.getType(), person.getName()))
	    .setMessage(String.format("You %s %s the %s.\n%s", action.getType().toLowerCase(Locale.US), person.getName(), person.getTitle(), action.getText()))		
	    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        }
	     })
	    .show();
		
		List<Action.ActionUnlocks> unlocks = action.getResults();
		for (Action.ActionUnlocks unlock : unlocks) {
			switch(unlock.unlockType) {
			case "Person":
				Person newPerson = mission.getPeople()[unlock.id];
				newPerson.setActive(true);
				writeToConsole(String.format("Unlocked %s.", newPerson.getName()));
				break;
			case "Location":
				Location newLocation = mission.getLocations()[unlock.id];
				newLocation.setActive(true);
				writeToConsole(String.format("Unlocked %s.", newLocation.getName()));
				break;
			case "Group":
				Group newGroup = mission.getGroups()[unlock.id];
				newGroup.setActive(true);
				writeToConsole(String.format("Unlocked %s.", newGroup.getName()));
				break;
			case "Flag":
				// TODO
				break;
			}
		}
		
		updateSpinners();
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
