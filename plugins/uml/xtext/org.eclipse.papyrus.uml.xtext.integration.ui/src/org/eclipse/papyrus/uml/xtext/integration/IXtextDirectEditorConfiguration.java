/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.xtext.integration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

/**
 * Specific direct editor confirguration for Xtext
 *
 * @since 2.2
 *
 */
public interface IXtextDirectEditorConfiguration extends ICustomDirectEditorConfiguration {

	/**
	 * @param semanticObject
	 *            the semantic element
	 * @return
	 *         the text to edit or the string value owned by the owned comment stereotyped <<TextualRepresentation>> when the previous edition set an invalid string
	 */
	String getTextToEditInternal(EObject semanticObject);

	/**
	 *
	 * Used when the edited object is not equal to the context element
	 *
	 * @return
	 *         the context element
	 */
	IContextElementProvider getContextProvider();

	/**
	 * @return
	 *         the injector to use to load the Xtext grammar
	 */
	Injector getInjector();

	/**
	 * @param semanticObject
	 * @param xtextObject
	 *            to get the string typed by the user, you can do NodeModelUtils.getNode(xtextObject).getText();
	 * @return
	 */
	ICommand getParseCommand(EObject semanticObject, EObject xtextObject);

	/**
	 *
	 * @param invalidString
	 *            the invalid string entered by the user
	 * @param semanticElement
	 *            the edited element
	 * @param parseResult
	 *            the result of the parsing
	 * @param diagnostics
	 *            the errors in the resource (provided by the EMF resource
	 * @param issues
	 *            the list if the {@link Issue} in the invalid string
	 * @return
	 *         the command to use when the string is invalid
	 */
	public ICommand createInvalidStringCommand(final String invalidString, final EObject semanticElement, final IParseResult parseResult, final List<Diagnostic> diagnostics, final List<Issue> issues);

}
