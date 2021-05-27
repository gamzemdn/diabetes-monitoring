package info.androidhive.loginandregistration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ListView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ResultActivity extends ActionBarActivity {
	ListView liste;
	ListView liste2;
	private SQLiteHandler db2;
	private String start;
	private String end;
	   private Calendar calendar;
	   private int year, month, day;
	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.rowrs);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        db2 = new SQLiteHandler(getApplicationContext());

        // session manager
       // session = new SessionManager(getApplicationContext());
		HashMap<String, String> user = db2.getUserDetails();
		String email = user.get("email");
        liste=(ListView)findViewById(R.id.list);
        Database db=new Database(getApplicationContext());
        List<Results> results = db.resultListele(email);
			ResultAdapter myListAdapter = new ResultAdapter(ResultActivity.this,
					results);

			liste.setAdapter(myListAdapter);
    }
   
	  @Override
	   protected Dialog onCreateDialog(int id) {
	   // TODO Auto-generated method stub
	      if (id == 999) {
	         return new DatePickerDialog(this, myDateListener, year, month, day);
	       }
	      else if(id == 998){
	    	  
	    	  
		         return new DatePickerDialog(this, myDateListener2, year, month, day);

	    	  
	      }
	      return null;
	   }
	  
	  private DatePickerDialog.OnDateSetListener myDateListener
	   = new DatePickerDialog.OnDateSetListener() {

	   @Override
	   public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
	      // TODO Auto-generated method stub
	      // arg1 = year
	      // arg2 = month
	      // arg3 = day
	      showDate(arg1, arg2+1, arg3);
	   }
	   };

	   private void showDate(int year, int month, int day) {
	      start=(new StringBuilder().append(day).append("-")
	      .append(month).append("-").append(year)).append(" 00:00:00").toString();
	   }
	   
	   private DatePickerDialog.OnDateSetListener myDateListener2
	   = new DatePickerDialog.OnDateSetListener() {

	   @Override
	   public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
	      // TODO Auto-generated method stub
	      // arg1 = year
	      // arg2 = month
	      // arg3 = day
	      showDate2(arg1, arg2+1, arg3);
	   }
	   };

	   private void showDate2(int year, int month, int day) {
	      end=(new StringBuilder().append(day).append("-")
	      .append(month).append("-").append(year)).append(" 00:00:00").toString();
	   }

   
   
  
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result_actions, menu);
		return true;
	}
	@Override
	@SuppressWarnings("deprecation")

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		db2 = new SQLiteHandler(getApplicationContext());

		// session manager
		// session = new SessionManager(getApplicationContext());
		HashMap<String, String> user = db2.getUserDetails();
		String email = user.get("email");

		switch (item.getItemId()) {
		case R.id.beginning:
			  

		      showDialog(999);

	
			break;
		case R.id.end:
		      showDialog(998);

			
			 break;
		case R.id.show:		
			 liste2=(ListView)findViewById(R.id.list);
		       Database db=new Database(getApplicationContext());

		       List<Results> results = db.resultDateListele(start,end,email);
					ResultAdapter myListAdapter = new ResultAdapter(ResultActivity.this,
							results);
					liste2.setAdapter(myListAdapter);
			break;
		default:
			break;
			
		}
		
			
		return super.onOptionsItemSelected(item);
	}
}

