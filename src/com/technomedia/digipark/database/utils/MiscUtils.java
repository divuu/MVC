package com.technomedia.digipark.database.utils;

public class MiscUtils {

	public static boolean checkNumeric( String data ) {

		if( data == null || data.length() == 0 )
			return false;

		for( int i = 0; i < data.length(); i++ ) {
			
			if( data.charAt(i) >= '0' && data.charAt(i) <= '9' )
				continue;
			else
				return false;
		}
		
		return true;
	}
}
