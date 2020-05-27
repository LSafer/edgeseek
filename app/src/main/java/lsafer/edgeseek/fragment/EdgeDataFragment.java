/*
 *	Copyright 2020 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package lsafer.edgeseek.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import lsafer.edgeseek.R;
import lsafer.edgeseek.data.EdgeData;

/**
 * @author lsafer
 * @since 27-May-20
 */
@SuppressWarnings("JavaDoc")
public class EdgeDataFragment extends PreferenceFragmentCompat {
	/**
	 * The data to be read-from/written-to by this.
	 */
	private EdgeData data;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		if (this.getActivity() instanceof Activity)
			this.data = ((Activity) this.getActivity()).getEdgeData(this);
		else throw new IllegalArgumentException("Illegal Owner, not implementing EdgeDataFragment.Activity");

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		this.getPreferenceManager().setPreferenceDataStore(this.data.store);
		this.setPreferencesFromResource(R.xml.pref_edge, rootKey);
	}

	/**
	 * An interface that the activity uses this fragment should implement.
	 */
	public interface Activity {
		/**
		 * Returns the edge-data instance that should be used by the fragment.
		 *
		 * @param fragment the fragment that have requested the data
		 * @return the edge-data instance of the
		 * @throws NullPointerException if the given fragment is null
		 */
		EdgeData getEdgeData(EdgeDataFragment fragment);
	}
}
