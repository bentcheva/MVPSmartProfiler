package smartprofiler.common;

import java.util.List;

import smartprofiler.MVP;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesManager;
import smartprofiler.presenter.ProfilesPresenter;
import android.util.Log;
import android.content.Context;

public class GenericActivity<PresenterType extends MVP.GenericPresenterOps> extends LifecycleLoggingActivity implements ContextView{
	
	
	/**
     * Used to retain the Data associated with the ProfilesActivity between runtime
     * configuration changes.
     */
    private final RetainedFragmentManager mRetainedFragmentManager 
        = new RetainedFragmentManager(this.getFragmentManager(),
                                      TAG);
    
    public static final String DATA_KEY = "presenter";
    
   
  
    private PresenterType mPresenter;
    
    
    @SuppressWarnings("unchecked")
	public void onCreate(MVP.ViewOps view, Class<PresenterType> presenterType) {
	// If this method returns true it's the first time the
	// Activity has been created.  	
if (mRetainedFragmentManager.firstTimeIn()) {
   Log.d(TAG,
         "First time calling onCreate()");
       // Initialize the GenericActivity fields.
   	Log.d(TAG, "initialized view=" + view);
   		initialize(view, presenterType);
  
} else {
   Log.d(TAG,
         "Second (or subsequent) time calling onCreate()");
		   // The RetainedFragmentManager was previously
		   // initialized, which means that a runtime
		   // configuration change occurred.
       Log.d(TAG, " reinitialized v=" + view);
   			reinitalize(view, presenterType);
  
}
}
    /**
     * Initialize the GenericActivity.
     */
    @SuppressWarnings("unchecked")
	private void initialize(MVP.ViewOps view, Class<PresenterType> presenterType){

   		//mPresenter = new ProfilesPresenter(view);
    	try {
			mPresenter =   (PresenterType) presenterType.newInstance();
	
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		//mPresenter.initPresenter();
    	mPresenter.onCreate(view);
    
   		/*
   		if(mPresenter.getModel().getModelData().size() > 0)
   			Log.d(TAG, "1st profile name in view " + getPresenter().getModel().getModelData().get(0).getProfileName());
   		Log.d(TAG, "getPresenter().getModel().getModelData() size is " + String.valueOf(getPresenter().getModel().getModelData().size()));   
   		mPresenter.setPresenterData(mPresenter.getModel().getModelData());
   		
   		if(mPresenter.getPresenterData().size() > 0)
   			Log.d(TAG, "To be stored data " + ((ProfileData) mPresenter.getPresenterData().get(0)).getProfileName());*/
   		mRetainedFragmentManager.put(DATA_KEY, mPresenter);
   	//	mPresenter.onConfigurationChanged(view);
    }
    
    /**
     * Re-initialize the GenericActivity.
     */
    private void reinitalize(MVP.ViewOps view, Class<PresenterType> presenterType){
    	
    	     mPresenter = mRetainedFragmentManager.get(DATA_KEY);
    	    /* if(mPresenter.getPresenterData().size() >  0)
    	    	 Log.d(TAG, "Retrived data " + ((ProfileData) mPresenter.getPresenterData().get(0)).getProfileName());
    	     view.setProfiles(mPresenter.getPresenterData());*/
    	     
    	     Log.d(TAG, " reinitialize    v=" + view);
    	     if(mPresenter != null){
    	    	mPresenter.setView(view);
    	    	mPresenter.onConfigurationChanged(view);
    	     }else
    	    	 initialize(view, presenterType);
    	
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
    public PresenterType getPresenter() {
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
