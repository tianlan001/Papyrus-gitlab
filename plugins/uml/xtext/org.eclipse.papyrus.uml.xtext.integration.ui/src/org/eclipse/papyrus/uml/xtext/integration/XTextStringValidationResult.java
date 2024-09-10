/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENWO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.xtext.integration;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.validation.Issue;

/**
 *
 * This class allows to save the validation results of the string edited with Xtext in a {@link CommandResult}
 *
 * @since 2.1
 *
 */
public class XTextStringValidationResult {

	/**
	 * The diagnostics (errors) owned by the Xtex{@link XtextResource}tResource
	 */
	private List<Diagnostic> diagnostics;

	/**
	 * The issue created by the validation of the {@link XtextResource}
	 */
	private List<Issue> issues;

	/**
	 * the result of the parsing
	 */
	private IParseResult parseResult;

	/**
	 * the edited string
	 */
	private String stringToSet;

	/**
	 * the object impacted by the command execution (the edited object or the comment used to save an invalid string value for example)
	 */
	private Object editedObject;

	/**
	 * Constructor.
	 *
	 * @param diagnostics
	 * @param issues
	 * @param parseResult
	 */
	public XTextStringValidationResult(final Object editedObject, final String typedString, final List<Diagnostic> diagnostics, final List<Issue> issues, final IParseResult parseResult) {
		this.stringToSet = typedString;
		this.diagnostics = diagnostics;
		this.issues = issues;
		this.parseResult = parseResult;
	}

	/**
	 *
	 * @return
	 * 		the diagnostics owned by the {@link XtextResource}
	 */
	public List<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	/**
	 *
	 * @return
	 * 		the issues obtained by the {@link XtextResource} validation
	 */
	public List<Issue> getIssues() {
		return issues;
	}

	/**
	 *
	 * @return
	 * 		the result of the parsing
	 */
	public IParseResult getParseResult() {
		return parseResult;
	}

	/**
	 *
	 * @return
	 * 		the string typed by the user
	 */
	public String getTypedString() {
		return stringToSet;
	}

	/**
	 *
	 * @return
	 * 		the object edited by the command execution (the initial edited object or the comment created in case of invalid string for example)
	 */
	public Object getEditedObejct() {
		return this.editedObject;
	}

	/**
	 *
	 * @return
	 * 		<code>true</code> if the string was a valid string
	 */
	public boolean isValidString() {
		return getIssues().size() == 0 && getDiagnostics().size() == 0 && getParseResult().hasSyntaxErrors() == false;
	}
}