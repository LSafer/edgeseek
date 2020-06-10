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

import android.graphics.Point;
import android.view.Display;
import androidx.arch.core.util.Function;

import java.util.List;
import java.util.Objects;

/**
 * Common utils across the application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 07-Oct-2019
 */
final public class Util {
	/**
	 * Compute the given inputs.
	 *
	 * @param axis0       the previous axis.
	 * @param axis1       the new axis.
	 * @param sensitivity the sensitivity.
	 * @param current     the current value.
	 * @param factor      the sensitivity factors.
	 * @param maximum     maximum value.
	 * @param minimum     minimum value.
	 * @return computed value from the given input.
	 */
	public static int compute(Float axis0, Float axis1, float sensitivity, float factor, int current, int maximum, int minimum) {
		float c = axis0 == null || axis1 == null ? 0 : (axis0 - axis1) * (sensitivity / factor);
		int x = (int) c + current;
		return x > maximum ? maximum : x < minimum ? minimum : x;
	}

	/**
	 * Fill the given list using the given function.
	 *
	 * @param list     to be filled
	 * @param length   how many elements to fill the given list
	 * @param function is the function to be used to fill the given list
	 * @param <E>      the type of the elements in the given list
	 * @return the given list
	 * @throws NullPointerException     if the given 'list' or 'function' is null
	 * @throws IllegalArgumentException if the given length is less than 0
	 */
	public static <E> List<E> fill(List<E> list, int length, Function<Integer, E> function) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(function, "function");
		if (length < 0)
			throw new IllegalArgumentException("length < 0");

		for (int i = 0; i < length; i++)
			list.add(function.apply(i));
		return list;
	}

	/**
	 * Get the height of the given display.
	 *
	 * @param display to get the height of.
	 * @return the height of the given display.
	 * @throws NullPointerException if the given 'display' is null.
	 */
	public static float getHeight(Display display) {
		Objects.requireNonNull(display, "display");
		Point point = new Point();
		display.getSize(point);
		return point.y;
	}

	/**
	 * Get the width of the given display.
	 *
	 * @param display to get the width of.
	 * @return the width of the given display.
	 * @throws NullPointerException if the given 'display' is null.
	 */
	public static float getWidth(Display display) {
		Objects.requireNonNull(display, "display");
		Point point = new Point();
		display.getSize(point);
		return point.x;
	}
}
