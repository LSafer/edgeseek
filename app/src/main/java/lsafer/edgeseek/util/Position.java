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
import lsafer.edgeseek.R;

/**
 * A utility class for managing positions.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 07-Jun-20
 */
@SuppressWarnings("JavaDoc")
final public class Position {
	//bottom
	final public static int BOTTOM = 0;
	final public static int BOTTOM2_LEFT = 8;
	final public static int BOTTOM2_RIGHT = 4;
	final public static int BOTTOM3_CENTER = 16;
	final public static int BOTTOM3_LEFT = 20;
	final public static int BOTTOM3_RIGHT = 12;
	final public static int BOTTOM4_CLEFT = 32;
	final public static int BOTTOM4_CRIGHT = 28;
	final public static int BOTTOM4_LEFT = 36;
	final public static int BOTTOM4_RIGHT = 24;
	//left
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
	//right
	final public static int RIGHT = 3;
	final public static int RIGHT2_BOTTOM = 11;
	final public static int RIGHT2_TOP = 7;
	final public static int RIGHT3_BOTTOM = 23;
	final public static int RIGHT3_CENTER = 19;
	final public static int RIGHT3_TOP = 15;
	final public static int RIGHT4_BOTTOM = 39;
	final public static int RIGHT4_CBOTTOM = 35;
	final public static int RIGHT4_CTOP = 31;
	final public static int RIGHT4_TOP = 27;
	//top
	final public static int TOP = 2;
	final public static int TOP2_LEFT = 6;
	final public static int TOP2_RIGHT = 10;
	final public static int TOP3_CENTER = 18;
	final public static int TOP3_LEFT = 14;
	final public static int TOP3_RIGHT = 22;
	final public static int TOP4_CLEFT = 30;
	final public static int TOP4_CRIGHT = 34;
	final public static int TOP4_LEFT = 26;
	final public static int TOP4_RIGHT = 38;

	/**
	 * This is an util class. And must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private Position() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Get the un-rotated position for the given parameters.
	 *
	 * @param position the original position.
	 * @param display  the rotation of the display.
	 * @return the un-rotated position for the given parameters.
	 */
	public static int getRotated(int position, int display) {
		int gap = (position / 4) * 4;
		return (display * 3 + position - gap) % 4 + gap;
	}

	/**
	 * Determine if the given 'position' should be displayed landscape (width = MATCH_PARENT) or not (height = MATCH_PARENT).
	 *
	 * @param position the position to be checked.
	 * @return whether the given 'position' is a landscape position or not.
	 */
	public static boolean isLandscape(int position) {
		int origin = Position.side.ofPosition(position);
		return origin == BOTTOM || origin == TOP;
	}

	/**
	 * A collection of edges' positions.
	 */
	final public static class edge {
		final public static int[] ARRAY = {
				//FULL
				BOTTOM, LEFT, TOP, RIGHT,
				//HALF
				BOTTOM2_RIGHT, LEFT2_BOTTOM, TOP2_LEFT, RIGHT2_TOP,
				BOTTOM2_LEFT, LEFT2_TOP, TOP2_RIGHT, RIGHT2_BOTTOM,
				//THIRD
				BOTTOM3_RIGHT, LEFT3_BOTTOM, TOP3_LEFT, RIGHT3_TOP,
				BOTTOM3_CENTER, LEFT3_CENTER, TOP3_CENTER, RIGHT3_CENTER,
				BOTTOM3_LEFT, LEFT3_TOP, TOP3_RIGHT, RIGHT3_BOTTOM,
				//QUARTER
				BOTTOM4_RIGHT, LEFT4_BOTTOM, TOP4_LEFT, RIGHT4_TOP,
				BOTTOM4_CRIGHT, LEFT4_CBOTTOM, TOP4_CLEFT, RIGHT4_CTOP,
				BOTTOM4_CLEFT, LEFT4_CTOP, TOP4_CRIGHT, RIGHT4_CBOTTOM,
				BOTTOM4_LEFT, LEFT4_TOP, TOP4_RIGHT, RIGHT4_BOTTOM
		};

