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
public class SideData extends AbstractBean {
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
	public int factor = Position.FACTOR_FULL;

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
