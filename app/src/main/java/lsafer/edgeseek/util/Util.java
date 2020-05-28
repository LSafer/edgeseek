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
package lsafer.edgeseek.util;

import android.view.Gravity;

import androidx.annotation.IdRes;

import lsafer.edgeseek.R;

/**
 * Common utils.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 07-Oct-2019
 */
final public class Util {
	/**
	 * Solve the change between the initial point {@code i} and the final point {@code f}
	 * considering the given change-sensitivity {@code s}.
	 *
	 * @param i the initial point (nullable, the change will be 0)
	 * @param f the final point (nullable, the change will be 0)
	 * @param s the sensitivity to be applied in the algorithm (nullable, the change will be 0)
	 * @return the change from `i` to `f` with the sensitivity `s` applied
	 */
	public static float change(Float i, Float f, Float s) {
		return i == null || f == null || s == null ? 0 : (i - f) * s;
	}

	/**
	 * Get {@link Gravity} value for the given position.
	 *
	 * @param position to get the gravity for
	 * @return the gravity for the given position
	 */
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

	/**
	 * Get the id from the given position.
	 *
	 * @param position to get the id from
	 * @return an id from the given position
	 */
	@IdRes
	public static int id(int position) {
		switch (position) {
			case 0:
				return R.id.bottom;
			case 1:
				return R.id.left;
			case 2:
				return R.id.top;
			case 3:
				return R.id.right;
			default:
				throw new RuntimeException("position: " + position + " is not expected");
		}
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
				return 0;
			case R.id.left:
				return 1;
			case R.id.top:
				return 2;
			case R.id.right:
				return 3;
			default:
				throw new RuntimeException("id: " + id + " is not expected");
		}
	}

	/**
	 * Get the un-rotated position for the given parameters.
	 *
	 * @param position        the original position
	 * @param rotate          whether is it allowed to rotate or not
	 * @param displayRotation the rotation of the display
	 * @return the un-rotated position for the given parameters
	 */
	public static int position(int position, boolean rotate, int displayRotation) {
		return rotate ? position : position == 4 ? 4 : Math.abs(displayRotation * 3 + position) % 4;
	}

	/**
	 * Cap the given float 'f' not passing more than the maximum 'max'
	 * and not passing less than the minimum 'min'. if 'f' is more than
	 * 'max' then 'max' will be returned. and if 'f' is less than 'min'
	 * then 'min' will be returned.
	 *
	 * @param f   the value to be capped
	 * @param max the maximum value
	 * @param min the minimum value
	 * @return 'max' if 'f' is more than 'max' or 'min' if 'f' is less than 'min' otherwise 'f' will be returned
	 */
	public static float range(float f, float max, float min) {
		return f > max ? max : f < min ? min : f;
	}
}