		/**
		 * Get the resource-id that is linked to the given position.
		 *
		 * @param position to get the id of.
		 * @return an id for the given position.
		 * @throws IllegalArgumentException if the given 'position' is not in  {@link #ARRAY}.
		 */
		@IdRes
		public static int getId(int position) {
			switch (position) {
				//BOTTOM
				case Position.BOTTOM:
					return R.id.bottom;
				case Position.BOTTOM2_RIGHT:
					return R.id.bottom2_right;
				case Position.BOTTOM2_LEFT:
					return R.id.bottom2_left;
				case Position.BOTTOM3_RIGHT:
					return R.id.bottom3_right;
				case Position.BOTTOM3_CENTER:
					return R.id.bottom3_center;
				case Position.BOTTOM3_LEFT:
					return R.id.bottom3_left;
				case Position.BOTTOM4_RIGHT:
					return R.id.bottom4_right;
				case Position.BOTTOM4_CRIGHT:
					return R.id.bottom4_cright;
				case Position.BOTTOM4_CLEFT:
					return R.id.bottom4_cleft;
				case Position.BOTTOM4_LEFT:
					return R.id.bottom4_left;
				//LEFT
				case Position.LEFT:
					return R.id.left;
				case Position.LEFT2_BOTTOM:
					return R.id.left2_bottom;
				case Position.LEFT2_TOP:
					return R.id.left2_top;
				case Position.LEFT3_BOTTOM:
					return R.id.left3_bottom;
				case Position.LEFT3_CENTER:
					return R.id.left3_center;
				case Position.LEFT3_TOP:
					return R.id.left3_top;
				case Position.LEFT4_BOTTOM:
					return R.id.left4_bottom;
				case Position.LEFT4_CBOTTOM:
					return R.id.left4_cbottom;
				case Position.LEFT4_CTOP:
					return R.id.left4_ctop;
				case Position.LEFT4_TOP:
					return R.id.left4_top;
				//TOP
				case Position.TOP:
					return R.id.top;
				case Position.TOP2_LEFT:
					return R.id.top2_left;
				case Position.TOP2_RIGHT:
					return R.id.top2_right;
				case Position.TOP3_LEFT:
					return R.id.top3_left;
				case Position.TOP3_CENTER:
					return R.id.top3_center;
				case Position.TOP3_RIGHT:
					return R.id.top3_right;
				case Position.TOP4_LEFT:
					return R.id.top4_left;
				case Position.TOP4_CLEFT:
					return R.id.top4_cleft;
				case Position.TOP4_CRIGHT:
					return R.id.top4_cright;
				case Position.TOP4_RIGHT:
					return R.id.top4_right;
				//RIGHT
				case Position.RIGHT:
					return R.id.right;
				case Position.RIGHT2_TOP:
					return R.id.right2_top;
				case Position.RIGHT2_BOTTOM:
					return R.id.right2_bottom;
				case Position.RIGHT3_TOP:
					return R.id.right3_top;
				case Position.RIGHT3_CENTER:
					return R.id.right3_center;
				case Position.RIGHT3_BOTTOM:
					return R.id.right3_bottom;
				case Position.RIGHT4_TOP:
					return R.id.right4_top;
				case Position.RIGHT4_CTOP:
					return R.id.right4_ctop;
				case Position.RIGHT4_CBOTTOM:
					return R.id.right4_cbottom;
				case Position.RIGHT4_BOTTOM:
					return R.id.right4_bottom;
				default:
					throw new RuntimeException("Unexpected position: " + position);
			}
		}

