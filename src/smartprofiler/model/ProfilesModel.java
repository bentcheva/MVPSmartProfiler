package smartprofiler.model;

import java.util.ArrayList;
import java.util.List;

import smartprofiler.MVP;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesPresenter;

public class ProfilesModel implements MVP.ModelAlarmOps{
	
	
	private List<ProfileData> mModelData;
	private ProfilesPresenter mPresenter;
	
	public ProfilesModel(){
		mModelData = new ArrayList<ProfileData>();
		
	}
	
	public void setPresenter(ProfilesPresenter presenter){
		mPresenter = presenter;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<ProfileData> getModelData() {
		// TODO Auto-generated method stub
		return mModelData;
	}

	@Override
	public void setAlarm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelAlarm() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void setModelData(List<T> data) {
		// TODO Auto-generated method stub
		mModelData = (List<ProfileData>) data;
	}

	

}
