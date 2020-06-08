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

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cufy.beans.AbstractBean;
import cufy.io.loadable.FileLoadable;
import cufy.io.loadable.FormatLoadable;
import cufy.text.Format;
import cufy.text.json.JSON;
import cufyx.perference.MapDataStore;
import lsafer.edgeseek.util.Position;
import lsafer.edgeseek.util.Util;

/**
 * The data of the hole application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 26-May-20
 */
final public class AppData extends AbstractBean implements FileLoadable, FormatLoadable {
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String ACTIVATED = "activated";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String AUTO_BOOT = "auto_boot";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String AUTO_BRIGHTNESS = "auto_brightness";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String EDGES = "edges";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String THEME = "theme";

	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String FACTOR_BOTTOM = "factorBottom";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String FACTOR_LEFT = "factorLeft";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String FACTOR_TOP = "factorTop";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String FACTOR_RIGHT = "factorRight";

	/**
	 * A string with the names of the factor fields.
	 * <ul>
	 *     <li>{@link #factorBottom}</li>
	 *     <li>{@link #factorLeft}</li>
	 *     <li>{@link #factorTop}</li>
	 *     <li>{@link #factorRight}</li>
	 * </ul>
	 */
	final public static String[] FACTORS = {FACTOR_BOTTOM, FACTOR_LEFT, FACTOR_TOP, FACTOR_RIGHT};

	/**
	 * The data of the permissions of this application.
	 * <br>
	 * Note: this data isn't stored in a file.
	 */
	final public PermissionsData permissions;
	/**
	 * The store to be used on preference screens/fragments.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * The activation status of this application.
	 */
	@Property
	public boolean activated = false;
	/**
	 * Launch the service on-start.
	 */
	@Property
	public boolean auto_boot = true;
	/**
	 * Turn on auto-brightness on screen-off.
	 */
	@Property
	public boolean auto_brightness = true;
	/**
	 * The data of the edges. Limited to 4 representing the 4 edges of the screen.
	 */
	@Property
	public List<EdgeData> edges = Util.fill(new ArrayList<>(), 0, 39, EdgeData::new);
	/**
	 * The left side factor.
	 */
	@Property
	public int factorLeft = 0;
	/**
	 * The right side factor.
	 */
	@Property
	public int factorRight = 0;
	/**
	 * The top side factor.
	 */
	@Property
	public int factorTop = 0;
	/**
	 * The bottom side factor.
	 */
	@Property
	public int factorBottom = 0;
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
	 * @param context to be used
	 * @param file    to read-from/write-to
	 */
	public AppData(Context context, File file) {
		this.file = file;
		this.permissions = new PermissionsData(context);
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

	/**
	 * Get the factor used at the given side in this app-data.
	 *
	 * @param side to get the factor used at it
	 * @return the factor used at the given side
	 */
	public int getFactor(int side) {
		switch (side) {
			case Position.BOTTOM:
				return this.factorBottom;
			case Position.LEFT:
				return this.factorLeft;
			case Position.TOP:
				return this.factorTop;
			case Position.RIGHT:
				return this.factorRight;
			default:
				throw new IllegalArgumentException("Unexpected side: " + side);
		}
	}
}