		/**
		 * Get the edge name for the given position.
		 *
		 * @param position of the edge to get the string resources integer for.
		 * @return the string resources integer of the name of the edge that have the given position.
		 * @throws IllegalArgumentException if the position is not in {@link #ARRAY}.
		 */
		@StringRes
		public static int getTitle(int position) {
			switch (position) {
				//BOTTOM
				case Position.BOTTOM:
					return R.string._bottom;
				case Position.BOTTOM2_RIGHT:
					return R.string._bottom2_right;
				case Position.BOTTOM2_LEFT:
					return R.string._bottom2_left;
				case Position.BOTTOM3_RIGHT:
					return R.string._bottom3_right;
				case Position.BOTTOM3_CENTER:
					return R.string._bottom3_center;
				case Position.BOTTOM3_LEFT:
					return R.string._bottom3_left;
				case Position.BOTTOM4_RIGHT:
					return R.string._bottom4_right;
				case Position.BOTTOM4_CRIGHT:
					return R.string._bottom4_cright;
				case Position.BOTTOM4_CLEFT:
					return R.string._bottom4_cleft;
				case Position.BOTTOM4_LEFT:
					return R.string._bottom4_left;
				//LEFT
				case Position.LEFT:
					return R.string._left;
				case Position.LEFT2_BOTTOM:
					return R.string._left2_bottom;
				case Position.LEFT2_TOP:
					return R.string._left2_top;
				case Position.LEFT3_BOTTOM:
					return R.string._left3_bottom;
				case Position.LEFT3_CENTER:
					return R.string._left3_center;
				case Position.LEFT3_TOP:
					return R.string._left3_top;
				case Position.LEFT4_BOTTOM:
					return R.string._left4_bottom;
				case Position.LEFT4_CBOTTOM:
					return R.string._left4_cbottom;
				case Position.LEFT4_CTOP:
					return R.string._left4_ctop;
				case Position.LEFT4_TOP:
					return R.string._left4_top;
				//TOP
				case Position.TOP:
					return R.string._top;
				case Position.TOP2_LEFT:
					return R.string._top2_left;
				case Position.TOP2_RIGHT:
					return R.string._top2_right;
				case Position.TOP3_LEFT:
					return R.string._top3_left;
				case Position.TOP3_CENTER:
					return R.string._top3_center;
				case Position.TOP3_RIGHT:
					return R.string._top3_right;
				case Position.TOP4_LEFT:
					return R.string._top4_left;
				case Position.TOP4_CLEFT:
					return R.string._top4_cleft;
				case Position.TOP4_CRIGHT:
					return R.string._top4_cright;
				case Position.TOP4_RIGHT:
					return R.string._top4_right;
				//RIGHT
				case Position.RIGHT:
					return R.string._right;
				case Position.RIGHT2_TOP:
					return R.string._right2_top;
				case Position.RIGHT2_BOTTOM:
					return R.string._right2_bottom;
				case Position.RIGHT3_TOP:
					return R.string._right3_top;
				case Position.RIGHT3_CENTER:
					return R.string._right3_center;
				case Position.RIGHT3_BOTTOM:
					return R.string._right3_bottom;
				case Position.RIGHT4_TOP:
					return R.string._right4_top;
				case Position.RIGHT4_CTOP:
					return R.string._right4_ctop;
				case Position.RIGHT4_CBOTTOM:
					return R.string._right4_cbottom;
				case Position.RIGHT4_BOTTOM:
					return R.string._right4_bottom;
				default:
					throw new RuntimeException("Unexpected position: " + position);
			}
		}

		/**
		 * Get the position of the given id-res.
		 *
		 * @param id to get the position of
		 * @return a position of the given id-res
		 * @throws IllegalArgumentException if the given 'id' is not in {@link #ARRAY}.
		 */
		public static int ofId(@IdRes int id) {
			switch (id) {
				//BOTTOM
				case R.id.bottom:
					return Position.BOTTOM;
				case R.id.bottom2_right:
					return Position.BOTTOM2_RIGHT;
				case R.id.bottom2_left:
					return Position.BOTTOM2_LEFT;
				case R.id.bottom3_right:
					return Position.BOTTOM3_RIGHT;
				case R.id.bottom3_center:
					return Position.BOTTOM3_CENTER;
				case R.id.bottom3_left:
					return Position.BOTTOM3_LEFT;
				case R.id.bottom4_right:
					return Position.BOTTOM4_RIGHT;
				case R.id.bottom4_cright:
					return Position.BOTTOM4_CRIGHT;
				case R.id.bottom4_cleft:
					return Position.BOTTOM4_CLEFT;
				case R.id.bottom4_left:
					return Position.BOTTOM4_LEFT;
				//LEFT
				case R.id.left:
					return Position.LEFT;
				case R.id.left2_bottom:
					return Position.LEFT2_BOTTOM;
				case R.id.left2_top:
					return Position.LEFT2_TOP;
				case R.id.left3_bottom:
					return Position.LEFT3_BOTTOM;
				case R.id.left3_center:
					return Position.LEFT3_CENTER;
				case R.id.left3_top:
					return Position.LEFT3_TOP;
				case R.id.left4_bottom:
					return Position.LEFT4_BOTTOM;
				case R.id.left4_cbottom:
					return Position.LEFT4_CBOTTOM;
				case R.id.left4_ctop:
					return Position.LEFT4_CTOP;
				case R.id.left4_top:
					return Position.LEFT4_TOP;
				//TOP
				case R.id.top:
					return Position.TOP;
				case R.id.top2_left:
					return Position.TOP2_LEFT;
				case R.id.top2_right:
					return Position.TOP2_RIGHT;
				case R.id.top3_left:
					return Position.TOP3_LEFT;
				case R.id.top3_center:
					return Position.TOP3_CENTER;
				case R.id.top3_right:
					return Position.TOP3_RIGHT;
				case R.id.top4_left:
					return Position.TOP4_LEFT;
				case R.id.top4_cleft:
					return Position.TOP4_CLEFT;
				case R.id.top4_cright:
					return Position.TOP4_CRIGHT;
				case R.id.top4_right:
					return Position.TOP4_RIGHT;
				//RIGHT
				case R.id.right:
					return Position.RIGHT;
				case R.id.right2_top:
					return Position.RIGHT2_TOP;
				case R.id.right2_bottom:
					return Position.RIGHT2_BOTTOM;
				case R.id.right3_top:
					return Position.RIGHT3_TOP;
				case R.id.right3_center:
					return Position.RIGHT3_CENTER;
				case R.id.right3_bottom:
					return Position.RIGHT3_BOTTOM;
				case R.id.right4_top:
					return Position.RIGHT4_TOP;
				case R.id.right4_ctop:
					return Position.RIGHT4_CTOP;
				case R.id.right4_cbottom:
					return Position.RIGHT4_CBOTTOM;
				case R.id.right4_bottom:
					return Position.RIGHT4_BOTTOM;
				default:
					throw new RuntimeException("Unexpected id: " + id);
			}
		}
	}

