////////////////////////////////////////////////////////////////////////////

// FileName Currency.java: Currency Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


////////////////////////////////////////////////////////////////////////////


package com.technomedia.digipark.database;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import java.sql.Timestamp;
import com.technomedia.logger.MLogger;


public class Currency  {

	//public static final String CREATE_CURRENCY_TABLE = "CREATE TABLE currency (currency_id INTEGER, currency_symbol TEXT, currency_word TEXT, currency_decimal TEXT, conversion_rate_with_usd REAL)";

	public final static String DATABASE_TABLE_NAME = "currency";


//MySQL Script
	//CREATE TABLE currency( currency_id int, currency_symbol varchar(8), currency_word varchar(16), currency_decimal varchar(16), conversion_rate_with_usd float );
	public static final String KEY_CURRENCY_ID = "currency_id";
	public static final String KEY_CURRENCY_SYMBOL = "currency_symbol";
	public static final String KEY_CURRENCY_WORD = "currency_word";
	public static final String KEY_CURRENCY_DECIMAL = "currency_decimal";
	public static final String KEY_CONVERSION_RATE_WITH_USD = "conversion_rate_with_usd";

	public static final String LABEL_CURRENCY_ID = "ID";//"Currency Id";
	public static final String LABEL_CURRENCY_SYMBOL = "Currency Symbol";
	public static final String LABEL_CURRENCY_WORD = "Currency Word";
	public static final String LABEL_CURRENCY_DECIMAL = "Currency Decimal";
	public static final String LABEL_CONVERSION_RATE_WITH_USD = "Conversion Rate With Usd";

	public static final int INDEX_CURRENCY_ID = 0;
	public static final int INDEX_CURRENCY_SYMBOL = 1;
	public static final int INDEX_CURRENCY_WORD = 2;
	public static final int INDEX_CURRENCY_DECIMAL = 3;
	public static final int INDEX_CONVERSION_RATE_WITH_USD = 4;

	public final static int NUM_OF_COLUMNS = 5;

	public int mCurrencyId;		//Unique currency ID
	public String mCurrencySymbol;		//Rs. or USD
	public String mCurrencyWord;		//Rupees, US Dollar
	public String mCurrencyDecimal;		//Paise, Cents
	public float mConversionRateWithUsd;

	public Currency() {
		mCurrencyId = 0;
		mCurrencySymbol = null;
		mCurrencyWord = null;
		mCurrencyDecimal = null;
		mConversionRateWithUsd = 0;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "currency_id " + mCurrencyId );
		MLogger.i( MLogger.MOD_DB, "currency_symbol " + mCurrencySymbol );
		MLogger.i( MLogger.MOD_DB, "currency_word " + mCurrencyWord );
		MLogger.i( MLogger.MOD_DB, "currency_decimal " + mCurrencyDecimal );
		MLogger.i( MLogger.MOD_DB, "conversion_rate_with_usd " + mConversionRateWithUsd );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_CURRENCY_ID);
		tableHeader.add(LABEL_CURRENCY_SYMBOL);
		tableHeader.add(LABEL_CURRENCY_WORD);
		tableHeader.add(LABEL_CURRENCY_DECIMAL);
		tableHeader.add(LABEL_CONVERSION_RATE_WITH_USD);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Currency> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Currency obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mCurrencyId));
			strList.put(obj.mCurrencySymbol == null?"":obj.mCurrencySymbol);
			strList.put(obj.mCurrencyWord == null?"":obj.mCurrencyWord);
			strList.put(obj.mCurrencyDecimal == null?"":obj.mCurrencyDecimal);
			strList.put(String.valueOf(obj.mConversionRateWithUsd));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "CrncyD", "CrncySymbl", "CrncyWrd", "CrncyDcml", "CnvrsnRtWthSd"};
		String[] keys = { "mCurrencyId", "mCurrencySymbol", "mCurrencyWord", "mCurrencyDecimal", "mConversionRateWithUsd"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Currency> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Currency obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Currency> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Currency obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mCurrencyId));
			strList.add(obj.mCurrencySymbol);
			strList.add(obj.mCurrencyWord);
			strList.add(obj.mCurrencyDecimal);
			strList.add(String.valueOf(obj.mConversionRateWithUsd));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Currency obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mCurrencyId));
		strList.add(obj.mCurrencySymbol);
		strList.add(obj.mCurrencyWord);
		strList.add(obj.mCurrencyDecimal);
		strList.add(String.valueOf(obj.mConversionRateWithUsd));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Currency > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Currency obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mCurrencyId));
			strList.add(String.valueOf(obj.mCurrencySymbol));
			strList.add(String.valueOf(obj.mCurrencyWord));
			strList.add(String.valueOf(obj.mCurrencyDecimal));
			strList.add(String.valueOf(obj.mConversionRateWithUsd));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Currency obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mCurrencyId));
		strList.add(String.valueOf(obj.mCurrencySymbol));
		strList.add(String.valueOf(obj.mCurrencyWord));
		strList.add(String.valueOf(obj.mCurrencyDecimal));
		strList.add(String.valueOf(obj.mConversionRateWithUsd));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Currency currency) {
		JSONObject jsonObj = new JSONObject();
		String[] currencyLabels = getmKeys();
		try {
			jsonObj.put(currencyLabels[ INDEX_CURRENCY_ID], currency.mCurrencyId );
			jsonObj.put(currencyLabels[ INDEX_CURRENCY_SYMBOL], currency.mCurrencySymbol );
			jsonObj.put(currencyLabels[ INDEX_CURRENCY_WORD], currency.mCurrencyWord );
			jsonObj.put(currencyLabels[ INDEX_CURRENCY_DECIMAL], currency.mCurrencyDecimal );
			jsonObj.put(currencyLabels[ INDEX_CONVERSION_RATE_WITH_USD], currency.mConversionRateWithUsd );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Currency jsonObjectToObject( String jsonObjectString ) {
		Currency currency = new Currency();
		JSONObject data;
		String[] currencyLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			currency.mCurrencyId = data.getInt(currencyLabels[INDEX_CURRENCY_ID]);
			currency.mCurrencySymbol = data.getString(currencyLabels[INDEX_CURRENCY_SYMBOL]);
			currency.mCurrencyWord = data.getString(currencyLabels[INDEX_CURRENCY_WORD]);
			currency.mCurrencyDecimal = data.getString(currencyLabels[INDEX_CURRENCY_DECIMAL]);
			currency.mConversionRateWithUsd = (float)data.getDouble(currencyLabels[INDEX_CONVERSION_RATE_WITH_USD]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return currency;

	}

	public static ArrayList<Currency> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Currency> objList = new ArrayList<Currency>();
		try {

			JSONArray currencyJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < currencyJSONArray.length(); i++) {

				JSONObject currencyJsonObject = currencyJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(currencyJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
