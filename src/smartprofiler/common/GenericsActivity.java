package smartprofiler.common;

import java.util.List;

import smartprofiler.MVP;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesManager;
import smartprofiler.presenter.ProfilesPresenter;
import android.util.Log;
import android.content.Context;

public class GenericsActivity extends LifecycleLoggingActivity implements ContextView{
	
	
	/**
     * Used to retain the Data associated with the ProfilesActivity between runtime
     * configuration changes.
     */
    private final RetainedFragmentManager mRetainedFragmentManager 
        = new RetainedFragmentManager(this.getFragmentManager(),
                                      TAG);
    
    public static final String DATA_KEY = "presenter";
    
   
  
    private ProfilesPresenter mPresenter;
    
    private MVP.ViewOps mView;
    
    @SuppressWarnings("unchecked")
	public void onCreate( MVP.ViewOps view, List<ProfileData> data) {
	// If this method returns true it's the first time the
	// Activity has been created.  	
if (mRetainedFragmentManager.firstTimeIn()) {
   Log.d(TAG,
         "First time calling onCreate()");
       // Initialize the GenericActivity fields.
   		initialize(view);
  
} else {
   Log.d(TAG,
         "Second (or subsequent) time calling onCreate()");
		   // The RetainedFragmentManager was previously
		   // initialized, which means that a runtime
		   // configuration change occurred.
   			reinitalize(view);
  
}
}
    /**
     * Initialize the GenericActivity.
     */
    private void initialize(MVP.ViewOps view){

   		mPresenter = new ProfilesPresenter(view);
   	//	mPresenter.initPresenter();
   		
   		if(view.getProfiles().size() > 0)
   			Log.d(TAG, "1st profile name in view " + view.getProfiles().get(0).getProfileName());
   		Log.d(TAG, "mView.getProfiles() size is " + String.valueOf(view.getProfiles().size()));   
   	//	mPresenter.setPresenterData(view.getProfiles());
   		
   		if(mPresenter.getPresenterData().size() > 0)
   			Log.d(TAG, "To be stored data " + ((ProfileData) mPresenter.getPresenterData().get(0)).getProfileName());
   		mRetainedFragmentManager.put(DATA_KEY, mPresenter);
   		mPresenter.onConfigurationChanged(view);
    }
    
    /**
     * Re-initialize the GenericActivity.
     */
    private void reinitalize(MVP.ViewOps view){
    	     mPresenter = mRetainedFragmentManager.get(DATA_KEY);
    	     if(mPresenter.getPresenterData().size() >  0)
    	    	 Log.d(TAG, "Retrived data " + ((ProfileData) mPresenter.getPresenterData().get(0)).getProfileName());
    	     view.setProfiles(mPresenter.getPresenterData());
    	     mPresenter.onConfigurationChanged(view);
    	
    }
    
    @Override
   	protected void onDestroy() {
   		// TODO Auto-generated method stub
    
    	mRetainedFragmentManager.put(DATA_KEY, mPresenter);
   		super.onDestroy();
   	}
    /**
     * Return the initialized ProvidedPresenterOps instance for use by
     * application logic in the View layer.
     */
    @SuppressWarnings("unchecked")
    public ProfilesPresenter getPresenter() {
        return  mPresenter;
    }
    /**
     * Get the Activity Context.
     */
	@Override
	public Context getActivityContext() {
		// TODO Auto-generated method stub
		return this;
	}
	/**
	 * Get the Application Context.
	 */
	@Override
	public Context getApplicationContext() {
		// TODO Auto-generated method stub
		return super.getApplicationContext();
	}
}