	/**
	 * The split-factors. Represents the number of positions available in the slitted-sides.
	 */
	final public static class factor {
		//factors
		final public static int FULL = 1;
		final public static int HALF = 2;
		final public static int QUARTER = 4;
		final public static int THIRD = 3;
		//all
		final public static int[] ARRAY = {
				Position.factor.FULL,
				Position.factor.HALF,
				Position.factor.THIRD,
				Position.factor.QUARTER
		};

		/**
		 * Get the split factor of the position given. If a position in a range of split of 3 (left, center, bottom) then 3 will be returned.
		 *
		 * @param position to get the factor of.
		 * @return the split factor at the range the given position is at.
		 */
		public static int ofPosition(int position) {
			return (int) (Math.sqrt(2 * position + 1) + 1) / 2;
		}
	}

	final public static class gravity {
		public static int ofPosition(int position) {
			switch (position) {
				//bottom
				case Position.BOTTOM:
					return Gravity.BOTTOM;
				case Position.BOTTOM3_CENTER:
					return Gravity.BOTTOM | Gravity.CENTER;
				//left
				case Position.LEFT:
					return Gravity.LEFT;
				case Position.LEFT3_CENTER:
					return Gravity.LEFT | Gravity.CENTER;
				//top
				case Position.TOP:
					return Gravity.TOP;
				case Position.TOP3_CENTER:
					return Gravity.TOP | Gravity.CENTER;
				//right
				case Position.RIGHT:
					return Gravity.RIGHT;
				case Position.RIGHT3_CENTER:
					return Gravity.RIGHT | Gravity.CENTER;
				//bottom & right
				case Position.BOTTOM2_RIGHT:
				case Position.BOTTOM3_RIGHT:
				case Position.BOTTOM4_RIGHT:
				case Position.RIGHT2_BOTTOM:
				case Position.RIGHT3_BOTTOM:
				case Position.RIGHT4_BOTTOM:
					return Gravity.BOTTOM | Gravity.RIGHT;
				case Position.BOTTOM4_CRIGHT:
				case Position.RIGHT4_CBOTTOM:
					return Gravity.BOTTOM | Gravity.CENTER | Gravity.RIGHT;
				//bottom & left
				case Position.BOTTOM2_LEFT:
				case Position.BOTTOM3_LEFT:
				case Position.BOTTOM4_LEFT:
				case Position.LEFT2_BOTTOM:
				case Position.LEFT3_BOTTOM:
				case Position.LEFT4_BOTTOM:
					return Gravity.BOTTOM | Gravity.LEFT;
				case Position.BOTTOM4_CLEFT:
				case Position.LEFT4_CBOTTOM:
					return Gravity.BOTTOM | Gravity.CENTER | Gravity.LEFT;
				//top & right
				case Position.TOP2_RIGHT:
				case Position.TOP3_RIGHT:
				case Position.TOP4_RIGHT:
				case Position.RIGHT2_TOP:
				case Position.RIGHT3_TOP:
				case Position.RIGHT4_TOP:
					return Gravity.TOP | Gravity.RIGHT;
				case Position.TOP4_CRIGHT:
				case Position.RIGHT4_CTOP:
					return Gravity.TOP | Gravity.CENTER | Gravity.RIGHT;
				//top & left
				case Position.TOP2_LEFT:
				case Position.TOP3_LEFT:
				case Position.TOP4_LEFT:
				case Position.LEFT2_TOP:
				case Position.LEFT3_TOP:
				case Position.LEFT4_TOP:
					return Gravity.TOP | Gravity.LEFT;
				case Position.TOP4_CLEFT:
				case Position.LEFT4_CTOP:
					return Gravity.TOP | Gravity.CENTER | Gravity.LEFT;
				default:
					throw new IllegalArgumentException("Unknown position: " + position);
			}
		}
	}

