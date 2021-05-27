package info.androidhive.loginandregistration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import testowlprj.TESTOWLPRJ;

public class MainActivity extends Activity {

	private EditText etKilo;
	private EditText etAge;
	private Button btnLogout;

	final Random rand = new Random();

	public Button startButton;
	public String currentDateTimeString;

	public TextView txtValue;
    public TextView textView2;
	final NumberFormat format = NumberFormat.getInstance();
	final Timer timer = new Timer();
	public RelativeLayout bgElement;
	public double number;

	ImageView imgResult;
	ImageView imgAdvice;
	ImageView imgPharm;
	ImageView imgHospital;
	private SQLiteHandler db;
	private SessionManager session;
	//public  String gender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etKilo= (EditText) findViewById(R.id.editTextKilo);
		etAge = (EditText) findViewById(R.id.editTextAge);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		txtValue=(TextView)findViewById(R.id.txtValue);
		startButton=(Button)findViewById(R.id.btnStart);
		bgElement=(RelativeLayout)findViewById(R.id.relativeLayout);
		imgResult=(ImageView)findViewById(R.id.imgResult);
		imgAdvice=(ImageView)findViewById(R.id.imgAdvice);
		imgHospital=(ImageView)findViewById(R.id.imgHospital);
			imgPharm=(ImageView)findViewById(R.id.imgPharm);
//textView2=(TextView)findViewById(R.id.textView2);
		format.setRoundingMode(RoundingMode.DOWN);
		format.setMaximumFractionDigits(2);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		final String  gender = db.getUserGender();
        //Toast.makeText(getApplicationContext(),
          //   gender, Toast.LENGTH_LONG)
            //   .show();
    	final	String email = user.get("email");

		// Displaying the user details on the screen
		//textView2.setText(gender);
	//	txtEmail.setText(email);



		imgResult.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, ResultActivity.class));
			}
		});

		imgHospital.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, HospitalActivity.class));
			}
		});

		imgPharm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, PharmActivity.class));
			}
		});


		imgAdvice.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, Advice.class));
			}
		});

		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				bgElement.setBackgroundColor(Color.WHITE);


				number = rand.nextDouble()*(3.5-0.8)+0.8;


				number= Math.floor(number * 1e2) / 1e2;



				new CountDownTimer(4000, 1000) {

					public void onTick(long millisUntilFinished) {
						txtValue.setText(Long.toString(millisUntilFinished / 1000));
					}

					public void onFinish() {
						txtValue.setText(format.format(number));
						currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


						Database dbs=new Database(getApplicationContext());
						dbs.resultKaydet(new Results(number, currentDateTimeString,Double.parseDouble(etKilo.getText().toString()),Integer.parseInt(etAge.getText().toString()),gender,email));
						dbs.close();


						if(number>=0.8 && number<=1.2){

							bgElement.setBackgroundColor(Color.GREEN);


						}
						else if(number>1.2 && number<=2.5)
						{


							bgElement.setBackgroundColor(Color.YELLOW);



						}
						else
						{


							bgElement.setBackgroundColor(Color.RED);

						}


					}
				}.start();

			}
		});

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});
	}


    /**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {

		session.setLogin(false);

		db.deleteUsers();



		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
