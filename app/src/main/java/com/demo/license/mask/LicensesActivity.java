package com.demo.license.mask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public final class LicensesActivity extends AppCompatActivity {

	public static void showInstance(Activity cxt) {
		Intent intent = new Intent(cxt, LicensesActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_licences);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		LicensesFragment dialogFragment = LicensesFragment.newInstance(getApplication());
		if (dialogFragment != null) {
			getSupportFragmentManager().beginTransaction()
			                           .replace(R.id.licences_fragment_container, dialogFragment, LicensesFragment.TAG)
			                           .commit();
		}
	}

}
