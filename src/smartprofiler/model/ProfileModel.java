package smartprofiler.model;

import java.util.ArrayList;
import java.util.List;

import smartprofiler.MVP;
import smartprofiler.common.PropertyItem;
import smartprofiler.presenter.AddNewProfilePresenter;
import smartprofiler.presenter.ProfileData;



public class ProfileModel implements MVP.ModelOps {

	private List<PropertyItem> mProperties;
	private AddNewProfilePresenter mPresenter;
	private String[] mProfileProperties = {"WiFi", "Sound", "Mobile Data",
			"Vibration", "Start time", "Stop time"};
	
	/**
	 * Constructor 
	 */
	public ProfileModel(){
		mProperties = new ArrayList<PropertyItem>();
		initPropertyData(mProfileProperties);
	}

	
	private void initPropertyData(String[] names){
		
		for(String str: names){
			mProperties.add(new PropertyItem(str, 3));
		}
		
	}
	public void setPresenter(AddNewProfilePresenter presenter){
		mPresenter = presenter;
	}
	public List<PropertyItem> toList(ProfileData profile){
		List<PropertyItem> listProperties = new ArrayList<PropertyItem>();
		listProperties.add(new PropertyItem(mProfileProperties[0], profile.getProfileWiFi()));
		listProperties.add(new PropertyItem(mProfileProperties[1], profile.getProfileSound()));
		listProperties.add(new PropertyItem(mProfileProperties[2], profile.getProfileMobileData()));
		listProperties.add(new PropertyItem(mProfileProperties[3], profile.getProfileVibration()));
		listProperties.add(new PropertyItem(mProfileProperties[4], profile.getProfileStartTime()));
		listProperties.add(new PropertyItem(mProfileProperties[5], profile.getProfileStopTime()));
		return listProperties;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyItem> getModelData() {
		// TODO Auto-generated method stub
		return mProperties;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void setModelData(List<T> data) {
		// TODO Auto-generated method stub
			mProperties = (List<PropertyItem>) data;
		
	}
	
}
