package org.jhotdraw.samples.mini;


import javax.swing.JSlider;
import java.io.Serializable;

public class BezierDemoProduct implements Serializable {
	private javax.swing.JSlider zoomSlider;

	public javax.swing.JSlider getZoomSlider() {
		return zoomSlider;
	}

	public void setZoomSlider(javax.swing.JSlider zoomSlider) {
		this.zoomSlider = zoomSlider;
	}

	public double getZoomFactor() {
		return zoomSlider.getValue() / 100d;
	}

	public double getError() {
		double error = 2d / getZoomFactor();
		return error;
	}

	public double getSquaredError() {
		double error = getError();
		return error * error;
	}
}