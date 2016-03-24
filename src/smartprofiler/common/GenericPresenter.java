package smartprofiler.common;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.util.Log;
import smartprofiler.MVP;
import smartprofiler.MVP.ViewOps;

public class GenericPresenter implements MVP.GenericPresenterOps{
	
	 //protected MVP.ViewOps mView;
	// private WeakReference<ViewOps> mView;
	private MVP.ViewOps mView;
	
	 /**
     * Debugging tag used by the Android logger.
     */
    protected final String TAG = 
        getClass().getSimpleName();
	
	public GenericPresenter(){
	}  
	
	
	 @Override
		public void onCreate(MVP.ViewOps view) {
			// TODO Auto-generated method stub
		// this.mView = new WeakReference<>(view);
		 Log.d(TAG, "GenericPresenter onCreate( )");
		 mView = view;
		}
	
	 public MVP.ViewOps getPresenterView(){
		 return  mView;
	 }
	 
	@Override
	public void onConfigurationChanged(ViewOps view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy(boolean isConfigurationChanged) {
		// TODO Auto-generated method stub
		mView = null;
	}


	@Override
	public void setView(ViewOps view) {
		// TODO Auto-generated method stub
		mView = view;
		
	}

	

}
