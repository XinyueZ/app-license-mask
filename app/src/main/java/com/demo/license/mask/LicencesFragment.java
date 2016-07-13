package com.demo.license.mask;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.demo.license.mask.databinding.LicencesBinding;
import com.demo.license.mask.ds.Library;
import com.demo.license.mask.ds.Licence;
import com.demo.license.mask.ds.Licences;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import kotlin.NotImplementedError;


public final class LicencesFragment extends AppCompatDialogFragment{
	public static final String TAG = LicencesFragment.class.getName();
	private static final int LAYOUT = R.layout.fragment_licences;
	private static final int ID_LOAD_LICENCES_TASK = 0x54;
	private static final String LICENCES_LIST_JSON = "licences-list.json";
	private LicencesBinding mBinding;
	private Gson mGson = new Gson();

	//------------------------------------------------
	//Subscribes, event-handlers
	//------------------------------------------------

	/**
	 * Handler for {@link CloseExpandableListGroupEvent}.
	 * @param e Event {@link CloseExpandableListGroupEvent}.
	 */
	@Subscribe
	public void onEvent(CloseExpandableListGroupEvent e) {
		mBinding.licencesList.collapseGroup(e.getGroupIndex());
	}
	//------------------------------------------------

	public static LicencesFragment newInstance(Context cxt) {
		return (LicencesFragment) instantiate(cxt, LicencesFragment.class.getName());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(LAYOUT, container, getShowsDialog());
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if(context instanceof Activity) {
			((Activity)context).setTitle(R.string.licences_title);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mBinding = DataBindingUtil.bind(view.findViewById(R.id.licences_fragment_root));
		loadLicences();
	}

	private void loadLicences() {
		getLoaderManager().initLoader(ID_LOAD_LICENCES_TASK, Bundle.EMPTY, new LoaderManager.LoaderCallbacks<Licences>() {
			@Override
			public Loader<Licences> onCreateLoader(int id, Bundle args) {
				return new AsyncTaskLoader<Licences>(getContext()) {
					@Override
					public Licences loadInBackground() {
						Licences licences;
						try {
							licences = mGson.fromJson(new InputStreamReader(getContext().getAssets()
							                                                            .open(LICENCES_LIST_JSON)), Licences.class);
						} catch (IOException e) {
							licences = null;
						}
						return licences;
					}
				};
			}

			@Override
			public void onLoadFinished(Loader<Licences> loader, Licences licences) {
				if (licences != null) {
					ExpandableListView expListView = mBinding.licencesList;
					if (expListView != null) {
						expListView.setAdapter(new LicencesListAdapter(licences));
					}
				}
			}

			@Override
			public void onLoaderReset(Loader<Licences> loader) {

			}
		}).forceLoad();
	}


	private static final class LicencesListAdapter extends BaseExpandableListAdapter {
		private static final String LICENCES_BOX = "licences-box";
		private static final String COPYRIGHT_HOLDERS = "<copyright holders>";
		private static final String YEAR = "<year>";
		private static final String LICENCE_BOX_LOCATION_FORMAT = "%s/%s.txt";
		private static final int LAYOUT_GROUP = R.layout.list_licences_item_group;
		private static final int LAYOUT_CHILD = R.layout.list_licences_item_child;
		private final Licences mLicences;
		private final int mLicencesCount;
		private final ArrayMap<Library, Pair<String, String>> mLibraryList = new ArrayMap<>();//Pair contains licence's name and description.
		private final ArrayMap<String, String> mLicenceContentList = new ArrayMap<>();

		public LicencesListAdapter(Licences licences) {
			mLicences = licences;
			List<Licence> licencesList = mLicences.getLicences();
			for (Licence licence : licencesList) {
				List<Library> libraries = licence.getLibraries();
				for (Library library : libraries) {
					mLibraryList.put(library, new Pair<>(licence.getName(), licence.getDescription()));
				}
			}
			mLicencesCount = mLibraryList.size();
		}

		public int getGroupCount() {
			return mLicencesCount;
		}

		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		public Object getGroup(int groupPosition) {
			throw new NotImplementedError("This adapter doesn't need group.");
		}

		public Object getChild(int groupPosition, int childPosition) {
			throw new NotImplementedError("This adapter doesn't need children.");
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition * childPosition;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}


		public boolean hasStableIds() {
			return true;
		}

		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			GroupHolder holder;
			if (convertView == null) {
				ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), LAYOUT_GROUP, parent, false);
				holder = new GroupHolder(binding);
				convertView = binding.getRoot();
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}
			Library library = mLibraryList.keyAt(groupPosition);
			Pair<String, String> nameDesc = mLibraryList.get(library);

			holder.mBinding.setVariable(com.demo.license.mask.BR.title, library.getName());
			holder.mBinding.setVariable(com.demo.license.mask.BR.description, nameDesc.second);
			holder.mBinding.executePendingBindings();
			return convertView;
		}

		public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			Context cxt = parent.getContext();
			ChildHolder holder;
			if (convertView == null) {
				ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), LAYOUT_CHILD, parent, false);
				holder = new ChildHolder(binding);
				convertView = binding.getRoot();
				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}

			String content;
			Library library = mLibraryList.keyAt(groupPosition);
			Pair<String, String> nameDesc = mLibraryList.get(library);
			//Licence content will be read from disk firstly and from memory next time.
			if (mLicenceContentList.get(nameDesc.first) == null) {
				content = loadLicencesContent(cxt, nameDesc.first);
				mLicenceContentList.put(nameDesc.first, content);
			} else {
				content = mLicenceContentList.get(nameDesc.first);
			}

			if (content.contains(YEAR) && content.contains(COPYRIGHT_HOLDERS)) {
				content = content.replace(YEAR, TextUtils.isEmpty(library.getCopyright()) ?
				                                "" :
				                                library.getCopyright())
				                 .replace(COPYRIGHT_HOLDERS, TextUtils.isEmpty(library.getOwner()) ?
				                                             "" :
				                                             library.getOwner());
			}
			holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					EventBus.getDefault().post(new CloseExpandableListGroupEvent(groupPosition));
				}
			});
			holder.mBinding.setVariable(com.demo.license.mask.BR.content, content);
			holder.mBinding.executePendingBindings();
			return convertView;
		}


		private static String loadLicencesContent(Context cxt, @NonNull final String licenceName) {
			String licenceLocation = String.format(LICENCE_BOX_LOCATION_FORMAT, LICENCES_BOX, licenceName);
			String licenceContent;
			try {
				licenceContent = Utils.readTextFile(cxt.getAssets()
				                                                    .open(licenceLocation));
			} catch (IOException e) {
				licenceContent = null;
			}
			return licenceContent;
		}

		private static final class GroupHolder {
			private ViewDataBinding mBinding;
			public GroupHolder(ViewDataBinding binding) {
				mBinding = binding;
			}
		}

		private final class ChildHolder {
			private ViewDataBinding mBinding;
			public ChildHolder(ViewDataBinding binding) {
				mBinding = binding;
			}
		}
	}
}
