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

import androidx.annotation.IdRes;

import lsafer.edge_seek.R;
import lsafer.edge_seek.io.App;

/**
 * Common utils.
 *
 * @author LSaferSE
 * @version 1 alpha (07-Oct-19)
 * @since 07-Oct-19
 */
final public class Common {
	/**
	 * Get the id-res for the given positions.
	 *
	 * @param position to get id-res for
	 * @param xposition to get id-res for
	 * @return an id-res for the given positions
	 */
	@IdRes
	public static int positionId(int position, int xposition) {
		if (xposition == 4)
			switch (position) {
				case 0:
					return R.id.bottom;
				case 1:
					return R.id.left;
				case 2:
					return R.id.top;
				case 3:
					return R.id.right;
			}
		else switch (position) {
			case 0:
				switch (xposition) {
					case 1:
						return R.id.bottom_left;
					case 3:
						return R.id.bottom_right;
				}
				break;
			case 1:
				switch (xposition) {
					case 0:
						return R.id.left_bottom;
					case 2:
						return R.id.left_top;
				}
				break;
			case 2:
				switch (xposition) {
					case 1:
						return R.id.top_left;
					case 3:
						return R.id.top_right;
				}
				break;
			case 3:
				switch (xposition) {
					case 0:
						return R.id.right_bottom;
					case 2:
						return R.id.right_top;
				}
				break;
		}
		throw new AssertionError("position: " + position + " and xposition: " + xposition + " is not expected");
	}

	/**
	 * Get the position from the given id-res.
	 *
	 * @param id to get position from
	 * @return a position from the given id-res
	 */
	public static int position(@IdRes int id) {
		switch (id) {
			case R.id.bottom:
			case R.id.bottom_left:
			case R.id.bottom_left1:
			case R.id.bottom_right:
			case R.id.bottom_right1:
				return 0;
			case R.id.left:
			case R.id.left_bottom:
			case R.id.left_bottom1:
			case R.id.left_top:
			case R.id.left_top1:
				return 1;
			case R.id.top:
			case R.id.top_left:
			case R.id.top_left1:
			case R.id.top_right:
			case R.id.top_right1:
				return 2;
			case R.id.right:
			case R.id.right_bottom:
			case R.id.right_bottom1:
			case R.id.right_top:
			case R.id.right_top1:
				return 3;
		}

		throw new RuntimeException("id: " + id + " is not expected");
	}

	/**
	 * Get the xposition from the given id-res.
	 *
	 * @param id to get position from
	 * @return an xposition from the given id-res
	 */
	public static int xposition(@IdRes int id) {
		switch (id) {
			case R.id.bottom:
			case R.id.left:
			case R.id.top:
			case R.id.right:
				return 4;
			case R.id.left_bottom:
			case R.id.left_bottom1:
			case R.id.right_bottom:
			case R.id.right_bottom1:
				return 0;
			case R.id.bottom_left:
			case R.id.bottom_left1:
			case R.id.top_left:
			case R.id.top_left1:
				return 1;
			case R.id.left_top:
			case R.id.left_top1:
			case R.id.right_top:
			case R.id.right_top1:
				return 2;
			case R.id.bottom_right:
			case R.id.bottom_right1:
			case R.id.top_right:
			case R.id.top_right1:
				return 3;
		}

		throw new RuntimeException("id: " + id + " is not expected");
	}

	/**
	 * Get the name for the given position.
	 *
	 * @param position to get the name for
	 * @return the name for the given position
	 */
	public static String positionName(int position) {
		return App.resources.getStringArray(R.array.positions)[position];
	}

	/**
	 * Get {@link Gravity} value for the given position.
	 *
	 * @param position to get the gravity for
	 * @return the gravity for the given position
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
