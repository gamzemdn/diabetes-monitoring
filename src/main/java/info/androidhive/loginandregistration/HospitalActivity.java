package info.androidhive.loginandregistration;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import info.androidhive.loginandregistration.GooglePlace;

public class HospitalActivity extends ListActivity  {
	ArrayList<GooglePlace> venuesList;

ListView listem;
// the google key

	// ============== YOU SHOULD MAKE NEW KEYS ====================//
	final String GOOGLE_KEY = "AIzaSyAl0E-YwAuc-EVuEZBBFyo1UoUnWoFpEM8";



//final	String latitude = "40.9979716, 28.798045;
//final	String longtitude = "";
	//LocationManager locationManager ;
	//String provider ;

	ArrayAdapter<String> myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital);

        listem=(ListView)findViewById(R.id.list);


        // start the AsyncTask that makes the call for the venus search.
				new googleplaces().execute();
   //


    }


    private class googleplaces extends AsyncTask<View, Void, String> {

        String temp;

        @Override
        protected String doInBackground(View... urls) {

            // Getting LocationManager object
            temp = makeCall("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.9979716,28.798045&radius=5000&keyword=hospital&sensor=false&key=AIzaSyAl0E-YwAuc-EVuEZBBFyo1UoUnWoFpEM8");

            //print the call in the console
            System.out.println("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.9979716,28.798045&radius=5000&keyword=hospital&sensor=false&key=AIzaSyAl0E-YwAuc-EVuEZBBFyo1UoUnWoFpEM8");

            return "";
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here


        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            if (temp == null) {
                // we have an error to the call
                // we can also stop the progress bar
            } else {
                // all things went right

                // parse Google places search result
                venuesList = (ArrayList<GooglePlace>) parseGoogleParse(temp);

                List<String> listTitle = new ArrayList<String>();

                for (int i = 0; i < venuesList.size(); i++) {
                    // make a list of the venus that are loaded in the list.
                    // show the name, the category and the city
                    listTitle.add(i, venuesList.get(i).getName() + "\nAddress: " + venuesList.get(i).getAdress());
                }

                // set the results to the list
                // and show them in the xml
                myAdapter = new ArrayAdapter<String>(HospitalActivity.this, R.layout.row_layout, R.id.listText, listTitle);
                setListAdapter(myAdapter);
              //  listem.setAdapter(myAdapter);
                listem=getListView();
                listem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                       // String item = ((TextView) view).getText().toString();
                     //   LatLng a = new LatLng(Double.parseDouble(venuesList.get(position).getLat()), Double.parseDouble(venuesList.get(position).getLng()));
                        Bundle b = new Bundle();
                       String lat=venuesList.get(position).getLat();
                        String lng=venuesList.get(position).getLng();
                        b.putString("latt", lat);
                        b.putString("lngg", lng);
                        Intent intent = new Intent(HospitalActivity.this, MapsActivity.class);
                        // add the selected text item to our intent.
                        intent.putExtras(b);
                        startActivity(intent);
                     //   Toast.makeText(getBaseContext(), a.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                // listem.setAdapter(myAdapter);
            //    LatLng a= new LatLng(Double.parseDouble(venuesList.get(1).getLat()),Double.parseDouble(venuesList.get(1).getLng()));

              //  Toast.makeText(getBaseContext(), a.toString(), Toast.LENGTH_LONG).show();
                //new ListClickHandler();

            }



        }

    }


	public static String makeCall(String url) {

		// string buffers the url
		StringBuffer buffer_string = new StringBuffer(url);
		String replyString = "";

		// instanciate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// instanciate an HttpGet
		HttpGet httpget = new HttpGet(buffer_string.toString());

		try {
			// get the responce of the httpclient execution of the url
			HttpResponse response = httpclient.execute(httpget);
			InputStream is = response.getEntity().getContent();

			// buffer input stream the result
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// the result as a string is ready for parsing
			replyString = new String(baf.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(replyString);

		// trim the whitespaces
		return replyString.trim();
	}

	private static ArrayList<GooglePlace> parseGoogleParse(final String response) {

		ArrayList<GooglePlace> temp = new ArrayList<GooglePlace>();
		try {

			// make an jsonObject in order to parse the response
			JSONObject jsonObject = new JSONObject(response);

			// make an jsonObject in order to parse the response
			if (jsonObject.has("results")) {

				JSONArray jsonArray = jsonObject.getJSONArray("results");

				for (int i = 0; i < jsonArray.length(); i++) {
					GooglePlace poi = new GooglePlace();
					
						if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).optString("name"));

								if(jsonArray.getJSONObject(i).has("vicinity")){
									poi.setAdress(jsonArray.getJSONObject(i).optString("vicinity"));
								}

                            if(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").has("lat")){
                                poi.setLat(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").optString("lat"));
                            }
                            if(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").has("lng")){
                                poi.setLng(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").optString("lng"));
                            }
                        }

					temp.add(poi);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<GooglePlace>();
		}
		return temp;

	}


       /*
            Bundle b = new Bundle();
            b.putString("latt", lat);
            b.putString("lngg", lng);
            Intent intent = new Intent(HospitalActivity.this, MapsActivity.class);
            // add the selected text item to our intent.
            intent.putExtras(b);
            startActivity(intent);*/

        }




