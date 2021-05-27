package info.androidhive.loginandregistration;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


public class PharmActivity extends ActionBarActivity  {
	private SQLiteHandler db2;
	private  String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharm);
        db2 = new SQLiteHandler(getApplicationContext());
		HashMap<String, String> user = db2.getUserDetails();


		//Toast.makeText(getApplicationContext(),
		//   gender, Toast.LENGTH_LONG)
		//   .show();
		email = user.get("email");


		BarChart chart = (BarChart) findViewById(R.id.chart);

		BarData data = new BarData(getXAxisValues(), getDataSet());
		chart.setData(data);
		chart.setDescription("My Chart");
		chart.animateXY(2000, 2000);
		chart.invalidate();
	}

	private ArrayList<BarDataSet> getDataSet() {

		ArrayList<BarDataSet> dataSets = null;
        Database db=new Database(getApplicationContext());
     //   List<Double> sonuclar=db.resultValueListele();
        double[] sonuclar=db.resultValueListele(email);

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();


        for (int i = 0; i < sonuclar.length; i++) {
            valueSet1.add(new BarEntry((float)sonuclar[i],i));

        }




        db.close();
	/*	BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
		valueSet1.add(v1e2);
		BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
		valueSet1.add(v1e3);
		BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
		valueSet1.add(v1e4);
		BarEntry v1e5 = new BarEntry(90.000f, 4); // May
		valueSet1.add(v1e5);
		BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
		valueSet1.add(v1e6);*/

		/*ArrayList<BarEntry> valueSet2 = new ArrayList<>();
		BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
		valueSet2.add(v2e1);
		BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
		valueSet2.add(v2e2);
		BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
		valueSet2.add(v2e3);
		BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
		valueSet2.add(v2e4);
		BarEntry v2e5 = new BarEntry(20.000f, 4); // May
		valueSet2.add(v2e5);
		BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
		valueSet2.add(v2e6);*/

		BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Results");
		barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
	//	BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
	// barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

		dataSets = new ArrayList<>();
		dataSets.add(barDataSet1);
	//	dataSets.add(barDataSet2);
		return dataSets;
	}

	private ArrayList<String> getXAxisValues() {
		ArrayList<String> xAxis = new ArrayList<>();

        Database db=new Database(getApplicationContext());
        //   List<Double> sonuclar=db.resultValueListele();
        String[] sonuclar=db.resultDatesListele(email);
        //ArrayList<BarEntry> valueSet1 = new ArrayList<>();
db.close();

        for (int i = 0; i < sonuclar.length; i++) {
            xAxis.add(sonuclar[i]);

        }

		return xAxis;
	}
}
