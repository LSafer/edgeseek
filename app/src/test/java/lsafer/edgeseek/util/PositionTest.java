package lsafer.edgeseek.util;

import android.view.Gravity;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("JavaDoc")
public class PositionTest {

	@Test
	public void getRotated() {
		//bottom display
		agr(Position.BOTTOM, Position.BOTTOM, Position.BOTTOM);
		agr(Position.BOTTOM, Position.LEFT, Position.LEFT);
		agr(Position.BOTTOM, Position.TOP, Position.TOP);
		agr(Position.BOTTOM, Position.RIGHT, Position.RIGHT);
		//bottom display bottom
		agr(Position.BOTTOM, Position.BOTTOM2_RIGHT, Position.BOTTOM2_RIGHT);
		agr(Position.BOTTOM, Position.BOTTOM2_LEFT, Position.BOTTOM2_LEFT);
		//bottom display left
		agr(Position.BOTTOM, Position.LEFT2_BOTTOM, Position.LEFT2_BOTTOM);
		agr(Position.BOTTOM, Position.LEFT2_TOP, Position.LEFT2_TOP);
		//bottom display top
		agr(Position.BOTTOM, Position.TOP2_LEFT, Position.TOP2_LEFT);
		agr(Position.BOTTOM, Position.TOP2_RIGHT, Position.TOP2_RIGHT);
		//bottom display right
		agr(Position.BOTTOM, Position.RIGHT2_TOP, Position.RIGHT2_TOP);
		agr(Position.BOTTOM, Position.RIGHT2_BOTTOM, Position.RIGHT2_BOTTOM);

		//left display
		agr(Position.LEFT, Position.BOTTOM, Position.RIGHT);
		agr(Position.LEFT, Position.LEFT, Position.BOTTOM);
		agr(Position.LEFT, Position.TOP, Position.LEFT);
		agr(Position.LEFT, Position.RIGHT, Position.TOP);
		//left display bottom
		agr(Position.LEFT, Position.BOTTOM2_RIGHT, Position.RIGHT2_TOP);
		agr(Position.LEFT, Position.BOTTOM2_LEFT, Position.RIGHT2_BOTTOM);
		//left display left
		agr(Position.LEFT, Position.LEFT2_BOTTOM, Position.BOTTOM2_RIGHT);
		agr(Position.LEFT, Position.LEFT2_TOP, Position.BOTTOM2_LEFT);
		//left display top
		agr(Position.LEFT, Position.TOP2_LEFT, Position.LEFT2_BOTTOM);
		agr(Position.LEFT, Position.TOP2_RIGHT, Position.LEFT2_TOP);
		//left display right
		agr(Position.LEFT, Position.RIGHT2_TOP, Position.TOP2_LEFT);
		agr(Position.LEFT, Position.RIGHT2_BOTTOM, Position.TOP2_RIGHT);

		//right display
		agr(Position.RIGHT, Position.BOTTOM, Position.LEFT);
		agr(Position.RIGHT, Position.LEFT, Position.TOP);
		agr(Position.RIGHT, Position.TOP, Position.RIGHT);
		agr(Position.RIGHT, Position.RIGHT, Position.BOTTOM);
		//right display bottom
		agr(Position.RIGHT, Position.BOTTOM2_RIGHT, Position.LEFT2_BOTTOM);
		agr(Position.RIGHT, Position.BOTTOM2_LEFT, Position.LEFT2_TOP);
		//right display left
		agr(Position.RIGHT, Position.LEFT2_BOTTOM, Position.TOP2_LEFT);
		agr(Position.RIGHT, Position.LEFT2_TOP, Position.TOP2_RIGHT);
		//right display top
		agr(Position.RIGHT, Position.TOP2_LEFT, Position.RIGHT2_TOP);
		agr(Position.RIGHT, Position.TOP2_RIGHT, Position.RIGHT2_BOTTOM);
		//right display right
		agr(Position.RIGHT, Position.RIGHT2_TOP, Position.BOTTOM2_RIGHT);
		agr(Position.RIGHT, Position.RIGHT2_BOTTOM, Position.BOTTOM2_LEFT);

		//top display
		agr(Position.TOP, Position.BOTTOM, Position.TOP);
		agr(Position.TOP, Position.LEFT, Position.RIGHT);
		agr(Position.TOP, Position.TOP, Position.BOTTOM);
		agr(Position.TOP, Position.RIGHT, Position.LEFT);
		//top display bottom
		agr(Position.TOP, Position.BOTTOM2_RIGHT, Position.TOP2_LEFT);
		agr(Position.TOP, Position.BOTTOM2_LEFT, Position.TOP2_RIGHT);
		//top display left
		agr(Position.TOP, Position.LEFT2_BOTTOM, Position.RIGHT2_TOP);
		agr(Position.TOP, Position.LEFT2_TOP, Position.RIGHT2_BOTTOM);
		//top display top
		agr(Position.TOP, Position.TOP2_LEFT, Position.BOTTOM2_RIGHT);
		agr(Position.TOP, Position.TOP2_RIGHT, Position.BOTTOM2_LEFT);
		//top display right
		agr(Position.TOP, Position.RIGHT2_TOP, Position.LEFT2_BOTTOM);
		agr(Position.TOP, Position.RIGHT2_BOTTOM, Position.LEFT2_TOP);
	}

