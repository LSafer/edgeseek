package lsafer.edgeseek.data;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cufyx.perference.MapDataStore;

/**
 * A map to simplify the permissions request and permissions get.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 07-Jun-2020
 */
final public class PermissionsData extends AbstractMap {
	/**
	 * The context to get the permissions-status of.
	 */
	final private Context context;
	/**
	 * The entry set of this map.
	 */
	final private Set<Entry> entrySet;
	/**
	 * A data store to use this map with the package {@code android.preference}.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * Construct a new permissions data.
	 *
	 * @param context the context
	 */
	public PermissionsData(Context context) {
		this.context = context;
		this.entrySet = Collections.unmodifiableSet(new HashSet(Arrays.asList(
				//android.permission.SYSTEM_ALERT_WINDOW
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.SYSTEM_ALERT_WINDOW;
					}

					@Override
					public Object getValue() {
						return Settings.canDrawOverlays(PermissionsData.this.context);
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				},
				//android.permission.WRITE_SETTINGS
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.WRITE_SETTINGS;
					}

					@Override
					public Object getValue() {
						return Settings.System.canWrite(PermissionsData.this.context);
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				},
				//android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
				new Entry() {
					@Override
					public Object getKey() {
						return Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
					}

					@Override
					public Object getValue() {
						return context.getSystemService(PowerManager.class)
								.isIgnoringBatteryOptimizations(context.getPackageName());
					}

					@Override
					public Object setValue(Object value) {
						Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
						intent.setData(Uri.parse("package:" + context.getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

						return this.getValue();
					}
				}
		)));
	}

	@Override
	public Set<Entry> entrySet() {
		return this.entrySet;
	}

	@Override
	public Object put(Object key, Object value) {
		for (Entry entry : this.entrySet)
			if (entry.getKey().equals(key))
				return entry.setValue(value);
		return null;
	}
}