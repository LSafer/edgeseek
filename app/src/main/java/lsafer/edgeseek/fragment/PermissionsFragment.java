package lsafer.edgeseek.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;

import lsafer.edgeseek.R;

/**
 * @author lsafer
 * @since 02-Jun-20
 */
public class PermissionsFragment extends PreferenceFragmentCompat {
	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		this.getPreferenceManager().setPreferenceDataStore(new PreferenceDataStore() {
			/**
			 * The application context.
			 */
			private Context context = PermissionsFragment.this.getActivity().getApplicationContext();

			@Override
			public void putBoolean(String key, boolean value) {
				switch (key) {
					case "display_over_other_apps":
						Intent i0 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
						i0.setData(Uri.parse("package:" + this.context.getPackageName()));
						i0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						this.context.startActivity(i0);
						break;
					case "write_system_settings":
						Intent i1 = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
						i1.setData(Uri.parse("package:" + this.context.getPackageName()));
						i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						this.context.startActivity(i1);
						break;
					case "ignore_battery_optimization":
						Intent i2 = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
						i2.setData(Uri.parse("package:" + this.context.getPackageName()));
						i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						this.context.startActivity(i2);
						break;
				}
			}

			@Override
			public boolean getBoolean(String key, boolean defValue) {
				switch (key) {
					case "display_over_other_apps":
						return Settings.canDrawOverlays(this.context);
					case "write_system_settings":
						return Settings.System.canWrite(this.context);
					case "ignore_battery_optimization":
						return this.context.getSystemService(PowerManager.class).isIgnoringBatteryOptimizations(this.context.getPackageName());
					default:
						return false;
				}
			}
		});
		this.setPreferencesFromResource(R.xml.pref_permissions, rootKey);
	}
}
