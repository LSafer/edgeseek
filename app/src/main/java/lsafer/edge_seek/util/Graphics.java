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
package lsafer.edge_seek.util;

import android.annotation.SuppressLint;
import android.view.Gravity;

/**
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
@SuppressWarnings({"unused", "WeakerAccess"})
final public class Graphics {
	/**
	 * @return
	 */
	@SuppressLint("RtlHardcoded")
	public static int gravity(int position) {
		switch (position) {
			case 0:
				return Gravity.BOTTOM;
			case 1:
				return Gravity.LEFT;
			case 2:
				return Gravity.TOP;
			case 3:
				return Gravity.RIGHT;
			default:
				return gravity(Math.abs(position) % 4);
		}
	}
}
