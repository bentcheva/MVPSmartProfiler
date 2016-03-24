package smartprofiler.views;
import smartprofiler.presenter.CreateDefaultProfiles;
import smartprofiler.presenter.ProfileData;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.smartprofiler.R;


/**
 * The purpose of this class is to visualize selected profile's data
 * in custom dialog box. 
 * @author Bobi
 *
 */
public class ViewProfileDialog {
	
	private DisplayMetrics mDisplayMetrics;
	private Context mContext;
	private ProfileData mProfile;
	private TextView mName;
	private TextView mWifi, mSound,  mVibration, mStart, mStop;
	private Dialog mDialog;
	private Button mCancelButton;
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        ViewProfileDialog.class.getName();
	
	public ViewProfileDialog( ProfileData profile, Context context){
		
		mProfile = profile;
		mContext = context;
		
		initViews();
		
		
	}
	
	private void initViews(){
		
		Log.d(TAG, "initViews()");
		mDialog = new Dialog(mContext);
		mDialog.setContentView(R.layout.view_profile);
		mDialog.setTitle("Profile's attributes");
		mName = (TextView) mDialog.findViewById(R.id.textView1);
		mWifi = (TextView) mDialog.findViewById(R.id.textView2);
		mSound = (TextView) mDialog.findViewById(R.id.textView3);
		mVibration = (TextView) mDialog.findViewById(R.id.textView5);
		mStart = (TextView) mDialog.findViewById(R.id.textView6);
		mStop = (TextView) mDialog.findViewById(R.id.textView7);
	//	mCancelButton = (Button) mDialog.findViewById(R.id.cancel);
		mCancelButton = (Button) mDialog.findViewById(R.drawable.raised_button);
	}
	
	private int[] getDimensions(){
		int[] dialogDimensions = new int[2];
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		dialogDimensions[0] = metrics.widthPixels;
		dialogDimensions[0] = metrics.heightPixels;
		return dialogDimensions;
	}
	
	/**
	 * This method initializes the TextViews with the data from the presenter
	 */
	public void viewDialog(){
		
		Log.d(TAG, "viewDialog()");
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		
		
		mDialog.getWindow().setLayout((8* width)/9, (1 * height)/2);
	
		if(mProfile != null)
		{
			mName.setText("Profile name " +  mProfile.getProfileName());
			mWifi.setText("WiFi is " + attributeToString(mProfile.getProfileWiFi()));
			mSound.setText("Sound is " + attributeToString(mProfile.getProfileSound()));
			mVibration.setText("Phone vibration is " + attributeToString(mProfile.getProfileVibration()));
			int[] startTime = Utils.Utils.minutesToTime(mProfile.getProfileStartTime());
			int[] stopTime = Utils.Utils.minutesToTime(mProfile.getProfileStopTime());
			if(mProfile.getProfileStartTime() != CreateDefaultProfiles.DEFAULT_START)
				mStart.setText("The profile activates at" + startTime[0] + ":" + startTime[1]);
			else
				mStart.setText("The profile is not time dependant.");
			
			if(mProfile.getProfileStopTime() != CreateDefaultProfiles.DEFAULT_STOP)
				mStop.setText("The profile de-activates at" + stopTime[0] + ":" + stopTime[1]);
			
		}
		
		mCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDialog.dismiss();
			}
		});
		
		mDialog.show();
	}
	
	private String attributeToString(int status){
		StringBuilder sb = new StringBuilder("");
		switch(status){
		case 0: sb.append("OFF");
				break;
		case 1: sb.append("ON");
				break;
		case 2: sb.append("NO CHANGE");
				break;
		case 3: sb.append("NOT SET");
		}
		return sb.toString();
	}

}
