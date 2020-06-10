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
import androidx.annotation.StyleRes;
import cufy.beans.AbstractBean;
import cufy.io.loadable.FileLoadable;
import cufy.io.loadable.FormatLoadable;
import cufy.text.Format;
import cufy.text.json.JSON;
import cufyx.perference.MapDataStore;
import lsafer.edgeseek.R;
import lsafer.edgeseek.util.Position;
import lsafer.edgeseek.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	final public static String SIDES = "sides";

	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String THEME = "theme";
	/**
	 * The value for the black-theme.
	 */
	final public static String THEME_BLACK = "black";
	/**
	 * The value for the dark-theme.
	 */
	final public static String THEME_DARK = "dark";
	/**
	 * The value for the light-theme.
	 */
	final public static String THEME_LIGHT = "light";
	/**
	 * The value for the white-theme.
	 */
	final public static String THEME_WHITE = "white";

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
	 * The data of the edges.
	 */
	@Property
	public List<EdgeData> edges = Util.fill(new ArrayList<>(), Position.edge.ARRAY.length, EdgeData::new);
	/**
	 * The data of the sides. Limited to 4 representing the 4 sides of the screen.
	 */
	@Property
	public List<SideData> sides = Util.fill(new ArrayList<>(), Position.side.ARRAY.length, SideData::new);

	/**
	 * The theme of the application.
	 */
	@Property
	public String theme = AppData.THEME_LIGHT;

	//------- defaults

	/**
	 * The file this loadable is loading-from/saving-to.
	 */
	private File file;

	/**
	 * Construct a new app-data.
	 *
	 * @param context to be used.
	 * @param file    to read-from/write-to.
	 * @throws NullPointerException if the given 'context' or 'file' is null.
	 */
	public AppData(Context context, File file) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(file, "file");
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

			//if it got removed by the parser
			this.put(AppData.EDGES, this.edges);
			this.put(AppData.SIDES, this.sides);
			for (EdgeData edge : this.edges)
				edge.put(EdgeData.BLACK_LIST, edge.blackList);
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

	//--------- end defaults

	/**
	 * Get the theme resources id for the theme set in this.
	 *
	 * @return the theme resources-id for this app-data theme.
	 */
	@StyleRes
	public int getTheme() {
		switch (this.theme) {
			default:
			case THEME_BLACK:
				return R.style.Black;
			case THEME_DARK:
				return R.style.Dark;
			case THEME_LIGHT:
				return R.style.Light;
			case THEME_WHITE:
				return R.style.White;
		}
	}
}
