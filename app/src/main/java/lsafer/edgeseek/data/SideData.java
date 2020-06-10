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

import cufy.beans.AbstractBean;
import cufyx.perference.MapDataStore;
import lsafer.edgeseek.util.Position;

/**
 * A bean that holds the data of a side.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 08-Jun-20
 */
final public class SideData extends AbstractBean {
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String FACTOR = "factor";

	/**
	 * The position of this side.
	 */
	final public int position;
	/**
	 * The preference-data-store of this side-data.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * The number of splits in this side.
	 */
	@Property
	public int factor = Position.factor.FULL;

	/**
	 * Constructs a new side data.
	 *
	 * @param position the position of this side.
	 * @throws IllegalArgumentException if the given 'position' is not within the range [0, 3]
	 */
	public SideData(int position) {
		if (position < 0 || position > 3)
			throw new IllegalArgumentException("Illegal side position: " + position);

		this.position = position;
	}
}
