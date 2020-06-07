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
import java.util.Arrays;
import java.util.List;

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
final public class AppData extends AbstractBean implements FileLoadable, FormatLoadable {
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
}
