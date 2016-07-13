package com.demo.license.mask;


import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Utils {
	public static void showLicenseListMask(FragmentActivity activity) {
		int orientation = activity.getResources()
		                          .getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			LicensesFragment.newInstance(activity.getApplication())
			                .show(activity.getSupportFragmentManager(), null);
		} else {
			LicensesActivity.showInstance(activity);
		}
	}


	public static String readTextFile(InputStream inputStream) {
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		StringBuilder text = new StringBuilder();

		String line;
		try {
			while((line = buffreader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException var6) {
			return null;
		}

		return text.toString();
	}
}
