package lsafer.edgeseek.util;

import android.view.Gravity;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import lsafer.edgeseek.R;

/**
 * @author lsafer
 * @since 07-Jun-20
 */
@SuppressWarnings("JavaDoc")
final public class Position { //[-5, 39]
	final public static int MAX = 39;
	final public static int MIN = 0;
	final public static int XMIN = -5;
	//EXTRA ALIGNS
	final public static int CRIGHT = -5;
	final public static int CTOP = -4;
	final public static int CLEFT = -3;
	final public static int CBOTTOM = -2;
	final public static int CENTER = -1;
	//FACTORS
	final public static int FACTOR_FULL = 1;
	final public static int FACTOR_HALF = 2;
	final public static int FACTOR_THIRD = 3;
	final public static int FACTOR_QUARTER = 4;
	final public static int[] FACTORS = new int[]{
			FACTOR_FULL,
			FACTOR_HALF,
			FACTOR_THIRD,
			FACTOR_QUARTER
	};
	//BOTTOM
	final public static int BOTTOM = 0;
	final public static int BOTTOM2_LEFT = 8;
	final public static int BOTTOM2_RIGHT = 4;
	final public static int BOTTOM3_CENTER = 16;
	final public static int BOTTOM3_LEFT = 20;
	final public static int BOTTOM3_RIGHT = 12;
	final public static int BOTTOM4_RIGHT = 24;
	final public static int BOTTOM4_CRIGHT = 28;
	final public static int BOTTOM4_CLEFT = 32;
	final public static int BOTTOM4_LEFT = 36;
	final public static int[] BOTTOMS = new int[]{
			BOTTOM,
			BOTTOM2_RIGHT,
			BOTTOM2_LEFT,
			BOTTOM3_RIGHT,
			BOTTOM3_CENTER,
			BOTTOM3_LEFT,
			BOTTOM4_RIGHT,
			BOTTOM4_CRIGHT,
			BOTTOM4_CLEFT,
			BOTTOM4_LEFT
	};
	//LEFT
	final public static int LEFT = 1;
	final public static int LEFT2_BOTTOM = 5;
	final public static int LEFT2_TOP = 9;
	final public static int LEFT3_BOTTOM = 13;
	final public static int LEFT3_CENTER = 17;
	final public static int LEFT3_TOP = 21;
	final public static int LEFT4_BOTTOM = 25;
	final public static int LEFT4_CBOTTOM = 29;
	final public static int LEFT4_CTOP = 33;
	final public static int LEFT4_TOP = 37;
	final public static int[] LEFTS = new int[]{
			LEFT,
			LEFT2_BOTTOM,
			LEFT2_TOP,
			LEFT3_BOTTOM,
			LEFT3_CENTER,
			LEFT3_TOP,
			LEFT4_BOTTOM,
			LEFT4_CBOTTOM,
			LEFT4_CTOP,
			LEFT4_TOP
	};
	//RIGHT
	final public static int RIGHT = 3;
	final public static int RIGHT2_BOTTOM = 11;
	final public static int RIGHT2_TOP = 7;
	final public static int RIGHT3_BOTTOM = 23;
	final public static int RIGHT3_CENTER = 19;
	final public static int RIGHT3_TOP = 15;
	final public static int RIGHT4_TOP = 27;
	final public static int RIGHT4_CTOP = 31;
	final public static int RIGHT4_CBOTTOM = 35;
	final public static int RIGHT4_BOTTOM = 39;
	final public static int[] RIGHTS = new int[]{
			RIGHT,
			RIGHT2_TOP,
			RIGHT2_BOTTOM,
			RIGHT3_TOP,
			RIGHT3_CENTER,
			RIGHT3_BOTTOM,
			RIGHT4_TOP,
			RIGHT4_CTOP,
			RIGHT4_CBOTTOM,
			RIGHT4_BOTTOM
	};
	//TOP
	final public static int TOP = 2;
	final public static int TOP2_LEFT = 6;
	final public static int TOP2_RIGHT = 10;
	final public static int TOP3_CENTER = 18;
	final public static int TOP3_LEFT = 14;
	final public static int TOP3_RIGHT = 22;
	final public static int TOP4_LEFT = 26;
	final public static int TOP4_CLEFT = 30;
	final public static int TOP4_CRIGHT = 34;
	final public static int TOP4_RIGHT = 38;
	final public static int[] TOPS = new int[]{
			TOP,
			TOP2_LEFT,
			TOP2_RIGHT,
			TOP3_LEFT,
			TOP3_CENTER,
			TOP3_RIGHT,
			TOP4_LEFT,
			TOP4_CLEFT,
			TOP4_CRIGHT,
			TOP4_RIGHT
	};
	//SIDES
	final public static int[] SIDES = new int[]{
			BOTTOM,
			LEFT,
			TOP,
			RIGHT
	};
	final public static int[] POSITIONS = new int[]{
			//1
			BOTTOM, LEFT, TOP, RIGHT,
			//2
			BOTTOM2_RIGHT, LEFT2_BOTTOM, TOP2_LEFT, RIGHT2_TOP,
			BOTTOM2_LEFT, LEFT2_TOP, TOP2_RIGHT, RIGHT2_BOTTOM,
			//3
			BOTTOM3_RIGHT, LEFT3_BOTTOM, TOP3_LEFT, RIGHT3_TOP,
			BOTTOM3_CENTER, LEFT3_CENTER, TOP3_CENTER, RIGHT3_CENTER,
			BOTTOM3_LEFT, LEFT3_TOP, TOP3_RIGHT, RIGHT3_BOTTOM,
			//4
			BOTTOM4_RIGHT, LEFT4_BOTTOM, TOP4_LEFT, RIGHT4_TOP,
			BOTTOM4_CRIGHT, LEFT4_CBOTTOM, TOP4_CLEFT, RIGHT4_CTOP,
			BOTTOM4_CLEFT, LEFT4_CTOP, TOP4_CRIGHT, RIGHT4_CBOTTOM,
			BOTTOM4_LEFT, LEFT4_TOP, TOP4_RIGHT, RIGHT4_BOTTOM
	};

