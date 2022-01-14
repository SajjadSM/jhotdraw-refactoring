package org.jhotdraw.draw.liner;


import org.jhotdraw.geom.BezierPath;

/**
 * @see org.jhotdraw.geom.Geom#OUT_RIGHT
 */
public class OutRight2 extends Soutcode2 {
	public void lineout(BezierPath path) {
		path.get(2).moveTo(path.get(1).x[0], path.get(3).y[0]);
	}
}