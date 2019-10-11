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
package lsafer.edge_seek.constant;


/**
 * Preference constant values.
 *
 * @author LSaferSE
 * @version 1 (alpha 11-Oct-2019)
 * @since 11-Oct-2019
 */
final public class Constant {
	/**
	 * A section that contains misc preferences.
	 */
	final public static int SECTION_MISC = 0;
	/**
	 * A section that contains advanced preferences.
	 */
	final public static int SECTION_ADVANCED = 1;
	/**
	 * A section that contains appearance and/or style preferences.
	 */
	final public static int SECTION_APPEARANCE = 2;
	/**
	 * A section that contains actions preferences.
	 */
	final public static int SECTION_ACTIONS = 3;
	/**
	 * A section that contains custom preference values.
	 */
	final public static int SECTION_CUSTOM = 4;
	/**
	 * A section that should be hidden.
	 */
	final public static int SECTION_HIDDEN = 5;

	/**
	 * A theme that have all dark colors. And a black background.
	 */
	final public static String THEME_BLACK = "black";
	/**
	 * A theme that have all light colors. And a light background.
	 */
	public static final String THEME_LIGHT = "light";

	/**
	 * A task controls brightness when invoked.
	 */
	final public static String TASK_CONTROL_BRIGHTNESS = "control brightness";
	/**
	 * A task controls audio when invoked.
	 */
	final public static String TASK_CONTROL_AUDIO = "control audio";
	/**
	 * A task expand statusbar when invoked.
	 */
	final public static String TASK_EXPAND_STATUSBAR = "expand statusbar";
	/**
	 * A task do nothing when invoked.
	 */
	final public static String TASK_NOTHING = "";
}