	private Position() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Get the position of the given id-res.
	 *
	 * @param id to get the position of
	 * @return a position of the given id-res
	 */
	public static int fromId(@IdRes int id) {
		switch (id) {
			//BOTTOM
			case R.id.bottom:
			case R.id.bottom_split:
				return BOTTOM;
			case R.id.bottom2_right:
				return BOTTOM2_RIGHT;
			case R.id.bottom2_left:
				return BOTTOM2_LEFT;
			case R.id.bottom3_right:
				return BOTTOM3_RIGHT;
			case R.id.bottom3_center:
				return BOTTOM3_CENTER;
			case R.id.bottom3_left:
				return BOTTOM3_LEFT;
			case R.id.bottom4_right:
				return BOTTOM4_RIGHT;
			case R.id.bottom4_cright:
				return BOTTOM4_CRIGHT;
			case R.id.bottom4_cleft:
				return BOTTOM4_CLEFT;
			case R.id.bottom4_left:
				return BOTTOM4_LEFT;
			//LEFT
			case R.id.left:
			case R.id.left_split:
				return LEFT;
			case R.id.left2_bottom:
				return LEFT2_BOTTOM;
			case R.id.left2_top:
				return LEFT2_TOP;
			case R.id.left3_bottom:
				return LEFT3_BOTTOM;
			case R.id.left3_center:
				return LEFT3_CENTER;
			case R.id.left3_top:
				return LEFT3_TOP;
			case R.id.left4_bottom:
				return LEFT4_BOTTOM;
			case R.id.left4_cbottom:
				return LEFT4_CBOTTOM;
			case R.id.left4_ctop:
				return LEFT4_CTOP;
			case R.id.left4_top:
				return LEFT4_TOP;
			//TOP
			case R.id.top:
			case R.id.top_split:
				return TOP;
			case R.id.top2_left:
				return TOP2_LEFT;
			case R.id.top2_right:
				return TOP2_RIGHT;
			case R.id.top3_left:
				return TOP3_LEFT;
			case R.id.top3_center:
				return TOP3_CENTER;
			case R.id.top3_right:
				return TOP3_RIGHT;
			case R.id.top4_left:
				return TOP4_LEFT;
			case R.id.top4_cleft:
				return TOP4_CLEFT;
			case R.id.top4_cright:
				return TOP4_CRIGHT;
			case R.id.top4_right:
				return TOP4_RIGHT;
			//RIGHT
			case R.id.right:
			case R.id.right_split:
				return RIGHT;
			case R.id.right2_top:
				return RIGHT2_TOP;
			case R.id.right2_bottom:
				return RIGHT2_BOTTOM;
			case R.id.right3_top:
				return RIGHT3_TOP;
			case R.id.right3_center:
				return RIGHT3_CENTER;
			case R.id.right3_bottom:
				return RIGHT3_BOTTOM;
			case R.id.right4_top:
				return RIGHT4_TOP;
			case R.id.right4_ctop:
				return RIGHT4_CTOP;
			case R.id.right4_cbottom:
				return RIGHT4_CBOTTOM;
			case R.id.right4_bottom:
				return RIGHT4_BOTTOM;
			default:
				throw new RuntimeException("Unexpected id: " + id);
		}
	}