	private void agr(int d, int p, int r) {
		Assert.assertSame(" (display: " + d + " position: " + p + ") ", r, Position.getRotated(p, d));
	}

	public static class factor {
		public void aop(int p, int r) {
			Assert.assertSame("position: " + p, r, Position.factor.ofPosition(p));
		}

		@Test
		public void ofPosition() {
			aop(Position.BOTTOM, Position.factor.FULL);
			aop(Position.LEFT, Position.factor.FULL);
			aop(Position.TOP, Position.factor.FULL);
			aop(Position.RIGHT, Position.factor.FULL);

			aop(Position.BOTTOM2_LEFT, Position.factor.HALF);
			aop(Position.BOTTOM2_RIGHT, Position.factor.HALF);
			aop(Position.LEFT2_BOTTOM, Position.factor.HALF);
			aop(Position.LEFT2_TOP, Position.factor.HALF);
			aop(Position.TOP2_LEFT, Position.factor.HALF);
			aop(Position.TOP2_RIGHT, Position.factor.HALF);
			aop(Position.RIGHT2_TOP, Position.factor.HALF);
			aop(Position.RIGHT2_BOTTOM, Position.factor.HALF);

			aop(Position.BOTTOM3_LEFT, Position.factor.THIRD);
			aop(Position.BOTTOM3_CENTER, Position.factor.THIRD);
			aop(Position.BOTTOM3_RIGHT, Position.factor.THIRD);
			aop(Position.LEFT3_BOTTOM, Position.factor.THIRD);
			aop(Position.LEFT3_CENTER, Position.factor.THIRD);
			aop(Position.LEFT3_TOP, Position.factor.THIRD);
			aop(Position.TOP3_LEFT, Position.factor.THIRD);
			aop(Position.TOP3_CENTER, Position.factor.THIRD);
			aop(Position.TOP3_RIGHT, Position.factor.THIRD);
			aop(Position.RIGHT3_TOP, Position.factor.THIRD);
			aop(Position.RIGHT3_CENTER, Position.factor.THIRD);
			aop(Position.RIGHT3_BOTTOM, Position.factor.THIRD);

			aop(Position.BOTTOM4_LEFT, Position.factor.QUARTER);
			aop(Position.BOTTOM4_CLEFT, Position.factor.QUARTER);
			aop(Position.BOTTOM4_CRIGHT, Position.factor.QUARTER);
			aop(Position.BOTTOM4_RIGHT, Position.factor.QUARTER);
			aop(Position.LEFT4_BOTTOM, Position.factor.QUARTER);
			aop(Position.LEFT4_CBOTTOM, Position.factor.QUARTER);
			aop(Position.LEFT4_CTOP, Position.factor.QUARTER);
			aop(Position.LEFT4_TOP, Position.factor.QUARTER);
			aop(Position.TOP4_LEFT, Position.factor.QUARTER);
			aop(Position.TOP4_CLEFT, Position.factor.QUARTER);
			aop(Position.TOP4_CRIGHT, Position.factor.QUARTER);
			aop(Position.TOP4_RIGHT, Position.factor.QUARTER);
			aop(Position.RIGHT4_TOP, Position.factor.QUARTER);
			aop(Position.RIGHT4_CTOP, Position.factor.QUARTER);
			aop(Position.RIGHT4_CBOTTOM, Position.factor.QUARTER);
			aop(Position.RIGHT4_BOTTOM, Position.factor.QUARTER);
		}
	}

