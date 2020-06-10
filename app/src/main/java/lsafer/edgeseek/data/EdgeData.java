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
import cufy.beans.AbstractBean;
import cufyx.perference.MapDataStore;
import lsafer.edgeseek.tasks.OnLongClickExpandStatusBar;
import lsafer.edgeseek.tasks.OnTouchAudioControl;
import lsafer.edgeseek.tasks.OnTouchBrightnessControl;
import lsafer.edgeseek.util.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * A structure holding the data of an edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 26-May-20
 */
final public class EdgeData extends AbstractBean {
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String ACTIVATED = "activated";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String BLACK_LIST = "blackList";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String COLOR = "color";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String LONG_CLICK = "longClick";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String ROTATE = "rotate";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String SEEK = "seek";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String SENSITIVITY = "sensitivity";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String TOAST = "toast";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String VIBRATION = "vibration";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String WIDTH = "width";

	/**
	 * The split factor used on this edge.
	 */
	final public int factor;
	/**
	 * The position of this edge.
	 * <pre>
	 *     ---
	 *     00: bottom
	 *     01: left
	 *     02: top
	 *     03: right
	 *     ---
	 *     04: bottom-right
	 *     05: left-bottom
	 *     06: top-left
	 *     07: right-top
	 *     ---
	 *     08: bottom-left
	 *     09: left-top
	 *     10: top-right
	 *     11: right-bottom
	 *     ---
	 * </pre>
	 */
	final public int position;
	/**
	 * The side of this edge.
	 */
	final public int side;
	/**
	 * The data store for this bean.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * Whether this edge is activated or not.
	 */
	@Property
	public boolean activated;
	/**
	 * A set of application names for this edge to not run on.
	 */
	@Property
	public Set<String> blackList = new HashSet<>();
	/**
	 * The color of the edge.
	 */
	@Property
	public int color = Color.argb(0, 255, 0, 0);
	/**
	 * The task to be performed on long click.
	 */
	@Property
	public String longClick = "";
	/**
	 * False makes the edge remain on the same position even if the screen changed its position.
	 */
	@Property
	public boolean rotate = false;
	/**
	 * What to-do when seeking.
	 */
	@Property
	public String seek = "";
	/**
	 * The sensitivity of this edge.
	 */
	@Property
	public int sensitivity = 45;
	/**
	 * Show a toast with the current volume-value when seeking.
	 */
	@Property
	public boolean toast = true;
	/**
	 * The vibration once the edge get touched.
	 */
	@Property
	public int vibration = 1;
	/**
	 * The width of this edge.
	 */
	@Property
	public int width = 35;

	/**
	 * Construct a new edge data for the edge in the given position.
	 *
	 * @param position the position of the edge.
	 * @throws IllegalArgumentException if hte given 'position' is unknown.
	 */
	public EdgeData(int position) {
		if (position < 0 || position >= Position.edge.ARRAY.length)
			throw new IllegalArgumentException("position out of range [0, " + (Position.edge.ARRAY.length - 1) + "]");
		this.position = position;
		this.factor = Position.factor.ofPosition(position);
		this.side = Position.side.ofPosition(position);

		//---- default values depending on position
		switch (position) {
			case Position.BOTTOM:
				this.activated = true;
				this.longClick = OnLongClickExpandStatusBar.TASK;
				this.width = 30;
				this.color = Color.argb(0, 50, 50, 150);
				break;
			case Position.LEFT:
				this.activated = true;
				this.seek = OnTouchAudioControl.TASK_MUSIC;
				this.color = Color.argb(0, 150, 50, 50);
				break;
			case Position.RIGHT:
				this.activated = true;
				this.seek = OnTouchBrightnessControl.TASK;
				this.color = Color.argb(0, 150, 50, 50);
				break;
		}
	}
}