	@IdRes
	public static int getId(int side, int factor) {
		switch (factor) {
			case FACTOR_FULL:
				switch (side) {
					case BOTTOM:
						return R.id.bottom;
					case LEFT:
						return R.id.left;
					case TOP:
						return R.id.top;
					case RIGHT:
						return R.id.right;
				}
			case FACTOR_HALF:
				switch (side) {
					case BOTTOM:
						return R.id.bottom2;
					case LEFT:
						return R.id.left2;
					case TOP:
						return R.id.top2;
					case RIGHT:
						return R.id.right2;
				}
			case FACTOR_THIRD:
				switch (side) {
					case BOTTOM:
						return R.id.bottom3;
					case LEFT:
						return R.id.left3;
					case TOP:
						return R.id.top3;
					case RIGHT:
						return R.id.right3;
				}
			case FACTOR_QUARTER:
				switch (side) {
					case BOTTOM:
						return R.id.bottom4;
					case LEFT:
						return R.id.left4;
					case TOP:
						return R.id.top4;
					case RIGHT:
						return R.id.right4;
				}
			default:
				throw new IllegalArgumentException("Unexpected factor: " + factor + " in side: " + side);
		}
	}

	/**
	 * Get {@link Gravity} value for the given position.
	 *
	 * @param position to get the gravity for
	 * @return the gravity for the given position
	 */
	public static int getGravity(int position) {
		int side = Position.getSide(position);
		int align = Position.getAlign(position);

		int gravity;

		switch (side) {
			case BOTTOM:
				gravity = Gravity.BOTTOM;
				break;
			case LEFT:
				gravity = Gravity.LEFT;
				break;
			case TOP:
				gravity = Gravity.TOP;
				break;
			case RIGHT:
				gravity = Gravity.RIGHT;
				break;
			default:
				throw new InternalError("Unexpected side: " + side);
		}

		switch (align) {
			case BOTTOM:
				return gravity | Gravity.BOTTOM;
			case LEFT:
				return gravity | Gravity.LEFT;
			case TOP:
				return gravity | Gravity.TOP;
			case RIGHT:
				return gravity | Gravity.RIGHT;
			case CENTER:
				return gravity | Gravity.CENTER;
			case CBOTTOM:
				return gravity | Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
			case CLEFT:
				return gravity | Gravity.CENTER_HORIZONTAL | Gravity.LEFT;
			case CTOP:
				return gravity | Gravity.CENTER_VERTICAL | Gravity.TOP;
			case CRIGHT:
				return gravity | Gravity.CENTER_HORIZONTAL | Gravity.RIGHT;
			default:
				throw new InternalError("Unexpected align: " + align);
		}
	}

