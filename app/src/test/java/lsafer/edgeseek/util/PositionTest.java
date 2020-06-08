package lsafer.edgeseek.util;

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

	@Test
	public void getOrigin() {
		//bottom
		ago(Position.BOTTOM2_RIGHT, Position.BOTTOM);
		ago(Position.BOTTOM2_LEFT, Position.BOTTOM);

		//left
		ago(Position.LEFT2_BOTTOM, Position.LEFT);
		ago(Position.LEFT2_TOP, Position.LEFT);

		//top
		ago(Position.TOP2_LEFT, Position.TOP);
		ago(Position.TOP2_RIGHT, Position.TOP);

		//right
		ago(Position.RIGHT2_TOP, Position.RIGHT);
		ago(Position.RIGHT2_BOTTOM, Position.RIGHT);
	}

	private void agr(int d, int p, int r) {
		Assert.assertSame(" (display: " + d + " position: " + p + ") ", r, Position.getRotated(p, d));
	}

	private void ago(int p, int r) {
		Assert.assertSame(" (position: " + p + ") ", r, Position.getSide(r));
	}
}
