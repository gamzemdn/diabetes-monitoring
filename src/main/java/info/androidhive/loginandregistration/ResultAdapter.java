package info.androidhive.loginandregistration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Results> resultListesi;
	
	public ResultAdapter(Activity activity, List<Results> results) {
		
		mInflater = (LayoutInflater) activity.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		resultListesi = results;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultListesi.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resultListesi.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		View satirView;
		satirView = mInflater.inflate(R.layout.satir, null);
		
		TextView value=(TextView) satirView.findViewById(R.id.lblValue);
		TextView date=(TextView) satirView.findViewById(R.id.lblDate);
		TextView kilo=(TextView) satirView.findViewById(R.id.lblKilo);
		TextView age=(TextView) satirView.findViewById(R.id.lblAge);
	//	TextView cinsiyet=(TextView) satirView.findViewById(R.id.lblCinsiyet);
		ImageView icon=(ImageView) satirView.findViewById(R.id.imgIcon2);
		
		Results rs=resultListesi.get(position);
		
		value.setText(String.valueOf(rs.getValue()));
		date.setText(rs.getDate());
		kilo.setText(String.valueOf(rs.getKilo()));
		age.setText(String.valueOf(rs.getAge()));
		//cinsiyet.setText(String.valueOf(rs.getGender()));
		if(rs.getValue()>=0.8 && rs.getValue()<=1.2){
			
			
			icon.setImageResource(R.drawable.circle_green);

			
		}
		else if(rs.getValue()>1.2 && rs.getValue()<=2.5){
			
			icon.setImageResource(R.drawable.circle_yellow);

			
			
		}
		else{
	
			icon.setImageResource(R.drawable.circle_red);

	
	
             }
		
		
		return satirView;
		
		
		
	}

}