	/**
	 * The sides. Represents the original positions for a side.
	 */
	final public static class side {
		//sides
		final public static int BOTTOM = Position.BOTTOM;
		final public static int LEFT = Position.LEFT;
		final public static int RIGHT = Position.RIGHT;
		final public static int TOP = Position.TOP;
		//all
		final public static int[] ARRAY = {
				Position.side.BOTTOM,
				Position.side.LEFT,
				Position.side.TOP,
				Position.side.RIGHT
		};

		@IdRes
		public static int getId(int side) {
			switch (side) {
				case Position.side.BOTTOM:
					return R.id.bottom_side;
				case Position.side.LEFT:
					return R.id.left_side;
				case Position.side.TOP:
					return R.id.top_side;
				case Position.side.RIGHT:
					return R.id.right_side;
				default:
					throw new IllegalArgumentException("Unknown side: " + side);
			}
		}

		/**
		 * Get string-res of the title of the give side.
		 *
		 * @param side to get a title string-res for.
		 * @return a title string-res for the given side.
		 * @throws IllegalArgumentException if the given 'side' is not in {@link #ARRAY}.
		 */
		@StringRes
		public static int getTitle(int side) {
			switch (side) {
				case Position.side.BOTTOM:
					return R.string._bottom_side;
				case Position.side.LEFT:
					return R.string._left_side;
				case Position.side.TOP:
					return R.string._top_side;
				case Position.side.RIGHT:
					return R.string._right_side;
				default:
					throw new IllegalArgumentException("Unexpected side: " + side);
			}
		}

		public static int ofId(@IdRes int id) {
			switch (id) {
				case R.id.bottom_side:
					return Position.side.BOTTOM;
				case R.id.left_side:
					return Position.side.LEFT;
				case R.id.top_side:
					return Position.side.TOP;
				case R.id.right_side:
					return Position.side.RIGHT;
				default:
					throw new IllegalArgumentException("Unknown id: " + id);
			}
		}

		/**
		 * Get the side the given position is at.
		 *
		 * @param position the position of the edge to get the side for.
		 * @return the position of the side of the given position of the targeted edge.
		 */
		public static int ofPosition(int position) {
			return position - position / 4 * 4;
		}
	}

	/**
	 * The side splits. Represents the positions where a slitted-sides' positions begins.
	 */
	final public static class split {
		//bottom
		final public static int BOTTOM = Position.BOTTOM;
		final public static int BOTTOM2 = Position.BOTTOM2_RIGHT;
		final public static int BOTTOM3 = Position.BOTTOM3_RIGHT;
		final public static int BOTTOM4 = Position.BOTTOM4_RIGHT;
		//left
		final public static int LEFT = Position.LEFT;
		final public static int LEFT2 = Position.LEFT2_BOTTOM;
		final public static int LEFT3 = Position.LEFT3_BOTTOM;
		final public static int LEFT4 = Position.LEFT4_BOTTOM;
		//right
		final public static int RIGHT = Position.RIGHT;
		final public static int RIGHT2 = Position.RIGHT2_TOP;
		final public static int RIGHT3 = Position.RIGHT3_TOP;
		final public static int RIGHT4 = Position.RIGHT4_TOP;
		//top
		final public static int TOP = Position.TOP;
		final public static int TOP2 = Position.TOP2_LEFT;
		final public static int TOP3 = Position.TOP3_LEFT;
		final public static int TOP4 = Position.TOP4_LEFT;
		//all
		final public static int[] ARRAY = {
				//bottom
				Position.split.BOTTOM,
				Position.split.BOTTOM2,
				Position.split.BOTTOM3,
				Position.split.BOTTOM4,
				//left
				Position.split.LEFT,
				Position.split.LEFT2,
				Position.split.LEFT3,
				Position.split.LEFT4,
				//top
				Position.split.TOP,
				Position.split.TOP2,
				Position.split.TOP3,
				Position.split.TOP4,
				//right
				Position.split.RIGHT,
				Position.split.RIGHT2,
				Position.split.RIGHT3,
				Position.split.RIGHT4
		};

