package smartprofiler;

import java.util.List;

import android.content.Intent;
import smartprofiler.common.ContextView;
import smartprofiler.presenter.ProfileData;

/**
 * Provides the interfaces for MVP pattern
 * @author Bobi
 *
 */
public interface MVP {

	
	/**
	 * This interface provides the API for the presenter 
	 * to interact with the View layer
	 * @author Bobi
	 *
	 */
	public interface ViewOps extends ContextView{
		/**
		 * Loads data into the View's List Adapter.
		 * @param <T>
		 * @param data List of ProfileData objects.
		 */
		public <T> void displayProfiles(List<T> data);
		/**
		 *  Sets The View's data structure holding the ProfileData objects.
		 * @param profiles
		 */
		public void setProfiles(List<ProfileData> profiles);
		/**
		 * Gets the Profiles.
		 * @return List of ProfileData objects.
		 */
		public List<ProfileData> getProfiles();
	//	public void displayProfiles(List<ProfileData> data);
	}
	/**
	 * Provides basic interface for Presenters
	 * @author Bobi
	 *
	 */
	public interface GenericPresenterOps {
		
		/**
		 * Callback method initializing the presenter
		 * @param view
		 */
		public void onCreate(ViewOps view);
		/**
		 * Callback when configuration ( device orientattion ) has changed.
		 * @param view
		 */
		public void onConfigurationChanged(ViewOps view);
		
		/**
		 *  Destroys the presenter if isConfigurationChanges is true.
		 * @param isConfigurationChanged 
		 */
		public void onDestroy(boolean isConfigurationChanged);
		/**
		 * Sets the presenter View
		 * @param view
		 */
		public void setView(ViewOps view);
		
	}
	/**
	 * Provides basic interface for the Model layer
	 * @author Bobi
	 *
	 */
	public interface ModelOps{
		
		/**
		 * Set the Profiles data obtained from the Presenter layer
		 * @param <T>
		 * @param data List of ProfileData objects
		 */
		public <T> void setModelData(List<T> data);
		/**
		 * Gets the Model's Data 
		 * @param <T>
		 * @return List of ProfileData objects
		 */
		public <T> List<T> getModelData();
		
		
		
	}
	
	public interface ModelAlarmOps extends ModelOps{
		
		public void setAlarm();
		
		public void cancelAlarm();
	}
	/**
	 * This interface provides API for ProfilePresenter to 
	 * interact with ProfileViewActivity
	 * @author Bobi
	 *
	 */
	public interface ProfileViewOps{
		
		public void showProfile(ProfileData profile);
	}
	
	/**
	 * This interface provides the APi for the presenter 
	 * to interact with the Model layer
	 * @author Bobi
	 *
	 */
	public interface PresenterOps {
		
		/**
		 *  Loading data from dB table.
		 *  Works as intermediator between
		 *  the Presenter Layer and the View Layer
		 */
		public void loadData();
		/**
		 *  First Ever run of the app.
		 */
		public boolean isFirstRunEver();
		
		/**
		 * Gets the Model associated with the Presenter 
		 * @return Model associated with the presenter
		 */
		public ModelOps getPresenterModel();
			
		
		/**
		 * Gets Presenter Data. Works as intermediator between
		 *  the Presenter Layer and the View Layer
		 * @param <T>
		 * @return List of ProfileData
		 */
	//	public <T> List<T> getPresenterData();
		/**
		 * Sets presenter data. Works as intermediator between
		 *  the Presenter Layer and the View Layer
		 * @param <T>
		 * @param data List of ProfileData
		 */
	//	public <T> void setPresenterData(List<T> data);
		/**
		 * Initializes the Presenter object
		 */
	//	public void initPresenter();
		/**
		 * Works as bridge between the View layer and the Model when deleting profile
		 */
		public void deleteProfile(int position);
		/**
		 * Works as bridge between the View layer and the Model when added new Profile
		 */
		public void addProfile(Intent data);
		
	}
}
