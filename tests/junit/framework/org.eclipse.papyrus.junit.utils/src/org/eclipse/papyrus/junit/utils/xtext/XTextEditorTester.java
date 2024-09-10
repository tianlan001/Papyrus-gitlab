/*****************************************************************************
 * Copyright (c) 2014, 2018, 2019 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - Bug 539293, Bug 552697
 *
 *****************************************************************************/
package org.eclipse.papyrus.junit.utils.xtext;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;

/**
 * @since 2.4
 */
public class XTextEditorTester<T extends EObject> {

	/**
	 * the XText editor configuration to use for the JUnit tests
	 */
	protected DefaultXtextDirectEditorConfiguration editor;

	/**
	 * the result of the executed command
	 */
	private CommandResult commandResult;

	public XTextEditorTester(final DefaultXtextDirectEditorConfiguration editor) {
		this.editor = editor;
	}

	public T parseText(final T initialElement, final String textToParse) throws Exception {
		IParser parser = editor.createParser(initialElement);
		ICommand parseCommand = parser.getParseCommand(new EObjectAdapter(initialElement), textToParse, 0);
		parseCommand.execute(new NullProgressMonitor(), null);
		this.commandResult = parseCommand.getCommandResult();
		return initialElement;
	}

	public T parseText(final EObject parentElement, final T initialElement, final String textToParse) throws Exception {
		IParser parser = editor.createParser(parentElement);
		ICommand parseCommand = parser.getParseCommand(new EObjectAdapter(initialElement), textToParse, 0);
		if (null != parseCommand) {
			parseCommand.execute(new NullProgressMonitor(), null);
		}

		this.commandResult = parseCommand.getCommandResult();
		return initialElement;
	}

	public String getInitialText(T element) {
		return this.editor.getTextToEdit(element);
	}

	public String getParentInitialText(final Object element) {
		return this.editor.getTextToEdit(element);
	}

	/**
	 *
	 * @return
	 *         the command result. This method must be called after the method parseText. If not, this method will return <code>null</code>;
	 *
	 * @since 1.3
	 */
	public CommandResult getCommandResult() {
		return this.commandResult;
	}
}
