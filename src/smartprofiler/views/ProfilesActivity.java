package smartprofiler.views;

import java.util.ArrayList;
import java.util.List;

import com.example.smartprofiler.R;

import smartprofiler.MVP;
import smartprofiler.common.GenericActivity;
import smartprofiler.common.LifecycleLoggingActivity;
import smartprofiler.model.AlarmReceiver;
import smartprofiler.model.ProfileAlarmManager;
import smartprofiler.presenter.AddNewProfile;
import smartprofiler.presenter.CreateDefaultProfiles;
import smartprofiler.presenter.DeleteProfileTask;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesLoader;
import smartprofiler.presenter.ProfilesManager;
import smartprofiler.presenter.ProfilesPresenter;
import smartprofiler.presenter.UpdateProfileTask;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ProfilesActivity provides the main User Interface for the app
 * When first started it loads default profiles.
 * There are options to delete/ edit/add profiles
 * @author Bobi
 *
 */
public class ProfilesActivity extends GenericActivity<ProfilesPresenter> implements MVP.ViewOps {

	

	

	protected ListView mProfilesList;
	protected ProfilesAdapter mMyAdapter;
	private ProfilesManager mManager;
	private String mNewName;
	private List<ProfileData> mProfiles;
	private ProfileAlarmManager mAlarmManager;
	
	
	
	static final String ACTION_ADD_PROFILE = 
			"com.example.smartprofiler.action.ACTION_ADD_PROFILE";
	
	static final int NEW_PROFILE_RESULT = 1;  // The request code for Adding new profile
	static final int EDIT_PROFILE_RESULT = 2;  // The request code for Editing existing profile
	static final int ADD_MODE = 10; // int constant for distinguishing the mode in which the Activity is going to be used
	static final int EDIT_MODE = 20;
	
