package sounding;

import java.util.Calendar;
import java.util.TimeZone;

import constant.Constant;
import data.AWSGetSounding;

/**
 * Get Sounding Batch
 */
public class GetSounding {

	/**
	 * main method to register sounding data (batch)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String getYear = "";
			String getMonth = "";
			String getDateHour = "";

			// use date information if specified
			if (args.length > 0) {
				if (args.length != 3) {
					System.out.println("Usage: program YEAR MONTH DATEHOUR");
					return;
				}
				else {
					getYear = args[0];
					getMonth = args[1];
					getDateHour = args[2];
				}
			}
			else {
				//-----------------------------------------------------
				// Get Latest Sounding Data (from Wyoming University)
				//-----------------------------------------------------
				TimeZone tz = TimeZone.getTimeZone("Europe/London");
				Calendar cal = Calendar.getInstance();
				cal.setTimeZone(tz);
				// data is updated after 90 min
				cal.add(Calendar.MINUTE, -90);
				int intYear = cal.get(Calendar.YEAR);
				int intMonth = cal.get(Calendar.MONTH) + 1;
				int intDate = cal.get(Calendar.DATE);
				int intHour = cal.get(Calendar.HOUR_OF_DAY);

				getYear = "0000" + String.valueOf(intYear);
				getYear = getYear.substring(getYear.length() - "0000".length());

				getMonth = "00" + String.valueOf(intMonth);
				getMonth = getMonth.substring(getMonth.length() - "00".length());

				getDateHour = "00" + String.valueOf(intDate);
				getDateHour = getDateHour.substring(getDateHour.length() - "00".length());
				if(intHour < 12) {
					getDateHour += "00";
				}
				else {
					getDateHour += "12";
				}
			}

			AWSGetSounding get = new AWSGetSounding(
					Constant.REGION_SOUTH_EAST_ASIA
					,getYear
					,getMonth
					,getDateHour
					,Constant.STATION_TATENO);
			get.getSounding();

		} catch(Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