		/**
		 * Returns the id of the given split.
		 *
		 * @param split the split to get the id for.
		 * @return the id of the given split.
		 * @throws IllegalArgumentException if the given 'split' is not in {@link #ARRAY}.
		 */
		@IdRes
		public static int getId(int split) {
			switch (split) {
				//bottom
				case Position.split.BOTTOM:
					return R.id.bottom;
				case Position.split.BOTTOM2:
					return R.id.bottom2;
				case Position.split.BOTTOM3:
					return R.id.bottom3;
				case Position.split.BOTTOM4:
					return R.id.bottom4;
				//left
				case Position.split.LEFT:
					return R.id.left;
				case Position.split.LEFT2:
					return R.id.left2;
				case Position.split.LEFT3:
					return R.id.left3;
				case Position.split.LEFT4:
					return R.id.left4;
				//top
				case Position.split.TOP:
					return R.id.top;
				case Position.split.TOP2:
					return R.id.top2;
				case Position.split.TOP3:
					return R.id.top3;
				case Position.split.TOP4:
					return R.id.top4;
				//right
				case Position.split.RIGHT:
					return R.id.right;
				case Position.split.RIGHT2:
					return R.id.right2;
				case Position.split.RIGHT3:
					return R.id.right3;
				case Position.split.RIGHT4:
					return R.id.right4;
				default:
					throw new IllegalArgumentException("Unexpected split: " + split);
			}
		}

		/**
		 * Get the split at the factor (and side) given.
		 *
		 * @param factor to get the split for.
		 * @param side   as more specification.
		 * @return the split at the side given with the factor given.
		 */
		public static int inSide(int side, int factor) {
			return factor * 2 * (factor - 1) + side;
		}

		public static int ofId(@IdRes int id) {
			switch (id) {
				//BOTTOM
				case R.id.bottom:
					return Position.split.BOTTOM;
				case R.id.bottom2:
					return Position.split.BOTTOM2;
				case R.id.bottom3:
					return Position.split.BOTTOM3;
				case R.id.bottom4:
					return Position.split.BOTTOM4;
				//LEFT
				case R.id.left:
					return Position.split.LEFT;
				case R.id.left2:
					return Position.split.LEFT2;
				case R.id.left3:
					return Position.split.LEFT3;
				case R.id.left4:
					return Position.split.LEFT4;
				//TOP
				case R.id.top:
					return Position.split.TOP;
				case R.id.top2:
					return Position.split.TOP2;
				case R.id.top3:
					return Position.split.TOP3;
				case R.id.top4:
					return Position.split.TOP4;
				//RIGHT
				case R.id.right:
					return Position.split.RIGHT;
				case R.id.right2:
					return Position.split.RIGHT2;
				case R.id.right3:
					return Position.split.RIGHT3;
				case R.id.right4:
					return Position.split.RIGHT4;
				default:
					throw new IllegalArgumentException("Unknown id: " + id);
			}
		}
	}

	/**
	 * The factor steps. Represents the positions where a factors' positions begins.
	 */
	final public static class step {
		//steps
		final public static int FULL = Position.BOTTOM;
		final public static int HALF = Position.BOTTOM2_RIGHT;
		final public static int QUARTER = Position.BOTTOM4_RIGHT;
		final public static int THIRD = Position.BOTTOM3_RIGHT;
		//all
		final public static int[] ARRAY = {
				Position.step.FULL,
				Position.step.HALF,
				Position.step.THIRD,
				Position.step.QUARTER
		};

		/**
		 * Get the step at the factor given.
		 *
		 * @param factor to get the step for.
		 * @return the step for the factor given.
		 */
		public static int ofFactor(int factor) {
			return factor * 2 * (factor - 1);
		}
	}
}