package smartprofiler.presenter;

import android.util.Log;
import smartprofiler.MVP;
import smartprofiler.MVP.ViewOps;
import smartprofiler.common.GenericPresenter;
import smartprofiler.model.ProfileModel;

public class AddNewProfilePresenter extends GenericPresenter implements MVP.GenericPresenterOps{

	private ProfileModel mModel;
	
	
	 /**
     * Debugging tag used by the Android logger.
     */
    protected final String TAG = 
        getClass().getSimpleName();
    
	
	@Override
	public void onCreate(ViewOps view) {
		// TODO Auto-generated method stub
		super.onCreate(view);
		
		mModel = new ProfileModel();
		mModel.setPresenter(this);
	}
	public  ProfileModel getModel(){
		return mModel;
	}
	
	@Override
	public ViewOps getPresenterView() {
		// TODO Auto-generated method stub
		return super.getPresenterView();
	}

	@Override
	public void onConfigurationChanged(ViewOps view) {
		// TODO Auto-generated method stub
		//super.onConfigurationChanged(view);
		Log.d(TAG, "onConfigurationChanged()");
		Log.d(TAG, "first status" + mModel.getModelData().get(0).getStatus());
		Log.d(TAG, "second status" + mModel.getModelData().get(1).getStatus());
		Log.d(TAG, "third status" + mModel.getModelData().get(2).getStatus());
		view.displayProfiles(mModel.getModelData());
		
	}

	@Override
	public void onDestroy(boolean isConfigurationChanged) {
		// TODO Auto-generated method stub
		super.onDestroy(isConfigurationChanged);
	}

	

}
