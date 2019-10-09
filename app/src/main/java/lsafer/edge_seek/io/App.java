/*
 * Copyright (c) 2019, LSafer, All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * -You can edit this file (except the header).
 * -If you have change anything in this file. You
 *  shall mention that this file has been edited.
 *  By adding a new header (at the bottom of this header)
 *  with the word "Editor" on top of it.
 */
package lsafer.edge_seek.io;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.StyleRes;

import lsafer.edge_seek.R;
import lsafer.io.File;
import lsafer.io.FolderMap;
import lsafer.io.JSONFileMap;
import lsafer.util.JSObject;
import lsafer.util.impl.AbstractIOJSObject;
import lsafer.util.impl.FolderHashMap;

/**
 * @author LSaferSE
 * @version 1 alpha (04-Oct-19)
 * @since 04-Oct-19
 */
@SuppressWarnings({"WeakerAccess", "javadoc", "unused"})
@JSObject.Configurations(restricted = false)
@SuppressLint({"StaticFieldLeak"})
public class App extends AbstractIOJSObject<File, Object, Object> implements FolderMap<Object, Object> {

	public transient static Resources resources;

	final public transient static App $ = new App();

	final public static UI ui = new UI();

	final public static Main main = new Main();

	final public static FolderHashMap<String, ?> edges = new FolderHashMap<>(null, Edge.class);

	public static App load(Context context) {
		App.resources = context.getResources();
		App.$.xremote(context.getExternalFilesDir(""));
		return App.$.load();
	}

	@JSObject.Configurations(restricted = false)
	public static class UI extends AbstractIOJSObject<File, Object, Object> implements JSONFileMap<Object, Object> {
		public String theme = "black";

		@StyleRes
		public int theme(){
			switch (theme) {
				case "light":
					return R.style.KroovLightAppTheme;
				case "black":
					default:
					return R.style.KroovBlackAppTheme;
			}
		}
	}

	@JSObject.Configurations(restricted = false)
	public static class Main extends AbstractIOJSObject<File, Object, Object> implements JSONFileMap<Object, Object> {
		public static boolean activated = true;
		public static boolean boot = true;
		public static boolean auto_brightness = true;
	}
}
