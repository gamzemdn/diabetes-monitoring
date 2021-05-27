package info.androidhive.loginandregistration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

	private static final String DATABASE_NAME="OLCUMM";
	private static final String TABLE_NAME="OLCUMSONUCLARII";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

			String sql="CREATE TABLE "+ TABLE_NAME + "(id INTEGER PRIMARY KEY, value REAL, tarih TEXT, kilo REAL, age INTEGER, gender TEXT, email TEXT"+")";
			db.execSQL(sql);
		
	}
	
	public void resultKaydet(Results result){
		SQLiteDatabase db=this.getWritableDatabase();
		
		ContentValues values=new ContentValues();
		
		values.put("value", result.getValue());
		values.put("tarih", result.getDate());
		values.put("kilo",result.getKilo());
		values.put("age",result.getAge());
		values.put("gender",result.getGender());
		values.put("email",result.getEmail());
		db.insert(TABLE_NAME, DATABASE_NAME, values);
		db.close();
		
	}
	
	
	
	public List<Results> resultListele(String email) {
		List<Results> results=new ArrayList<Results>();
		SQLiteDatabase db=this.getWritableDatabase();
		
		Cursor cursor=db.query(TABLE_NAME, new String[]{"id","value","tarih","kilo","age","gender","email"}, "email=?", new String[]{email}, null,null, "tarih"+" DESC");

		while (cursor.moveToNext()) {
			Results rs=new Results();
			rs.setId(cursor.getInt(0));
			rs.setValue(cursor.getDouble(1));
			rs.setDate(cursor.getString(2));
			rs.setKilo(cursor.getDouble(3));
			rs.setAge(cursor.getInt(4));
			rs.setEmail(cursor.getString(6));
			results.add(rs);
					
		}
		
		db.close();
		return results;
		
	}


	public double[] resultValueListele(String email) {
		//List<Double> results=new ArrayList<Double>();
		SQLiteDatabase db=this.getWritableDatabase();

		// Cursor cursor=db.query(TABLE_NAME, new String[]{"id","value","date"}, "date"+" >= "+start+" AND "+"date"+" <= "+end, null, null,null, "date"+" DESC");
		Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE email='"+email+"'", null);
        int count = cursor.getCount();

        double[] values = new double[count];

        for(int i=0; i<count; i++) {
            cursor.moveToNext();

			values[i] = cursor.getDouble(1);



        }
db.close();

        return values;
	}

    public String[] resultDatesListele(String email) {
     //   List<String> results=new ArrayList<String>();
        SQLiteDatabase db=this.getWritableDatabase();

        // Cursor cursor=db.query(TABLE_NAME, new String[]{"id","value","date"}, "date"+" >= "+start+" AND "+"date"+" <= "+end, null, null,null, "date"+" DESC");
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE email='"+email+"'", null);
        int count = cursor.getCount();


        String[] dates = new String[count];
        for(int i=0; i<count; i++) {
            cursor.moveToNext();


            dates[i]=cursor.getString(2);


        }

db.close();
        return dates;
    }


	public List<Results> resultDateListele(String start,String end, String email) {
		List<Results> results=new ArrayList<Results>();
		SQLiteDatabase db=this.getWritableDatabase();
  
	 // Cursor cursor=db.query(TABLE_NAME, new String[]{"id","value","date"}, "date"+" >= "+start+" AND "+"date"+" <= "+end, null, null,null, "date"+" DESC");
		Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE email='"+email+"' AND tarih BETWEEN '"+start+"' AND '"+end+"' ORDER BY tarih DESC",null);
		while (cursor.moveToNext()) {
			Results rs=new Results();
			rs.setId(cursor.getInt(0));
			rs.setValue(cursor.getDouble(1));
			rs.setDate(cursor.getString(2));
			rs.setKilo(cursor.getDouble(3));
			rs.setAge(cursor.getInt(4));
			//rs.setGender(cursor.getString(5));
			results.add(rs);
					
		}
		
		db.close();
		return results;
		
	}
	
	public int countValue() {
		SQLiteDatabase db=this.getWritableDatabase();
         int result=0;
	//  Cursor cursor=db.query(TABLE_NAME, new String[]{"id","value","date"}, "date"+" >= "+start+" AND "+"date"+" <= "+end, null, null,null, "date"+" DESC");
		Cursor cursor=db.rawQuery("SELECT COUNT(value) FROM "+TABLE_NAME,null);

		while (cursor.moveToNext()) {
	     result=cursor.getInt(0);
		}
		return result;
		
	}
	
	public double sumValue() {
		SQLiteDatabase db=this.getWritableDatabase();
         double result=0;
	//  Cursor cursor=db.query(TABLE_NAME, new String[]{"id","value","date"}, "date"+" >= "+start+" AND "+"date"+" <= "+end, null, null,null, "date"+" DESC");
		Cursor cursor=db.rawQuery("SELECT SUM(value) FROM "+TABLE_NAME,null);
    
		while (cursor.moveToNext()) {
			
			result= cursor.getDouble(0);
					
		}
		return result;
		
	}



    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
	}

}
