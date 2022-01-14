package org.jhotdraw.draw.liner;


import org.jhotdraw.geom.BezierPath;

/**
 * @see org.jhotdraw.geom.Geom#OUT_TOP
 */
public class OutTop2 extends Soutcode2 {
	public void lineout(BezierPath path) {
		path.get(2).moveTo(path.get(1).y[0], path.get(3).x[0]);
	}
}