	/**
	 * Get the split factor of the position given.
	 * If a position in a range of split of 3 (left, center, bottom) then 3 will be returned.
	 * <ul>
	 *     <li>{@link #FACTOR_FULL}</li>
	 *     <li>{@link #FACTOR_HALF}</li>
	 *     <li>{@link #FACTOR_THIRD}</li>
	 *     <li>{@link #FACTOR_QUARTER}</li>
	 * </ul>
	 *
	 * @param position to get the factor of
	 * @return the split factor at the range the given position is at
	 * @throws IllegalArgumentException if the given position is out of any known range (currently [1, 4])
	 */
	public static int getFactor(int position) {
		if (position <= RIGHT)
			return 1;
		if (position <= RIGHT2_BOTTOM)
			return 2;
		if (position <= RIGHT3_BOTTOM)
			return 3;
		if (position <= RIGHT4_BOTTOM)
			return 4;

		throw new IllegalArgumentException("Unexpected position: " + position);
	}

	/**
	 * Get the resource-id that is linked to the given position.
	 *
	 * @param position to get the id of
	 * @return an id for the given position
	 * @throws IllegalArgumentException if the given 'position' have no known id
	 */
	@IdRes
	public static int getId(int position) {
		switch (position) {
			//BOTTOM
			case BOTTOM:
				return R.id.bottom;
			case BOTTOM2_RIGHT:
				return R.id.bottom2_right;
			case BOTTOM2_LEFT:
				return R.id.bottom2_left;
			case BOTTOM3_RIGHT:
				return R.id.bottom3_right;
			case BOTTOM3_CENTER:
				return R.id.bottom3_center;
			case BOTTOM3_LEFT:
				return R.id.bottom3_left;
			case BOTTOM4_RIGHT:
				return R.id.bottom4_right;
			case BOTTOM4_CRIGHT:
				return R.id.bottom4_cright;
			case BOTTOM4_CLEFT:
				return R.id.bottom4_cleft;
			case BOTTOM4_LEFT:
				return R.id.bottom4_left;
			//LEFT
			case LEFT:
				return R.id.left;
			case LEFT2_BOTTOM:
				return R.id.left2_bottom;
			case LEFT2_TOP:
				return R.id.left2_top;
			case LEFT3_BOTTOM:
				return R.id.left3_bottom;
			case LEFT3_CENTER:
				return R.id.left3_center;
			case LEFT3_TOP:
				return R.id.left3_top;
			case LEFT4_BOTTOM:
				return R.id.left4_bottom;
			case LEFT4_CBOTTOM:
				return R.id.left4_cbottom;
			case LEFT4_CTOP:
				return R.id.left4_ctop;
			case LEFT4_TOP:
				return R.id.left4_top;
			//TOP
			case TOP:
				return R.id.top;
			case TOP2_LEFT:
				return R.id.top2_left;
			case TOP2_RIGHT:
				return R.id.top2_right;
			case TOP3_LEFT:
				return R.id.top3_left;
			case TOP3_CENTER:
				return R.id.top3_center;
			case TOP3_RIGHT:
				return R.id.top3_right;
			case TOP4_LEFT:
				return R.id.top4_left;
			case TOP4_CLEFT:
				return R.id.top4_cleft;
			case TOP4_CRIGHT:
				return R.id.top4_cright;
			case TOP4_RIGHT:
				return R.id.top4_right;
			//RIGHT
			case RIGHT:
				return R.id.right;
			case RIGHT2_TOP:
				return R.id.right2_top;
			case RIGHT2_BOTTOM:
				return R.id.right2_bottom;
			case RIGHT3_TOP:
				return R.id.right3_top;
			case RIGHT3_CENTER:
				return R.id.right3_center;
			case RIGHT3_BOTTOM:
				return R.id.right3_bottom;
			case RIGHT4_TOP:
				return R.id.right4_top;
			case RIGHT4_CTOP:
				return R.id.right4_ctop;
			case RIGHT4_CBOTTOM:
				return R.id.right4_cbottom;
			case RIGHT4_BOTTOM:
				return R.id.right4_bottom;
			default:
				throw new RuntimeException("Unexpected position: " + position);
		}
	}