	static final String MODE = "mode";
	static final String NAME = "name";
	static final String CANCEL = "Cancel";
	static final String EDITED_PROFILE = "edited_profile";
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        ProfilesPresenter.class.getName();
    
   
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profiles);
		
		//setting the Action bar
		Log.d("ProfilesActivity", "onCreate()");
		Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
	    setSupportActionBar(myToolbar);
	    //setting the ListView
		mProfilesList = (ListView) findViewById(R.id.list);
		mMyAdapter = new ProfilesAdapter(this);
		mProfilesList.setAdapter(mMyAdapter);
		
		
		//setProfiles(new ArrayList<ProfileData>());
		//calls onCreate of the super class
		//if(mProfiles.size() > 0)
		//	Log.d(TAG, "Profiles before super.onCreate" + mProfiles.get(0).getProfileName());
		
		super.onCreate(this, ProfilesPresenter.class);
		
		registerForContextMenu(mProfilesList);
		
		//Deactivates the current profile and activates the selected profile 
		mProfilesList.setOnItemClickListener(new OnItemClickListener( ) {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Log.d(TAG, ((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileName() + " profile selected.");
				Log.d(TAG, ((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileWiFi() + " WiFi.");
				Log.d(TAG, ((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileSound() + " Sound.");
				Log.d(TAG, ((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileVibration() + " Vibration.");
				
				for(int i =0; i < getPresenter().getModel().getModelData().size(); i++)
				{
					if(((ProfileData) getPresenter().getModel().getModelData().get(i)).getProfileStatus() == 1){
						((ProfileData) getPresenter().getModel().getModelData().get(i)).setProfileStatus(0);
						((ProfileData) getPresenter().getModel().getModelData().get(position)).setProfileStatus(1);
						
					}
					
					Log.d(TAG, ((ProfileData) getPresenter().getModel().getModelData().get(i)).getProfileName() + " status is " + 
										String.valueOf(((ProfileData) getPresenter().getModel().getModelData().get(i)).getProfileStatus()));
					Log.d(TAG, "mPresenter data " + ((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileName());
					//getPresenter().setPresenterData(getPresenter().getModel().getModelData());
				}
				((ProfileData) getPresenter().getModel().getModelData().get(position)).setProfileStatus(1);
				if(mAlarmManager != null){
					//stop the Alarms
					mAlarmManager.deactivateAlarm();
					// freeing the old Alarm for  GC 
					mAlarmManager = null;
				}
				if(((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileStartTime() == CreateDefaultProfiles.DEFAULT_START 
						|| ((ProfileData) getPresenter().getModel().getModelData().get(position)).getProfileStopTime() == CreateDefaultProfiles.DEFAULT_STOP){
					// the profile is not time window dependant
					Log.d(TAG, "setting Resources");
					Utils.Utils.setResources((ProfileData) getPresenter().getModel().getModelData().get(position), ProfilesActivity.this);
					// TODO to deactivate any active alarms
				}
				else{
					mAlarmManager = new ProfileAlarmManager(getActivityContext(), makeIntent((ProfileData) getPresenter().getModel().getModelData().get(position), 
																								getActivityContext(), AlarmReceiver.BUNDLE_CODE));
					mAlarmManager.activateAlarm();
					
				}
				mMyAdapter.addAll(getPresenter().getModel().getModelData());
				mMyAdapter.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// Destroys the current presenetr in case of 
		//ConfigurationChange in order to free it fo GC
		getPresenter().onDestroy(isChangingConfigurations());
		super.onDestroy();
	}

	/**
	 * Factory method returning Intent
	 * @param profile ProfileData object bundled as Extras in the Intent
	 * @param context Activity's context
	 * @return Intent object or null
	 */
	private Intent makeIntent(ProfileData profile, Context context, String bundleCode){
		
		if(profile != null){
			Intent intent = new Intent(context, AlarmReceiver.class);
			Bundle mBundle = new Bundle(); 
			mBundle.putParcelable(ProfileAlarmManager.ALARM_DATA, profile); 
			return intent.putExtra(bundleCode, mBundle);
			
		}else
			return null;
	}

	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "Profiles size " + String.valueOf(getPresenter().getModel().getModelData().size()));
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
		getPresenter().updateDataBaseTable();
	}
	

	/**
	 * Loads tha Profiles data into the ListView
	 */
	@Override
	public <T> void displayProfiles(List<T> data) {
		// TODO Auto-generated method stub
		Log.d(TAG, "data = " + data);
        if (data == null 
            || data.size() == 0){
        	Log.d(TAG, "data == null");
        		Utils.Utils.showToast(getApplicationContext(), "error");
        }
        else {
            Log.d(TAG,
                  "displayProfiles() with number of profiles = "
                  + data.size());

            // Add the results to the Adapter and notify changes.
            mMyAdapter.clear();
            mMyAdapter.addAll(data);
            List<String> names = Utils.Utils.namesToString((List<ProfileData>) data);
            Log.d(TAG, "names = " + names);
            Log.d(TAG, "count = " + String.valueOf(mMyAdapter.getCount()));
            mMyAdapter.notifyDataSetChanged();
        }
	}
	
	
	

	//Creates context menu when ListView item is long pressed
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	 
	 //   menu.setHeaderTitle("Choose");
		
		menu.add("View");
		menu.add("Edit");
	    menu.add("Delete");
		
		
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Log.d(TAG, "removing item pos=" + info.position);
        
		if(item.getTitle() == "Edit"){
			Log.d(TAG, "Edit");
				 Intent editProfileIntent = new Intent(getApplicationContext(), AddProfileActivity.class);
				 editProfileIntent.putExtra(MODE, EDIT_MODE);
				 Bundle mBundle = new Bundle(); 
				 mBundle.putParcelable(EDITED_PROFILE, mMyAdapter.getAll().get(info.position)); 
				 editProfileIntent.putExtras(mBundle);
				 startActivityForResult(editProfileIntent, EDIT_PROFILE_RESULT);
				}
		if(item.getTitle() == "Delete"){
				Log.d(TAG, "Delete");
				
				for(int i = 0; i < mMyAdapter.getCount(); i++)
					Log.d(TAG, mMyAdapter.getAll().get(i).getProfileName());
				//new DeleteProfileTask(mManager, this).execute(mMyAdapter.getItem(info.position));
				getPresenter().deleteProfile(info.position);
				mMyAdapter.removeItem(info.position);
			}
		if(item.getTitle() == "View"){
			Log.d(TAG, "View");
			//viewDialog();
			ViewProfileDialog dialogView = new ViewProfileDialog((ProfileData)getPresenter().getModel().getModelData().get(info.position), this);
			dialogView.viewDialog();
			
			
		}
			else 
				return false;
		
		return true;
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stubgetMenuInflater().inflate(R.menu.profiles, menu);
		getMenuInflater().inflate(R.menu.profiles, menu);
		
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(getApplicationContext(), "Settings pressed", Toast.LENGTH_LONG).show();
			return true;
		}
		if (id == R.id.action_add) {
			Intent intent = new Intent(this, AddProfileActivity.class);
			intent.setAction(Intent.ACTION_SEND);
			showDialogue();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * This method prompts the User to enter name of the new profile via 
	 * DialogBox. Pressing "Done" initiates the start of AddProfileActivity
	 */
	private void showDialogue(){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this); 
		final EditText edittext = new EditText(this);
		
		alert.setMessage("Enter Profile Name");
		alert.setTitle("Add new profile");

		alert.setView(edittext);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		       
		       mNewName = edittext.getText().toString();
		       edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
		       Intent addProfileIntent = new Intent(getApplicationContext(), AddProfileActivity.class);
				addProfileIntent.setAction(Intent.ACTION_SEND);
				addProfileIntent.putExtra(NAME, mNewName);
				startActivityForResult(addProfileIntent, NEW_PROFILE_RESULT);
		      
		      
		    }
		});

		alert.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		        // what ever you want to do with No option.
		    	mNewName = null;
		    	dialog.cancel();
		    }
		});
		alert.show();
		
	}
	
	
	/**
	 * Callback method , called when the AddProfileActivity exits. 
	 * Returns the control to the caller activity - ProfilesActivity.
	 * Gets the new DataProfile object from the intent and starts an 
	 * AsyncTask to add the profile to the dB table
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 // Check which request we're responding to
	    if (requestCode == NEW_PROFILE_RESULT) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) 
	           // new AddNewProfile(mManager, Utils.Utils.getIntentData(data, AddProfileActivity.PARCABLE_CODE), this).execute();
	        	getPresenter().addProfile(data);
	    }
	    
	 // Check which request we're responding to
	    if (requestCode == EDIT_PROFILE_RESULT) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) 
	           // new AddNewProfile(mManager, Utils.Utils.getIntentData(data, AddProfileActivity.PARCABLE_CODE), this).execute();
	        	getPresenter().updateDataBaseTable();
	    }

	}

	
	
	/**
	 * Sets the member variable mProfiles.
	 * @param profiles List<ProfileData>
	 */
	@Override
	public void setProfiles(List<ProfileData> profiles) {
		// TODO Auto-generated method stub
		this.mProfiles = profiles;
	}
	
	/**
	 * Returns List with ProfileData objects
	 * @return List<ProfileData>
	 */
	@Override
	public List<ProfileData> getProfiles() {
		// TODO Auto-generated method stub
		return this.mProfiles;
	}

	

	}


	
	
	


