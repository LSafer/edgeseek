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

/**
 * A structure holding the data of an edge.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 26-May-20
 */
public class EdgeData extends AbstractBean {
	/**
	 * The position of this edge.
	 */
	final public int position;
	/**
	 * The data store for this bean.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * Whether this edge is activated or not.
	 */
	@Property
	public boolean activated;

	/**
	 * The width of this edge.
	 */
	@Property
	public int width = 20;

	/**
	 * The color of the edge.
	 */
	@Property
	public int color = 0x000000;

	/**
	 * The transparency of the edge.
	 */
	public int alpha = 0;

	/**
	 * Construct a new edge data for the edge in the given position.
	 *
	 * @param position the position of the edge
	 * @throws IllegalArgumentException if hte given 'position' is not within the range [0, 3]
	 */
	public EdgeData(int position) {
		if (position < 0 || position > 3)
			throw new IllegalArgumentException("position out of range [0, 3]");
		this.position = position;

		//---- default values
		this.activated = position == 1 || position == 3;
	}
}
