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
final public class AppData extends AbstractBean implements FileLoadable, FormatLoadable {
	/**
	 * The store to be used on preference screens/fragments.
	 */
	final public MapDataStore store = new MapDataStore(this);

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
	 * The file this loadable is loading-from/saving-to.
	 */
	private File file;

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
	 * Set the file that this app-data is loading-from/saving-to. Once set can't be changed.
	 *
	 * @param file to be set
	 * @throws IllegalStateException if it has been already set in this app-data instance
	 * @throws NullPointerException  if the given file is null
	 */
	public void setFile(File file) {
		Objects.requireNonNull(file, "file");

		if (this.file != null)
			throw new IllegalStateException("file already set");

		this.file = file;
	}
}
