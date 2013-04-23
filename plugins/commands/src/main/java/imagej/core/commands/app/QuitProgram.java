/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2013 Board of Regents of the University of
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

package imagej.core.commands.app;

import imagej.command.Command;
import imagej.command.ContextCommand;
import imagej.data.display.WindowService;
import imagej.menu.MenuConstants;
import imagej.ui.DialogPrompt;
import imagej.ui.UIService;

import org.scijava.app.StatusService;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Quits ImageJ.
 * 
 * @author Grant Harris
 * @author Barry DeZonia
 * @author Curtis Rueden
 */
@Plugin(type = Command.class, label = "Quit", iconPath = "/icons/commands/door_in.png", menu = {
	@Menu(label = MenuConstants.FILE_LABEL, weight = MenuConstants.FILE_WEIGHT,
		mnemonic = MenuConstants.FILE_MNEMONIC),
	@Menu(label = "Quit", weight = Double.MAX_VALUE, mnemonic = 'q',
		accelerator = "^Q") }, headless = true)
public class QuitProgram extends ContextCommand {

	public static final String MESSAGE = "Quit ImageJ?";

	@Parameter
	private StatusService statusService;

	@Parameter
	private WindowService windowService;

	@Parameter
	private UIService uiService;

	@Override
	public void run() {
		if (windowService != null && windowService.getOpenWindows().size() > 0) {
			if (!promptForQuit()) {
				return;
			}

			// TODO - save existing data
			// TODO - close windows
		}
		if (statusService != null) {
			statusService.showStatus("Quitting...");
		}
		getContext().dispose();
	}

	private boolean promptForQuit() {
		final DialogPrompt.Result result =
			uiService.showDialog(MESSAGE, "Quit",
				DialogPrompt.MessageType.QUESTION_MESSAGE,
				DialogPrompt.OptionType.YES_NO_OPTION);
		return result == DialogPrompt.Result.YES_OPTION;
	}

}