	/**
	 * Get the side the given position is at.
	 * <ul>
	 *     <li>{@link #BOTTOM}</li>
	 *     <li>{@link #LEFT}</li>
	 *     <li>{@link #TOP}</li>
	 *     <li>{@link #RIGHT}</li>
	 * </ul>
	 *
	 * @param position
	 * @return
	 */
	public static int getSide(int position) {
		return position - position / 4 * 4;
	}

	/**
	 * Get the align of the given position at its side.
	 * <ul>
	 *     <li>{@link #BOTTOM}</li>
	 *     <li>{@link #LEFT}</li>
	 *     <li>{@link #TOP}</li>
	 *     <li>{@link #RIGHT}</li>
	 *     <li>{@link #CENTER}</li>
	 *     <li>{@link #CBOTTOM}</li>
	 *     <li>{@link #CLEFT}</li>
	 *     <li>{@link #CTOP}</li>
	 *     <li>{@link #CRIGHT}</li>
	 * </ul>
	 *
	 * @param position to get the align of
	 * @return the align of the given position
	 * @throws IllegalArgumentException if the given 'position' is out of known positions
	 */
	public static int getAlign(int position) {
		//if you think you could came up with a formula, just do it :)
		switch (position) {
			//BOTTOM
			case BOTTOM:
			case LEFT2_BOTTOM:
			case LEFT3_BOTTOM:
			case LEFT4_BOTTOM:
			case RIGHT2_BOTTOM:
			case RIGHT3_BOTTOM:
			case RIGHT4_BOTTOM:
				return BOTTOM;
			//LEFT
			case LEFT:
			case BOTTOM2_LEFT:
			case BOTTOM3_LEFT:
			case BOTTOM4_LEFT:
			case TOP2_LEFT:
			case TOP3_LEFT:
			case TOP4_LEFT:
				return LEFT;
			//TOP
			case TOP:
			case LEFT2_TOP:
			case LEFT3_TOP:
			case LEFT4_TOP:
			case RIGHT2_TOP:
			case RIGHT3_TOP:
			case RIGHT4_TOP:
				return TOP;
			//RIGHT
			case RIGHT:
			case BOTTOM2_RIGHT:
			case BOTTOM3_RIGHT:
			case BOTTOM4_RIGHT:
			case TOP2_RIGHT:
			case TOP3_RIGHT:
			case TOP4_RIGHT:
				return RIGHT;
			//CENTER
			case CENTER:
			case BOTTOM3_CENTER:
			case LEFT3_CENTER:
			case TOP3_CENTER:
			case RIGHT3_CENTER:
				return CENTER;
			//CBOTTOM
			case CBOTTOM:
			case LEFT4_CBOTTOM:
			case RIGHT4_CBOTTOM:
				return CBOTTOM;
			//CLEFT
			case CLEFT:
			case BOTTOM4_CLEFT:
			case TOP4_CLEFT:
				return CLEFT;
			//CTOP
			case CTOP:
			case LEFT4_CTOP:
			case RIGHT4_CTOP:
				return CTOP;
			case CRIGHT:
			case BOTTOM4_CRIGHT:
			case TOP4_CRIGHT:
				return CRIGHT;
			default:
				throw new IllegalArgumentException("Unexpected position: " + position);
		}
	}

	/**
	 * Get the un-rotated position for the given parameters.
	 *
	 * @param position the original position
	 * @param display  the rotation of the display
	 * @return the un-rotated position for the given parameters
	 */
	public static int getRotated(int position, int display) {
		int gap = (position / 4) * 4;
		return (display * 3 + position - gap) % 4 + gap;
	}

