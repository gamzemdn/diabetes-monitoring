package info.androidhive.loginandregistration;


import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;


public class Advice extends Activity {

	double toplam, ort, ort2;
	EditText preg;
	EditText bgl;
	EditText pressure;
	EditText skin;
	EditText insulin;
	EditText weight;
	EditText height;
	EditText age;
	double bmi;
	double pedigree;
	Button submit;
	int pregg,bgll,pressuree,skinn,insulinn,agee;
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advice);
        Database db=new Database(getApplicationContext());
        double result=db.sumValue();
        int count=db.countValue();
	    toplam=result/count;
   	    toplam= Math.floor(toplam * 1e2) / 1e2;

	preg=(EditText)findViewById(R.id.pregEditText);
	bgl=(EditText)findViewById(R.id.bglEditText);
	pressure=(EditText)findViewById(R.id.pressureEditText);
	skin=(EditText)findViewById(R.id.skinEditText);
	insulin=(EditText)findViewById(R.id.insulinEditText);
	weight=(EditText)findViewById(R.id.weightEditText);
	height=(EditText)findViewById(R.id.heightEditView);
	age=(EditText)findViewById(R.id.ageEditText);
	submit=(Button)findViewById(R.id.btnsubmit);

	submit.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			pregg=Integer.parseInt(preg.getText().toString());
			bgll=Integer.parseInt(bgl.getText().toString());
			pressuree=Integer.parseInt(pressure.getText().toString());
			skinn=Integer.parseInt(skin.getText().toString());
			insulinn=Integer.parseInt(insulin.getText().toString());
			agee=Integer.parseInt(age.getText().toString());
			double heightt=Double.parseDouble(height.getText().toString());
			heightt=heightt*heightt;
			double weightt=Double.parseDouble(weight.getText().toString());
			bmi=weightt/heightt;
			//bmi=30.1;
			DecimalFormat df = new DecimalFormat("#.#");
			String dx=df.format(bmi);
			bmi=Double.valueOf(dx);
			pedigree=0.398;
			//Toast.makeText(getApplicationContext(), height.getText().toString() , Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), weight.getText().toString() , Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), String.valueOf(bmi), Toast.LENGTH_LONG).show();
			Attribute Attribute1 = new Attribute("preg");
			Attribute Attribute2 = new Attribute("plas");
			Attribute Attribute3 = new Attribute("dbp");
			Attribute Attribute4 = new Attribute("skin");
			Attribute Attribute5 = new Attribute("insulin");
			Attribute Attribute6 = new Attribute("mass");
			Attribute Attribute7 = new Attribute("pedi");
			Attribute Attribute8 = new Attribute("age");

			List classvalues = new ArrayList(2);
			classvalues.add("0");
			classvalues.add("1");

			Attribute Attribute9 = new Attribute("Class", classvalues);
			List fvWekaAttributes=new ArrayList(9);
			fvWekaAttributes.add(Attribute1);
			fvWekaAttributes.add(Attribute2);
			fvWekaAttributes.add(Attribute3);
			fvWekaAttributes.add(Attribute4);
			fvWekaAttributes.add(Attribute5);
			fvWekaAttributes.add(Attribute6);
			fvWekaAttributes.add(Attribute7);
			fvWekaAttributes.add(Attribute8);
			fvWekaAttributes.add(Attribute9);
			Instances test = new Instances("Rel", (ArrayList<Attribute>) fvWekaAttributes, 10);
			// Set class index
			test.setClassIndex(8);
			Instance iExample = new DenseInstance(9);
			iExample.setValue((Attribute)fvWekaAttributes.get(0), pregg);
			iExample.setValue((Attribute) fvWekaAttributes.get(1), bgll);
			iExample.setValue((Attribute)fvWekaAttributes.get(2), pressuree);
			iExample.setValue((Attribute) fvWekaAttributes.get(3), skinn);
			iExample.setValue((Attribute)fvWekaAttributes.get(4), insulinn);
			iExample.setValue((Attribute)fvWekaAttributes.get(5), bmi);
			iExample.setValue((Attribute)fvWekaAttributes.get(6),pedigree);
			iExample.setValue((Attribute)fvWekaAttributes.get(7), agee);
			iExample.setValue((Attribute)fvWekaAttributes.get(8), 1);
			// add the instance
			test.add(iExample);
			try {

				AssetManager assetManager = getAssets();
				InputStream iss = assetManager.open("yeni.arff");
				Instances data =new Instances(new BufferedReader(new InputStreamReader(iss)));
				//  DataSource source = new DataSource ("/assets/yeni.arff");
				//  Instances data = source.getDataSet();
				iss.close();
				// setting class attribute if the data format does not provide this information
				// For example, the XRFF format saves the class attribute information as well
				if (data.classIndex() == -1)
					data.setClassIndex(data.numAttributes() - 1);

           /* String[] options = new String[1];
            options[0] = "-U";            // unpruned tree
            J48 tree = new J48();         // new instance of tree
            tree.setOptions(options);     // set the options
            tree.buildClassifier(data);   // build classifier*/
				Classifier cls = new J48();
				cls.buildClassifier(data);
				// evaluate classifier and print some statistics
				Evaluation eval = new Evaluation(data);
				eval.evaluateModel(cls, test);

				String strSummary = eval.toSummaryString();
				System.out.println(strSummary);
				iExample.setDataset(data);

				double[] fDistribution = cls.distributionForInstance(iExample);

				System.out.println(fDistribution[0]);
				System.out.println(fDistribution[1]);
				DecimalFormat df2 = new DecimalFormat("#.#");
				String dx2=df2.format(fDistribution[0]);
				fDistribution[0]=Double.valueOf(dx2);
				DecimalFormat df3 = new DecimalFormat("#.#");
				String dx3=df3.format(fDistribution[1]);
				fDistribution[1]=Double.valueOf(dx3);

				if(fDistribution[0]>fDistribution[1])

				{

					//Toast.makeText(getApplicationContext(), "IYISIN!!!!", Toast.LENGTH_LONG).show();
					LayoutInflater layoutInflater= (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
					View popupView = layoutInflater.inflate(R.layout.popup, null);
					TextView txtAdvice=(TextView)popupView.findViewById(R.id.txtAdvice);
					ImageView image=(ImageView)popupView.findViewById(R.id.imgAdvice);
					txtAdvice.setText("No Sign of Disease:)");
					image.setImageDrawable(getResources().getDrawable(R.drawable.circle_green));
					final PopupWindow popupWindow = new PopupWindow(
							popupView,
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);

					Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
					btnDismiss.setOnClickListener(new Button.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							popupWindow.dismiss();
						}
					});

					popupWindow.showAtLocation(submit, Gravity.CENTER, 0, 0);
				}
				else
				{

					//Toast.makeText(getApplicationContext(), "IYISIN!!!!", Toast.LENGTH_LONG).show();
					LayoutInflater layoutInflater= (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
					View popupView = layoutInflater.inflate(R.layout.popup, null);
					TextView txtAdvice=(TextView)popupView.findViewById(R.id.txtAdvice);
					ImageView image=(ImageView)popupView.findViewById(R.id.imgAdvice);
					txtAdvice.setText("Risk is Detected!");
					image.setImageDrawable(getResources().getDrawable(R.drawable.circle_red));
					final PopupWindow popupWindow = new PopupWindow(
							popupView,
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);

					Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
					btnDismiss.setOnClickListener(new Button.OnClickListener(){

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							popupWindow.dismiss();
						}});

					popupWindow.showAtLocation(submit, Gravity.CENTER, 0, 0);
				}
           /* AssetManager assetManager = getAssets();
           InputStream iss = assetManager.open("yeni.arff");//getApplicationContext().getAssets("yeni.arff"); //assetManager.open("yeni.arff");
            File outFile = new File(getExternalFilesDir(null), "kopya.arff");
            OutputStream oss=new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int read;
            while((read = iss.read(buffer)) != -1){
                oss.write(buffer, 0, read);
            }
            Instances dataset =new Instances(new BufferedReader(new InputStreamReader(iss)));
            ArffSaver saver=new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(outFile);
            saver.writeBatch();*/
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	});



}

	@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main_actions, menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	switch (item.getItemId()) {
	case R.id.home:
		onBackPressed();
		break;

	default:
		break;
	}
	   

	return super.onOptionsItemSelected(item);
}
}
