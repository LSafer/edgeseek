package lsafer.edgeseek.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import lsafer.edgeseek.R;

/**
 * A fragment navigates the user to where to grant the application's permissions.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 02-Jun-20
 */
public class AboutFragment extends PreferenceFragmentCompat {
	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		this.setPreferencesFromResource(R.xml.fragment_about, rootKey);
	}
}
