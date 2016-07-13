package com.demo.license.mask;


import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Utils {
	public static void showLicenseListMask(FragmentActivity activity) {
		int orientation = activity.getResources()
		                          .getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			LicencesFragment.newInstance(activity.getApplication())
			                .show(activity.getSupportFragmentManager(), null);
		} else {
			LicencesActivity.showInstance(activity);
		}
	}

	public static boolean isReadableFile(String path) {
		File testFile = new File(path);
		return testFile == null?false:testFile.exists() && testFile.isFile() && testFile.canRead();
	}

	public static File getExternalCacheDir(Context appContext) {
		return appContext.getExternalCacheDir();
	}

	public static File getCacheDir(Context appContext) {
		return appContext.getCacheDir();
	}

	public static void deleteRecursive(File d) {
		if(d != null && d.exists()) {
			if(d.isDirectory()) {
				File[] arr$ = d.listFiles();
				int len$ = arr$.length;

				for(int i$ = 0; i$ < len$; ++i$) {
					File child = arr$[i$];
					deleteRecursive(child);
				}
			}

			d.delete();
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
