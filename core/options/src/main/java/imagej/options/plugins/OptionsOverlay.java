//
// OptionsOverlay.java
//

/*
ImageJ software for multidimensional image processing and analysis.

Copyright (c) 2010, ImageJDev.org.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the names of the ImageJDev.org developers nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package imagej.options.plugins;

import imagej.ext.plugin.Menu;
import imagej.ext.plugin.Parameter;
import imagej.ext.plugin.Plugin;
import imagej.options.OptionsPlugin;

/**
 * Runs the Image::Overlay::OverlayOptions dialog.
 * 
 * @author Barry DeZonia
 */
@Plugin(type = OptionsPlugin.class, menu = {
	@Menu(label = "Image", mnemonic = 'i'),
	@Menu(label = "Overlay", mnemonic = 'o'),
	@Menu(label = "Overlay Options...") })
public class OptionsOverlay extends OptionsPlugin {

	@Parameter(label = "Stroke Color", choices = { "red", "green", "blue",
		"magenta", "cyan", "yellow", "orange", "black", "white" })
	private String strokeColor = "yellow";

	@Parameter(label = "Width")
	private float width = 1;

	@Parameter(label = "Fill Color", choices = { "none", "red", "green",
		"blue", "magenta", "cyan", "yellow", "orange", "black", "white" })
	private String fillColor = "none";

	// -- OptionsScript methods --

	public OptionsOverlay() {
		load(); // NB: Load persisted values *after* field initialization.
	}
	
	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(final String color) {
		this.strokeColor = color;
	}

	public float getWidth() {
		return width;
	}
	
	public void setWidth(final float width) {
		this.width = width;
	}
	
	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(final String color) {
		this.fillColor = color;
	}
}