	public static class gravity {
		public void agg(int p, int r) {
			Assert.assertSame("Position: " + p, r, Position.gravity.ofPosition(p));
		}

		@Test
		public void getGravity() {
			agg(Position.BOTTOM, Gravity.BOTTOM);
			agg(Position.LEFT, Gravity.LEFT);
			agg(Position.TOP, Gravity.TOP);
			agg(Position.RIGHT, Gravity.RIGHT);

			//BOTTOM
			//right
			agg(Position.BOTTOM2_RIGHT, Gravity.BOTTOM | Gravity.RIGHT);
			agg(Position.BOTTOM3_RIGHT, Gravity.BOTTOM | Gravity.RIGHT);
			agg(Position.BOTTOM4_RIGHT, Gravity.BOTTOM | Gravity.RIGHT);
			//left
			agg(Position.BOTTOM2_LEFT, Gravity.BOTTOM | Gravity.LEFT);
			agg(Position.BOTTOM3_LEFT, Gravity.BOTTOM | Gravity.LEFT);
			agg(Position.BOTTOM4_LEFT, Gravity.BOTTOM | Gravity.LEFT);
			//center
			agg(Position.BOTTOM3_CENTER, Gravity.BOTTOM | Gravity.CENTER);
			//center+
			agg(Position.BOTTOM4_CRIGHT, Gravity.BOTTOM | Gravity.CENTER | Gravity.RIGHT);
			agg(Position.BOTTOM4_CLEFT, Gravity.BOTTOM | Gravity.CENTER | Gravity.LEFT);

			//LEFT
			//bottom
			agg(Position.LEFT2_BOTTOM, Gravity.LEFT | Gravity.BOTTOM);
			agg(Position.LEFT3_BOTTOM, Gravity.LEFT | Gravity.BOTTOM);
			agg(Position.LEFT4_BOTTOM, Gravity.LEFT | Gravity.BOTTOM);
			//top
			agg(Position.LEFT2_TOP, Gravity.LEFT | Gravity.TOP);
			agg(Position.LEFT3_TOP, Gravity.LEFT | Gravity.TOP);
			agg(Position.LEFT4_TOP, Gravity.LEFT | Gravity.TOP);
			//center
			agg(Position.LEFT3_CENTER, Gravity.LEFT | Gravity.CENTER);
			//center+
			agg(Position.LEFT4_CBOTTOM, Gravity.LEFT | Gravity.CENTER | Gravity.BOTTOM);
			agg(Position.LEFT4_CTOP, Gravity.LEFT | Gravity.CENTER | Gravity.TOP);

			//TOP
			//left
			agg(Position.TOP2_LEFT, Gravity.TOP | Gravity.LEFT);
			agg(Position.TOP3_LEFT, Gravity.TOP | Gravity.LEFT);
			agg(Position.TOP4_LEFT, Gravity.TOP | Gravity.LEFT);
			//right
			agg(Position.TOP2_RIGHT, Gravity.TOP | Gravity.RIGHT);
			agg(Position.TOP3_RIGHT, Gravity.TOP | Gravity.RIGHT);
			agg(Position.TOP4_RIGHT, Gravity.TOP | Gravity.RIGHT);
			//center
			agg(Position.TOP3_CENTER, Gravity.TOP | Gravity.CENTER);
			//center+
			agg(Position.TOP4_CLEFT, Gravity.TOP | Gravity.CENTER | Gravity.LEFT);
			agg(Position.TOP4_CRIGHT, Gravity.TOP | Gravity.CENTER | Gravity.RIGHT);

			//LEFT
			//top
			agg(Position.RIGHT2_TOP, Gravity.RIGHT | Gravity.TOP);
			agg(Position.RIGHT3_TOP, Gravity.RIGHT | Gravity.TOP);
			agg(Position.RIGHT4_TOP, Gravity.RIGHT | Gravity.TOP);
			//bottom
			agg(Position.RIGHT2_BOTTOM, Gravity.RIGHT | Gravity.BOTTOM);
			agg(Position.RIGHT3_BOTTOM, Gravity.RIGHT | Gravity.BOTTOM);
			agg(Position.RIGHT4_BOTTOM, Gravity.RIGHT | Gravity.BOTTOM);
			//center
			agg(Position.RIGHT3_CENTER, Gravity.RIGHT | Gravity.CENTER);
			//center+
			agg(Position.RIGHT4_CTOP, Gravity.RIGHT | Gravity.CENTER | Gravity.TOP);
			agg(Position.RIGHT4_CBOTTOM, Gravity.RIGHT | Gravity.CENTER | Gravity.BOTTOM);
		}
	}

