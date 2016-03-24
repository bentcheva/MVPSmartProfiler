package smartprofiler.presenter;

import java.lang.ref.WeakReference;

import android.util.Log;
import smartprofiler.MVP;
import smartprofiler.MVP.ViewOps;
import smartprofiler.common.GenericPresenter;


public class ProfilePresenter extends GenericPresenter implements MVP.GenericPresenterOps {

	
	// private WeakReference<V> mView;
	 
	 	@Override
	public void onCreate(ViewOps view) {
		// TODO Auto-generated method stub
		super.onCreate(view);
	}

	@Override
	public void onConfigurationChanged(ViewOps view) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(view);
	}

	@Override
	public void onDestroy(boolean isConfigurationChanged) {
		// TODO Auto-generated method stub
		super.onDestroy(isConfigurationChanged);
	}

		/**
	     * Debugging tag used by the Android logger.
	     */
	    protected final String TAG = 
	        getClass().getSimpleName();
	 
	public ProfilePresenter() {
		
		// TODO Auto-generated constructor stub
		
	}

}
