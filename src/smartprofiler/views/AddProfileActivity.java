package smartprofiler.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.smartprofiler.R;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import smartprofiler.MVP;
import smartprofiler.common.GenericActivity;
import smartprofiler.common.LifecycleLoggingActivity;
import smartprofiler.common.PropertyItem;
import smartprofiler.presenter.AddNewProfilePresenter;
import smartprofiler.presenter.CreateDefaultProfiles;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesManager;

public class AddProfileActivity extends GenericActivity<AddNewProfilePresenter> implements MVP.ViewOps{
	
//	private AddDataAdapter mNewProfileAdapter;
	private AddDataAdapterRadio mAddDataAdapterRadio;
	private ListView mPropertiesList;
	private boolean isStartTimeSet;
	private boolean isStopTimeSet;
	
	int[] colors = {0xE0f2f1, 0xb2dfdb, 0x80cbc4, 0x4db6ac, 0x26a69a, 0x009688}; // red for the example
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        AddProfileActivity.class.getName();
    
	public static final String PARCABLE_CODE = "profile";
	private Calendar mCalendarStartTime = Calendar.getInstance();
	private Calendar mCalendarStopTime = Calendar.getInstance();
	

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_profile);
		
		mPropertiesList = (ListView) findViewById(R.id.list2);
	
		//super.onCreate(this, AddNewProfilePresenter.class);
	
	//	mNewProfileAdapter = new AddDataAdapter(this);
		mAddDataAdapterRadio = new AddDataAdapterRadio(this);
		//mPropertiesList.setAdapter(mNewProfileAdapter);
		mPropertiesList.setAdapter(mAddDataAdapterRadio);
		
		super.onCreate(this, AddNewProfilePresenter.class);
		
	//	mNewProfileAdapter.setAdapterPresenter(getPresenter());
		mAddDataAdapterRadio.setAdapterPresenter(getPresenter(), Utils.Utils.getIntentData(getIntent(), ProfilesActivity.EDITED_PROFILE));
		
	
		
		// call back listener, called when the start and stop items from the ListView are clicked
		mPropertiesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "OnItemClick");
				switch(position){
				case 4:	Log.d(TAG, "StartTime picking");
					
					new TimePickerDialog(AddProfileActivity.this, timeStartListener, 
							mCalendarStartTime.get(Calendar.HOUR_OF_DAY), 
							mCalendarStartTime.get(Calendar.MINUTE), true).show();
							
					
					break;
				case 5: Log.d(TAG, "Stop time picking");
				new TimePickerDialog(AddProfileActivity.this, timeStopListener, 
						mCalendarStopTime.get(Calendar.HOUR_OF_DAY), 
						mCalendarStopTime.get(Calendar.MINUTE), true).show();
						
					break;
				}
				
			}
		});
	}
	// listener for StartTime TimePicker Dialog
	TimePickerDialog.OnTimeSetListener timeStartListener = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			mCalendarStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mCalendarStartTime.set(Calendar.MINUTE, minute);
			isStartTimeSet = true;
			
		}
	};
	// listener for StopTime TimePicker Dialog
	TimePickerDialog.OnTimeSetListener timeStopListener = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			mCalendarStopTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mCalendarStopTime.set(Calendar.MINUTE, minute);
			isStopTimeSet = true;
		}
	};
	
	private ArrayList<PropertyItem> makePropertyData(String[] names){
		ArrayList<PropertyItem> propertiesList = new ArrayList<PropertyItem>();
		for(String str: names){
			propertiesList.add(new PropertyItem(str, 0));
		}
		return propertiesList;
	}

	/**
	 * Called when Cancel button is pressed
	 * Kills the AddProfileActivity and returns back to the  main activity  ProfilesActivity
	 * @param view
	 */
	public void cancelPressed(View view){
		//preparing the object mProperiesList for GC
		mPropertiesList = null;
		finish();
	}
	/**
	 * Called when OK button is pressed
	 * @param view
	 */
	public void okPressed(View view){
		
		ArrayList<PropertyItem> propertyList = new ArrayList<PropertyItem>();
		String newProfileName = getIntent().getExtras().getString(ProfilesActivity.NAME);
		
		for(int i =0; i < mPropertiesList.getAdapter().getCount(); i++ ){
			propertyList.add((PropertyItem) mPropertiesList.getAdapter().getItem(i));
			Log.d(TAG, propertyList.get(i).getName());
			Log.d(TAG, String.valueOf(propertyList.get(i).getStatus()));
		}
		Log.d(TAG, "name of new profile " +  newProfileName);
		ProfileData newProfile = new ProfileData(newProfileName,
				0,
				propertyList.get(0).getStatus(),
				propertyList.get(1).getStatus(),
				propertyList.get(2).getStatus(),
				propertyList.get(3).getStatus(),
				CreateDefaultProfiles.DEFAULT_START, 
				CreateDefaultProfiles.DEFAULT_STOP, null);
		if(isStartTimeSet && isStopTimeSet)
			newProfile = new ProfileData(newProfileName,
				0,
				propertyList.get(0).getStatus(),
				propertyList.get(1).getStatus(),
				propertyList.get(2).getStatus(),
				propertyList.get(3).getStatus(),
				Utils.Utils.timeInMinutes(mCalendarStartTime.get(Calendar.HOUR_OF_DAY), mCalendarStartTime.get(Calendar.MINUTE)),
				Utils.Utils.timeInMinutes(mCalendarStopTime.get(Calendar.HOUR_OF_DAY), mCalendarStopTime.get(Calendar.MINUTE)),
				null);
	
	
		Intent resultIntent = new Intent();
		Bundle mBundle = new Bundle(); 
		mBundle.putParcelable(PARCABLE_CODE, newProfile); 
		
		resultIntent.putExtras(mBundle);
		
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}

	

	@Override
	public void setProfiles(List<ProfileData> profiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProfileData> getProfiles() {
		// TODO Auto-generated method stub
		return null;
	}

	

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
            @SuppressWarnings("unchecked")
			List<PropertyItem> properties = (List<PropertyItem>) data;
            Log.d(TAG, "first property is " + properties.get(0).getStatus());
            Log.d(TAG, "second property is " + properties.get(2).getStatus());
            // Add the results to the Adapter and notify changes.
           // mNewProfileAdapter.clear();
            mAddDataAdapterRadio.clear();
         //   mNewProfileAdapter.addAll(properties);
            mAddDataAdapterRadio.addAll(properties);
            mAddDataAdapterRadio.notifyDataSetChanged();
          //  mNewProfileAdapter.notifyDataSetChanged();
        }
	}

	

}
