/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.modelexplorer.directeditor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Specific Parser for the Named Element.
 */
public class NamedElementDirectEditorParser implements IParser {

	/**
	 * Name of the current Name Element.
	 */
	private String textToEdit;

	/**
	 * Determinates if this is a label modification.
	 */
	private boolean labelModification;

	/**
	 * Constructor.
	 *
	 * @param textToEdit
	 *            The text to edit.
	 */
	public NamedElementDirectEditorParser(final String textToEdit) {
		this(textToEdit, false);
	}

	/**
	 * Constructor.
	 *
	 * @param textToEdit
	 *            The text to edit.
	 * @param labelModification
	 *            Determinates if this is a label modification.
	 */
	public NamedElementDirectEditorParser(final String textToEdit, final boolean labelModification) {
		this.textToEdit = textToEdit;
		this.labelModification = labelModification;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEditString(final IAdaptable element, final int flags) {
		return textToEdit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getParseCommand(final IAdaptable element, final String newString, final int flags) {
		CompositeCommand compositeCommand = new CompositeCommand("Rename"); //$NON-NLS-1$

		EObject eObjectElement = element.getAdapter(EObject.class);

		if (eObjectElement instanceof NamedElement) {
			if (null != newString && !newString.isEmpty()) {
				if (labelModification) {
					compositeCommand.add(
							new EMFtoGMFCommandWrapper(UMLLabelInternationalization.getInstance().getSetLabelCommand(((ModelSet) eObjectElement.eResource().getResourceSet()).getTransactionalEditingDomain(), (NamedElement) eObjectElement, newString, null)));
				} else {
					SetRequest setRequest = new SetRequest(eObjectElement, UMLPackage.eINSTANCE.getNamedElement_Name(), newString);

					IElementEditService provider = ElementEditServiceUtils.getCommandProvider(eObjectElement);
					ICommand editCommand = provider.getEditCommand(setRequest);

					compositeCommand.add(editCommand);
				}
			}
		}

		return compositeCommand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(final IAdaptable element, final int flags) {
		return textToEdit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAffectingEvent(final Object event, final int flags) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(final IAdaptable element) {
		// Not used
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParserEditStatus isValidEditString(final IAdaptable element, final String editString) {
		// Not used
		return null;
	}
}
