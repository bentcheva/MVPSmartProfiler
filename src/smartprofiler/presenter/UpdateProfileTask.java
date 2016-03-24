package smartprofiler.presenter;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

public class UpdateProfileTask extends AsyncTask<Void, Void, Void>{

	

	private ProfilesManager mManager;
	private List<ProfileData> mData;
	
	
	 /**
     * Used for Android debugging.
     */
    private final static String TAG = 
        UpdateProfileTask.class.getName();
	
	
	public UpdateProfileTask(ProfilesManager manager, List<ProfileData> data){
		
		mManager = manager;
		mData = data;
	}


	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "do in background");
		mManager.open();
		for(int i = 0; i < mData.size(); i++){
			Log.d(TAG, mData.get(i).getProfileName());
			Log.d(TAG, " " + mData.get(i).getProfileStatus());
			mManager.updateProfile(mData.get(i));
		}
		mManager.close();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		//super.onPostExecute(result);
		Log.d(TAG, "onPostExecute()");
	}


}
