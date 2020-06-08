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
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import java.util.Objects;

import lsafer.edgeseek.R;

/**
 * Common utils across the application.
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
	 * Compute the given inputs.
	 *
	 * @param axis0       the previous axis
	 * @param axis1       the new axis
	 * @param sensitivity the sensitivity
	 * @param current     the current value
	 * @param factor      the sensitivity factor
	 * @param maximum     maximum value
	 * @param minimum     minimum value
	 * @return computed value from the given input
	 */
	public static int compute(Float axis0, Float axis1, float sensitivity, float factor, int current, int maximum, int minimum) {
		float c = axis0 == null || axis1 == null ? 0 : (axis0 - axis1) * (sensitivity / factor);
		int x = (int) c + current;
		return x > maximum ? maximum : x < minimum ? minimum : x;
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
	 * Get the edge name for the given position.
	 *
	 * @param position of the edge to get the string resources integer for
	 * @return the string resources integer of the name of the edge that have the given position
	 * @throws IllegalArgumentException if the position is not within the range [0, 3]
	 */
	@StringRes
	public static int positionEdgeName(int position) {
		switch (position) {
			case 0:
				return R.string.bottom_edge;
			case 1:
				return R.string.left_edge;
			case 2:
				return R.string.top_edge;
			case 3:
				return R.string.right_edge;
			default:
				throw new IllegalArgumentException("unexpected position: " + position);
		}
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

	/**
	 * Get the theme resources id for the theme-name provided.
	 *
	 * @param name the theme name
	 * @return the theme resources-id for the given theme-name
	 * @throws NullPointerException if the given 'name' is null
	 */
	@StyleRes
	public static int theme(String name) {
		Objects.requireNonNull(name, "name");
		switch (name) {
			default:
			case "black":
				return R.style.Black;
			case "dark":
				return R.style.Dark;
			case "light":
				return R.style.Light;
			case "white":
				return R.style.White;
		}
	}

	/**
	 * Fill the given list using the given function.
	 *
	 * @param list     to be filled
	 * @param index    the initial index to start on
	 * @param length   how many elements to fill the given list
	 * @param function is the function to be used to fill the given list
	 * @param <E>      the type of the elements in the given list
	 * @return the given list
	 * @throws NullPointerException     if the given 'list' or 'function' is null
	 * @throws IllegalArgumentException if the given length is less than 0
	 */
	public static <E> List<E> fill(List<E> list, int index, int length, Function<Integer, E> function) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(function, "function");
		if (length < 0)
			throw new IllegalArgumentException("length < 0");

		for (int i = 0, x = index; i < length; i++, x++)
			list.add(function.apply(x));
		return list;
	}
}
