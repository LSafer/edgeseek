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

import android.graphics.Color;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import cufy.beans.AbstractBean;
import cufyx.perference.MapDataStore;

/**
 * A structure holding the data of an edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 26-May-20
 */
final public class EdgeData extends AbstractBean {
	/**
	 * The position of this edge.
	 */
	final public int position;
	/**
	 * The data store for this bean.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * The listeners to be called when data changed.
	 */
	final private Collection<OnDataChangeListener> listeners = new HashSet<>();

	/**
	 * Whether this edge is activated or not.
	 */
	@Property
	public boolean activated;
	/**
	 * The color of the edge.
	 */
	@Property
	public int color = Color.argb(0, 0, 0, 0);
	/**
	 * False makes the edge remain on the same position even if the screen changed its position.
	 */
	@Property
	public boolean rotate = false;
	/**
	 * What to do when seeking.
	 */
	@Property
	public String seek = "brightness";
	/**
	 * The sensitivity of this edge.
	 */
	@Property
	public int sensitivity = 70;
	/**
	 * The width of this edge.
	 */
	@Property
	public int width = 20;

	/**
	 * Construct a new edge data for the edge in the given position.
	 *
	 * @param position the position of the edge
	 * @throws IllegalArgumentException if hte given 'position' is not within the range [0, 3]
	 */
	public EdgeData(int position) {
		if (position < 0 || position > 3)
			throw new IllegalArgumentException("position out of range [0, 3]");
		this.position = position;

		//---- default values
		this.activated = position == 1 || position == 3;
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
	 * A listener that listens to the change in an edge-data instance.
	 */
	public interface OnDataChangeListener {
		/**
		 * Get called when a change in the data occurred in the target edge-data.
		 *
		 * @param data     the data-instance that the change occurred on
		 * @param key      the key
		 * @param oldValue the old value
		 * @param newValue the new value
		 */
		void onDataChange(EdgeData data, Object key, Object oldValue, Object newValue);
	}
}
