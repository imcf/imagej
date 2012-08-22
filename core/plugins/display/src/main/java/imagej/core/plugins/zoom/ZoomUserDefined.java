/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2012 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package imagej.core.plugins.zoom;

import imagej.data.display.ImageCanvas;
import imagej.data.display.ImageDisplay;
import imagej.data.display.ImageDisplayService;
import imagej.ext.plugin.DynamicPlugin;
import imagej.ext.plugin.Menu;
import imagej.ext.plugin.Parameter;
import imagej.ext.plugin.Plugin;
import imagej.menu.MenuConstants;
import imagej.module.DefaultModuleItem;
import imagej.module.ItemIO;
import imagej.util.RealCoords;
import imagej.util.RealRect;

/**
 * Zooms in on the center of the image at the user-specified magnification
 * level.
 * 
 * @author Barry DeZonia
 */
@Plugin(label = "Set Zoom...", menu = {
	@Menu(label = MenuConstants.IMAGE_LABEL, weight = MenuConstants.IMAGE_WEIGHT,
		mnemonic = MenuConstants.IMAGE_MNEMONIC),
	@Menu(label = "Zoom", mnemonic = 'z'), @Menu(label = "Set...", weight = 6) },
	headless = true, initializer = "initAll")
public class ZoomUserDefined extends DynamicPlugin {

	// -- Constants --

	private static final String ZOOM = "zoomPercent";
	private static final String CTR_U = "centerU";
	private static final String CTR_V = "centerV";

	// -- Parameters --

	@Parameter
	private ImageDisplayService imageDisplayService;

	@Parameter(type = ItemIO.BOTH)
	private ImageDisplay display;

	@Parameter(label = "Zoom (%):", persist = false)
	private double zoomPercent;

	@Parameter(label = "X center:", persist = false)
	private double centerU;

	@Parameter(label = "Y center:", persist = false)
	private double centerV;

	// -- ZoomUserDefined methods --

	public ImageDisplayService getImageDisplayService() {
		return imageDisplayService;
	}

	public void setImageDisplayService(
		final ImageDisplayService imageDisplayService)
	{
		this.imageDisplayService = imageDisplayService;
	}

	public ImageDisplay getDisplay() {
		return display;
	}

	public void setDisplay(final ImageDisplay display) {
		this.display = display;
	}

	public double getZoomPercent() {
		return zoomPercent;
	}

	public void setZoomPercent(final double zoomPercent) {
		this.zoomPercent = zoomPercent;
	}

	public double getCenterU() {
		return centerU;
	}

	public void setCenterU(final double centerU) {
		this.centerU = centerU;
	}

	public double getCenterV() {
		return centerV;
	}

	public void setCenterV(final double centerV) {
		this.centerV = centerV;
	}

	// -- Runnable methods --

	@Override
	public void run() {
		display.getCanvas().setZoomAndCenter(zoomPercent / 100.0,
			new RealCoords(getCenterU(), getCenterV()));
	}

	// -- Initializers --

	protected void initAll() {
		initZoom();
		initCenter();
	}
 // -- Helper methods --

	private void initZoom() {
		final ImageCanvas canvas = display.getCanvas();

		@SuppressWarnings("unchecked")
		final DefaultModuleItem<Double> zoomItem =
			(DefaultModuleItem<Double>) getInfo().getInput(ZOOM);
		zoomItem.setMinimumValue(0.1);
		zoomItem.setMaximumValue(500000.0);
		setZoomPercent(100 * canvas.getZoomFactor());
	}

	private void initCenter() {
		final RealRect planeExtents = display.getPlaneExtents();
		final ImageCanvas canvas = display.getCanvas();
		final RealCoords panCenter = canvas.getPanCenter();

		@SuppressWarnings("unchecked")
		final DefaultModuleItem<Double> centerUItem =
			(DefaultModuleItem<Double>) getInfo().getInput(CTR_U);
		centerUItem.setMinimumValue(planeExtents.x);
		centerUItem.setMaximumValue(planeExtents.x + planeExtents.width);
		setCenterU(panCenter.x);

		@SuppressWarnings("unchecked")
		final DefaultModuleItem<Double> centerVItem =
			(DefaultModuleItem<Double>) getInfo().getInput(CTR_V);
		centerVItem.setMinimumValue(planeExtents.y);
		centerVItem.setMaximumValue(planeExtents.y + planeExtents.height);
		setCenterV(panCenter.y);
	}

}
