package smartprofiler.views;

import java.util.List;

import com.example.smartprofiler.R;

import smartprofiler.common.PropertyItem;
import smartprofiler.presenter.AddNewProfilePresenter;
import smartprofiler.presenter.ProfileData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * Helper class to populate the listView containing the Profile properties
 * @author Bobi
 *
 */

public class AddDataAdapterRadio extends ArrayAdapter<PropertyItem> {
	

private int mResource;
private AddNewProfilePresenter mPresenter;
private ProfileData mEditedProfile;


/**
 * Used for Android debugging.
 */
private final static String TAG = 
    AddDataAdapterRadio.class.getName();


	/**
	 * Constructor
	 * @param context 
	 * @param mPropertyItemList
	 */
	public AddDataAdapterRadio(Context context) {
		
		// TODO Auto-generated constructor stub
		super(context, R.layout.add_profile_row);
		// TODO Auto-generated constructor stub
		mResource = R.layout.add_profile_row;
		
	}

	public void setAdapterPresenter(AddNewProfilePresenter presenter, ProfileData edited){
		mPresenter = presenter;
		mEditedProfile = edited;
		//if the mEditedProfile is not null, means that we are in Edit mode, sets the Model's data
	/*	if(mEditedProfile != null)
		{
			List<PropertyItem> item = mPresenter.getModel().toList(mEditedProfile);
			mPresenter.getModel().setModelData(item);
		}  */
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mPresenter!= null)
			return mPresenter.getModel().getModelData().size();
		else 
			return 0;
	}

	@Override
	public PropertyItem getItem(int position) {
		// TODO Auto-generated method stub
		return mPresenter.getModel().getModelData().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	

	public List<PropertyItem> getAll(){
		return mPresenter.getModel().getModelData();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	 ViewHolder holder;
	
		// TODO Auto-generated method stub
		 if (convertView != null) {
	            // Get the parts of this view that's already been
	            // instantiated.
			    Log.d(TAG, "ConvertView != null");
			   
	            holder = (ViewHolder) convertView.getTag();
	        } else {
	            // We don't have a view to convert, make one.
	            convertView =
	                LayoutInflater.from(getContext()).inflate(mResource,
	                                                          parent,
	                                                      false);
	        holder = new ViewHolder();
	        holder.property  = (TextView) convertView.findViewById(R.id.itemView1);
	        holder.property.setText(mPresenter.getModel().getModelData().get(position).getName());
	        holder.radio = (RadioGroup) convertView.findViewById(R.id.radioGroup1);
	        holder.on = (RadioButton) convertView.findViewById(R.id.radio0);
	        holder.off = (RadioButton) convertView.findViewById(R.id.radio1);
	        holder.nochange = (RadioButton) convertView.findViewById(R.id.radio2);
	        convertView.setTag(holder);
	        holder.radio.setTag(position);
	        Log.d(TAG, "mPresenter status  " + mPresenter.getModel().getModelData().get(position).getStatus());
	        switch(mPresenter.getModel().getModelData().get(position).getStatus()){
	        	case 0: holder.on.setChecked(true);
	        			break;
	        	case 1: holder.off.setChecked(true);
	        			break;
	        	case 2: holder.nochange.setChecked(true);
	        			break;
	        	default: Log.d(TAG, "Unexpected value");
	        			break;
	        				
	        }
	        if(position == 4)
	        	holder.radio.setVisibility(View.INVISIBLE);
	        if(position == 5)
	        	holder.radio.setVisibility(View.INVISIBLE);
	       
	        }  
		 
		 holder.radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int profileId = (int)group.getTag();
				Log.d(TAG, "position in listview is " + profileId);
				
				
				switch(checkedId){
				case R.id.radio0: mPresenter.getModel().getModelData().get(profileId).setStatus(1);
								  
									break;
				case R.id.radio1: mPresenter.getModel().getModelData().get(profileId).setStatus(0);
									break;
				case R.id.radio2: mPresenter.getModel().getModelData().get(profileId).setStatus(2);
									break;
				default: mPresenter.getModel().getModelData().get(profileId).setStatus(3);
									break;
				}
				Log.d(TAG, " " + mPresenter.getModel().getModelData().get(profileId).getStatus());
				
				
			}

		 });
		 
		
		 
		return convertView;
	}

	
	
	private class ViewHolder{
		private TextView property;
		private RadioGroup radio;
		private RadioButton on, off, nochange;
		
	}

}
