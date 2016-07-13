package com.demo.license.mask.ds;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Licenses {
	@SerializedName("Licenses") private List<License> mLicenses;

	public List<License> getLicenses() {
		return mLicenses;
	}

	public void setLicenses(List<License> licenses) {
		mLicenses = licenses;
	}
}
