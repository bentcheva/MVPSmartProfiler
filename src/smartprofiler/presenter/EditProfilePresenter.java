package smartprofiler.presenter;

import smartprofiler.MVP;
import smartprofiler.MVP.ViewOps;
import smartprofiler.common.GenericPresenter;
import smartprofiler.model.ProfileModel;

public class EditProfilePresenter extends GenericPresenter implements MVP.GenericPresenterOps{
	
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
	}

	@Override
	public ViewOps getPresenterView() {
		// TODO Auto-generated method stub
		return super.getPresenterView();
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

}
