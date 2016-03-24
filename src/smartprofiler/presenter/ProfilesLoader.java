package smartprofiler.presenter;

import java.util.ArrayList;
import java.util.List;

import smartprofiler.MVP;
import smartprofiler.MVP.ViewOps;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class ProfilesLoader extends AsyncTask<Void, Void, List<ProfileData>> {

	

	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
    		ProfilesLoader.class.getName();

	private ProfilesManager mProfilesManager;
	private List<ProfileData> mProfilesList;
	private MVP.PresenterOps mPresType;
	private MVP.ViewOps mView;
  
	
	
	public ProfilesLoader(ProfilesManager manager, MVP.ViewOps view, MVP.PresenterOps presOps){
		mProfilesManager = manager;
		mView = view;
		mPresType = presOps;
		
		
	}
	
	@Override
	protected List<ProfileData> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		mProfilesList = new ArrayList<ProfileData>();
		mProfilesManager.open();
		mProfilesList = mProfilesManager.getAll();
		return mProfilesList = mProfilesManager.getAll();
	}
	
	@Override
	protected void onPostExecute(List<ProfileData> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d(TAG, "onPostExecute");
		Activity activity = (Activity) mView.getActivityContext();
	//	((ProfilesActivity) activity).setProfiles(mProfilesList);
		mPresType.getPresenterModel().setModelData(result);
		if (activity != null) {
		   // displayes the profiles in the UI thread
			((ViewOps) mView.getActivityContext()).displayProfiles(result);
			//((ProfilesActivity) mView.getActivityContext()).setProfiles(result);
			
			
			if(mPresType != null)
				//mPresType.setPresenterData(result);
				mPresType.getPresenterModel().setModelData(result);
		}
		Log.d(TAG, "Loader");
		for(int i = 0; i < result.size(); i++)
			Log.d(TAG, result.get(i).getProfileName());
		
		Log.d(TAG, "Data from mModel");
		for(int i = 0; i < result.size(); i++)
			Log.d(TAG, ((ProfileData)mPresType.getPresenterModel().getModelData().get(i)).getProfileName());
		if(mPresType != null)
			Log.d(TAG, "mPresType size is " + String.valueOf(mPresType.getPresenterModel().getModelData().size()));
	}
	}
	
	

