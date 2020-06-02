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
package lsafer.edgeseek.data;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import cufy.beans.AbstractBean;
import cufy.io.loadable.FileLoadable;
import cufy.io.loadable.FormatLoadable;
import cufy.text.Format;
import cufy.text.json.JSON;
import cufyx.perference.MapDataStore;

/**
 * The data of the hole application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 26-May-20
 */
final public class AppData extends AbstractBean implements FileLoadable, FormatLoadable, EdgeData.OnDataChangeListener {
	/**
	 * The store to be used on preference screens/fragments.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * The listeners to be called when data changed.
	 */
	final private Collection<OnDataChangeListener> listeners = new HashSet<>();

	/**
	 * The activation status of this application.
	 */
	@Property
	public boolean activated = true;
	/**
	 * Launch the service on-start.
	 */
	@Property
	public boolean auto_boot = true;
	/**
	 * Turn on auto-brightness on screen-off.
	 */
	@Property
	public boolean auto_brightness = false;
	/**
	 * The data of the edges. Limited to 4 representing the 4 edges of the screen.
	 */
	@Property
	public List<EdgeData> edges = Arrays.asList(
			new EdgeData(0),
			new EdgeData(1),
			new EdgeData(2),
			new EdgeData(3)
	);
	/**
	 * The theme of the application.
	 */
	@Property
	public String theme = "black";

	/**
	 * The file this loadable is loading-from/saving-to.
	 */
	private File file;

	/**
	 * Construct a new app-data.
	 *
	 * @param file to read-from/write-to
	 */
	public AppData(File file) {
		this.file = file;

		//---
		this.edges.forEach(e -> e.registerOnDataChangeListener(this));
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public Format getFormat() {
		return JSON.global;
	}

	@Override
	public void load() {
		try {
			FormatLoadable.super.load();
			this.put("edges", this.edges);
		} catch (IOException e) {
			Log.e("MainData", "load: ", e);
		}
	}

	@Override
	public void save() {
		try {
			FormatLoadable.super.save();
		} catch (IOException e) {
			Log.e("MainData", "save: ", e);
		}
	}

	@Override
	public void onDataChange(EdgeData data, Object key, Object oldValue, Object newValue) {
		this.listeners.forEach(l -> l.onDataChange(this, key, oldValue, newValue));
	}

	@Override
	public Object put(Object key, Object value) {
		Object old = super.put(key, value);
		this.listeners.forEach(l -> l.onDataChange(this, key, old, value));
		return old;
	}

	/**
	 * Register a listener to be called when data changed in this bean.
	 *
	 * @param listener to be registered
	 * @throws NullPointerException     if the given 'listener' is null
	 * @throws IllegalArgumentException if the given listener is already registered
	 */
	public void registerOnDataChangeListener(OnDataChangeListener listener) {
		Objects.requireNonNull(listener, "consumer");
		if (this.listeners.contains(listener))
			throw new IllegalArgumentException("listener already registered");

		this.listeners.add(listener);
	}

	/**
	 * Unregister the given listener from the listeners-list.
	 *
	 * @param listener to be unregistered
	 * @throws NullPointerException     if the given 'listener' is null
	 * @throws IllegalArgumentException if the given listener not registered
	 */
	public void unregisterOnDataChangeListener(OnDataChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		if (!this.listeners.contains(listener))
			throw new IllegalArgumentException("listener not registered");

		this.listeners.remove(listener);
	}

	/**
	 * A listener that listens to the change in an app-data instance.
	 */
	public interface OnDataChangeListener {
		/**
		 * Get called when a change in the data occurred in the target app-data.
		 *
		 * @param data     the data-instance that the change occurred on
		 * @param key      the key changed
		 * @param oldValue the old value
		 * @param newValue the new value
		 */
		void onDataChange(AppData data, Object key, Object oldValue, Object newValue);
	}
}