	public static class side {
		@Test
		public void ofPosition() {
			//bottom
			aop(Position.BOTTOM2_RIGHT, Position.BOTTOM);
			aop(Position.BOTTOM2_LEFT, Position.BOTTOM);

			//left
			aop(Position.LEFT2_BOTTOM, Position.LEFT);
			aop(Position.LEFT2_TOP, Position.LEFT);

			//top
			aop(Position.TOP2_LEFT, Position.TOP);
			aop(Position.TOP2_RIGHT, Position.TOP);

			//right
			aop(Position.RIGHT2_TOP, Position.RIGHT);
			aop(Position.RIGHT2_BOTTOM, Position.RIGHT);
		}

		private void aop(int p, int r) {
			Assert.assertSame(" (position: " + p + ") ", r, Position.side.ofPosition(r));
		}
	}

	public static class split {
		public void ao(int s, int f, int r) {
			Assert.assertSame("factor: " + f + " side:" + s, r, Position.split.inSide(s, f));
		}

		@Test
		public void inSide() {
			ao(Position.side.BOTTOM, Position.factor.FULL, Position.split.BOTTOM);
			ao(Position.side.LEFT, Position.factor.FULL, Position.split.LEFT);
			ao(Position.side.TOP, Position.factor.FULL, Position.split.TOP);
			ao(Position.side.RIGHT, Position.factor.FULL, Position.split.RIGHT);

			ao(Position.side.BOTTOM, Position.factor.HALF, Position.split.BOTTOM2);
			ao(Position.side.LEFT, Position.factor.HALF, Position.split.LEFT2);
			ao(Position.side.TOP, Position.factor.HALF, Position.split.TOP2);
			ao(Position.side.RIGHT, Position.factor.HALF, Position.split.RIGHT2);

			ao(Position.side.BOTTOM, Position.factor.THIRD, Position.split.BOTTOM3);
			ao(Position.side.LEFT, Position.factor.THIRD, Position.split.LEFT3);
			ao(Position.side.TOP, Position.factor.THIRD, Position.split.TOP3);
			ao(Position.side.RIGHT, Position.factor.THIRD, Position.split.RIGHT3);

			ao(Position.side.BOTTOM, Position.factor.QUARTER, Position.split.BOTTOM4);
			ao(Position.side.LEFT, Position.factor.QUARTER, Position.split.LEFT4);
			ao(Position.side.TOP, Position.factor.QUARTER, Position.split.TOP4);
			ao(Position.side.RIGHT, Position.factor.QUARTER, Position.split.RIGHT4);
		}
	}
}
