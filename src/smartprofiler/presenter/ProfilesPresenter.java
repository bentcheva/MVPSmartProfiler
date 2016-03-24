package smartprofiler.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import smartprofiler.MVP;
import smartprofiler.MVP.ModelAlarmOps;
import smartprofiler.MVP.ModelOps;
import smartprofiler.MVP.ViewOps;
import smartprofiler.common.GenericPresenter;
import smartprofiler.model.ProfilesModel;
import smartprofiler.views.AddProfileActivity;
import smartprofiler.views.ProfilesActivity;

public class ProfilesPresenter extends GenericPresenter implements  MVP.GenericPresenterOps,MVP.PresenterOps {
	
	//private MVP.ViewOps mView;
	private ProfilesManager mManager;
	private List<ProfileData> mDataList;
	private ProfilesModel mModel;
	
	 /**
     * Debugging tag used by the Android logger.
     */
    protected final String TAG = 
        getClass().getSimpleName();
    
    /**
	 * Zero argument constructor needed for newInstance()
	 */
	public ProfilesPresenter(){
		super();
	}
	
	@Override
	public void onCreate(ViewOps view) {
		// TODO Auto-generated method stub
		 
		 super.onCreate(view);
		 
		// mManager = new ProfilesManager(mView.getApplicationContext());
		 mManager = new ProfilesManager(getPresenterView().getApplicationContext());
		 mDataList = new ArrayList<ProfileData>();
		 mModel = new ProfilesModel();
		 initPresenter();
	}
	/**
	 * Constructor takes as argument class implementing MVP.ViewOps interface
	 * @param view
	 */
	@SuppressWarnings("unchecked")
	public ProfilesPresenter(MVP.ViewOps view){
		
		 Log.d(TAG, "new ProfilesPresenter"); 
		// mView = view;
		 mManager = new ProfilesManager(getPresenterView().getApplicationContext());
		 mDataList = new ArrayList<ProfileData>();
		 mModel = new ProfilesModel();
		 initPresenter();
		 super.onCreate(view);
		
		
	}
	
	/**
	 * Returns the Model layer associated to this Presenter
	 * @return
	 */
	public MVP.ModelAlarmOps getModel(){
		return (ModelAlarmOps) mModel;
	}
	


	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method checks if the db table doesn't exists,which is equivalent to first time running the app.
	 *  If the table doesn't exist the method creates new table and loads 3 default profiles
	 *  If the table exists, uses the records of the table to display the profiles in the ListView
	 */
	//@Override
	private void initPresenter() {
		if(isFirstRunEver())
			new CreateDefaultProfiles(mManager).execute();
		mModel.setPresenter(this);
		new ProfilesLoader(mManager, getPresenterView(), this).execute();  
	}

	@Override
	public void deleteProfile(int position) {
		// TODO Auto-generated method stub
		new DeleteProfileTask(mManager, (ProfilesActivity) getPresenterView(), this)
							.execute( mModel.getModelData().get(position));
	}

	@Override
	public void addProfile(Intent data) {
		// TODO Auto-generated method stub
		 new AddNewProfile(mManager, Utils.Utils.getIntentData(data, AddProfileActivity.PARCABLE_CODE), getPresenterView(), this ).execute();
		
	}
	
	
	
	public List<ProfileData> getPresenterData(){
		return this.mDataList;
	}

	@Override
	public boolean isFirstRunEver() {
		// TODO Auto-generated method stub
		mManager = new ProfilesManager(getPresenterView().getApplicationContext());
		boolean tableExists = mManager.doesTableExist();
		boolean tableEmpty = mManager.isTableEmpty();
		Log.d(TAG, String.valueOf(tableExists));
		if(tableEmpty){
			Log.d(TAG, "dB table is empty");
			return true;
		}
		
		Log.d(TAG, "dB table is populated");
		return false;
		
	}



	@Override
	public void onConfigurationChanged(ViewOps view) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onConfigurationChanged");
		Log.d(TAG, "mModel data  size is " + String.valueOf(getModel().getModelData()));
		view.displayProfiles(getModel().getModelData());
		
	}
	
	
	@Override
	public void onDestroy(boolean isConfigurationChanged) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroy()");
		if(isConfigurationChanged){
			super.onDestroy(isConfigurationChanged);
			Log.d(TAG, "Orientatioin changed");
		}
		else{
			// TO DO save the current data profiles into the dBase table
			Log.d(TAG, "Killing the app");
			
		}
	}
	
	public void updateDataBaseTable(){
		if(mModel.getModelData() != null)
			new UpdateProfileTask(mManager, mModel.getModelData()).execute();
	}

/*	@SuppressWarnings("unchecked")
	@Override
	public <T> void setPresenterData(List<T> data) {
		// TODO Auto-generated method stub
		mDataList = (List<ProfileData>) data;
		
	}
*/
	@Override
	public ModelOps getPresenterModel() {
		// TODO Auto-generated method stub
		return mModel;
	}

}