	public static int[] getSidePositions(int side) {
		switch (side) {
			case BOTTOM:
				return BOTTOMS;
			case LEFT:
				return LEFTS;
			case TOP:
				return TOPS;
			case RIGHT:
				return RIGHTS;
			default:
				throw new IllegalArgumentException("Unexpected side: " + side);
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
	public static int getEdgeTitle(int position) {
		switch (position) {
			//BOTTOM
			case BOTTOM:
				return R.string._bottom;
			case BOTTOM2_RIGHT:
				return R.string._bottom2_right;
			case BOTTOM2_LEFT:
				return R.string._bottom2_left;
			case BOTTOM3_RIGHT:
				return R.string._bottom3_right;
			case BOTTOM3_CENTER:
				return R.string._bottom3_center;
			case BOTTOM3_LEFT:
				return R.string._bottom3_left;
			case BOTTOM4_RIGHT:
				return R.string._bottom4_right;
			case BOTTOM4_CRIGHT:
				return R.string._bottom4_cright;
			case BOTTOM4_CLEFT:
				return R.string._bottom4_cleft;
			case BOTTOM4_LEFT:
				return R.string._bottom4_left;
			//LEFT
			case LEFT:
				return R.string._left;
			case LEFT2_BOTTOM:
				return R.string._left2_bottom;
			case LEFT2_TOP:
				return R.string._left2_top;
			case LEFT3_BOTTOM:
				return R.string._left3_bottom;
			case LEFT3_CENTER:
				return R.string._left3_center;
			case LEFT3_TOP:
				return R.string._left3_top;
			case LEFT4_BOTTOM:
				return R.string._left4_bottom;
			case LEFT4_CBOTTOM:
				return R.string._left4_cbottom;
			case LEFT4_CTOP:
				return R.string._left4_ctop;
			case LEFT4_TOP:
				return R.string._left4_top;
			//TOP
			case TOP:
				return R.string._top;
			case TOP2_LEFT:
				return R.string._top2_left;
			case TOP2_RIGHT:
				return R.string._top2_right;
			case TOP3_LEFT:
				return R.string._top3_left;
			case TOP3_CENTER:
				return R.string._top3_center;
			case TOP3_RIGHT:
				return R.string._top3_right;
			case TOP4_LEFT:
				return R.string._top4_left;
			case TOP4_CLEFT:
				return R.string._top4_cleft;
			case TOP4_CRIGHT:
				return R.string._top4_cright;
			case TOP4_RIGHT:
				return R.string._top4_right;
			//RIGHT
			case RIGHT:
				return R.string._right;
			case RIGHT2_TOP:
				return R.string._right2_top;
			case RIGHT2_BOTTOM:
				return R.string._right2_bottom;
			case RIGHT3_TOP:
				return R.string._right3_top;
			case RIGHT3_CENTER:
				return R.string._right3_center;
			case RIGHT3_BOTTOM:
				return R.string._right3_bottom;
			case RIGHT4_TOP:
				return R.string._right4_top;
			case RIGHT4_CTOP:
				return R.string._right4_ctop;
			case RIGHT4_CBOTTOM:
				return R.string._right4_cbottom;
			case RIGHT4_BOTTOM:
				return R.string._right4_bottom;
			default:
				throw new RuntimeException("Unexpected position: " + position);
		}
	}

	@StringRes
	public static int getSideTitle(int side) {
		switch (side) {
			case BOTTOM:
				return R.string._bottom_side;
			case LEFT:
				return R.string._left_side;
			case TOP:
				return R.string._top_side;
			case RIGHT:
				return R.string._right_side;
			default:
				throw new IllegalArgumentException("Unexpected side: " + side);
		}
	}

	/**
	 * Determine if the given 'position' should be displayed landscape (width = MATCH_PARENT) or not (height = MATCH_PARENT).
	 *
	 * @param position the position to be checked
	 * @return whether the given 'position' is a landscape position or not
	 */
	public static boolean isLandscape(int position) {
		int origin = Position.getSide(position);
		return origin == BOTTOM || origin == TOP;
	}
}
