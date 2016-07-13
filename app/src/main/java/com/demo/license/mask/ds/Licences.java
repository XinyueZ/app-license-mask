package com.demo.license.mask.ds;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Licences {
	@SerializedName("licences") private List<Licence> mLicences;

	public List<Licence> getLicences() {
		return mLicences;
	}

	public void setLicences(List<Licence> licences) {
		mLicences = licences;
	}
}
