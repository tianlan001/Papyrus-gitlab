/*****************************************************************************
 * Copyright (c) 2008, 2009 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Yann TANGUY (CEA LIST) yann.tanguy@cea.fr
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.parsers;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Property;

/**
 * This the parser in charge of editing and displaying properties in Papyrus. For the edition of
 * properties, a dialog box is opened
 *
 * @author Patrick Tessier
 */
public class PropertyParser implements IParser {

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public String getEditString(final IAdaptable element, int flags) {
		EObject e = element.getAdapter(EObject.class);
		if (e != null) {
			final Property property = (Property) e;
			return UMLLabelInternationalization.getInstance().getLabel(property);
		}
		return "";
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		final Property property = (Property) element.getAdapter(EObject.class);
		final String result = newString;
		final TransactionalEditingDomain editingDomain;
		try {
			editingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(property);
		} catch (ServiceException ex) {
			return null;
		}

		AbstractTransactionalCommand tc = new AbstractTransactionalCommand(editingDomain, "Edit Property", (List<?>) null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				SafeRunnable.run(new SafeRunnable() {

					@Override
					public void run() {
						RecordingCommand rc = new RecordingCommand(getEditingDomain()) {

							@Override
							protected void doExecute() {
								if (InternationalizationPreferencesUtils.getInternationalizationPreference(property) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(property)) {
									UMLLabelInternationalization.getInstance().setLabel(property, result, null);
								} else {
									property.setName(result);
								}
							}
						};
						getEditingDomain().getCommandStack().execute(rc);
					}
				});
				return CommandResult.newOKCommandResult();
			}
		};
		return tc;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		return "<default>";
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return false;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return new ParserEditStatus(UMLDiagramEditorPlugin.ID, IStatus.OK, "");
	}
}
