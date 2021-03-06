package smartprofiler.views;

import java.util.List;

import com.example.smartprofiler.R;

import smartprofiler.common.PropertyItem;
import smartprofiler.presenter.AddNewProfilePresenter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * Helper class to populate the listView containing the Profile properties
 * @author Bobi
 *
 */

public class AddDataAdapter extends ArrayAdapter<PropertyItem> {
	
private Context mContext;
private int mResource;
//private List<PropertyItem> mProperties;
private AddNewProfilePresenter mPresenter;


/**
 * Used for Android debugging.
 */
private final static String TAG = 
    AddDataAdapter.class.getName();


	/**
	 * Constructor
	 * @param context 
	 * @param mPropertyItemList
	 */
	public AddDataAdapter(Context context) {
		
		// TODO Auto-generated constructor stub
		super(context, R.layout.profile_item );
		// TODO Auto-generated constructor stub
		mContext = context;
		mResource = R.layout.profile_item;
		
	}

	public void setAdapterPresenter(AddNewProfilePresenter presenter){
		mPresenter = presenter;
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
	        holder.status = (CheckBox) convertView.findViewById(R.id.checkBox1);
	        convertView.setTag(holder);
	        holder.status.setTag(position);
	        if(mPresenter.getModel().getModelData().get(position).getStatus()==0)
	        	holder.status.setChecked(false);
	        else{
	        	holder.status.setChecked(true);
	        }
	        }  
		 
		 
		 holder.status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				 if(isChecked == true){
					 mPresenter.getModel().getModelData().get((int) buttonView.getTag()).setStatus(1);
					 mPresenter.getModel().getModelData().get((int) buttonView.getTag()).setStatus(1);
					 Log.d(TAG, String.valueOf(mPresenter.getModel().getModelData().get((int) buttonView.getTag()).getName()));
					 Log.d(TAG, String.valueOf(mPresenter.getModel().getModelData().get((int) buttonView.getTag()).getStatus()));
				 }
			}
		});
		 
		return convertView;
	}

	
	
	private class ViewHolder{
		private TextView property;
		private CheckBox status;
		
	}

}
