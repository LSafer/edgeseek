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

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.StyleRes;

import lsafer.edge_seek.R;
import lsafer.edge_seek.constant.Constant;
import lsafer.io.File;
import lsafer.io.FolderMap;
import lsafer.io.JSONFileMap;
import lsafer.util.JSObject;
import lsafer.util.impl.AbstractIOJSObject;
import lsafer.util.impl.FolderHashMap;

/**
 * Application static fields.
 *
 * @author LSaferSE
 * @version 1 alpha (04-Oct-19)
 * @since 04-Oct-19
 */
@JSObject.Configurations(restricted = false)
final public class App extends AbstractIOJSObject<File, Object, Object> implements FolderMap<Object, Object> {
	/**
	 * Whether this class (static wise) is initialized or not.
	 */
	private transient static boolean initialized = false;

	/**
	 * Public resources from the first context initializes this class (static wise).
	 */
	public transient static Resources resources;

	/**
	 * The instance of this class. To avoid multiple instancing.
	 */
	private final transient static App $ = new App();

	/**
	 * The instance of {@link UI} class. To avoid multiple instancing.
	 */
	final public static UI ui = new UI();

	/**
	 * The instance of {@link Main} class. To avoid multiple instancing.
	 */
	final public static Main main = new Main();

	/**
	 * The folder where edges data get stored at.
	 */
	final public static FolderHashMap<String, ?> edges = new FolderHashMap<>(null, Edge.class);

	/**
	 * Initialize this class (static wise).
	 *
	 * @param context to initialize with
	 * @return the {@link #$ global instance} of this
	 */
	public static App init(Context context) {
		if (!initialized) {
			App.$.xremote(context.getExternalFilesDir(""));
			App.$.applyRemote();
			initialized = true;
			resources = context.getResources();
		}

		return App.$;
	}

	/**
	 * The class to use to store UI preference data.
	 */
	@JSObject.Configurations(restricted = false)
	final public static class UI extends AbstractIOJSObject<File, Object, Object> implements JSONFileMap<Object, Object> {
		/**
		 * The theme that the user prefer to use.
		 */
		public static String theme = Constant.THEME_BLACK;

		/**
		 * Get the {@link StyleRes style-id} for the current set theme.
		 *
		 * @return the style-id for the current set theme
		 */
		@StyleRes
		public static int theme(){
			switch (UI.theme) {
				case Constant.THEME_LIGHT:
					return R.style.KroovLightAppTheme;
				case Constant.THEME_BLACK:
					default:
					return R.style.KroovBlackAppTheme;
			}
		}
	}

	/**
	 * The class to use to store application main data.
	 */
	@JSObject.Configurations(restricted = false)
	final public static class Main extends AbstractIOJSObject<File, Object, Object> implements JSONFileMap<Object, Object> {
		/**
		 * Whether this application is activated or not.
		 */
		public static boolean activated = true;

		/**
		 * Whether this application should auto-start itself on the startup of the device or not.
		 */
		public static boolean boot = true;

		/**
		 * If the application should turn the auto-brightness on each time the screen went off or not.
		 */
		public static boolean auto_brightness = true;
	}
}
