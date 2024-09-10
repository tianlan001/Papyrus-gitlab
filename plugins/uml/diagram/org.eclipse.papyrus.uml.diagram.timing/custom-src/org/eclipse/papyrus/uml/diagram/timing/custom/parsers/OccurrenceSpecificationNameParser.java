/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.Fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.parsers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.timing.custom.Messages;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.OccurrenceSpecificationUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Used to get and set the name of an OccurrenceSpecification. An OccurrenceSpecification has a default name that
 * indicates what state transition it represents (when the OccurrenceSpecification is a state change). Changing the name
 * of such an OccurrenceSpecification removes the "autogenerated name" EAnnotation.
 */
public class OccurrenceSpecificationNameParser implements IParser {

	@Override
	public String getEditString(final IAdaptable adaptable, final int flags) {
		final OccurrenceSpecification occurrenceSpecification = getOccurrenceSpecification(adaptable);
		if (occurrenceSpecification == null) {
			return ""; //$NON-NLS-1$
		}
		return nonNullString(UMLLabelInternationalization.getInstance().getLabel(occurrenceSpecification));
	}

	@Override
	public IParserEditStatus isValidEditString(final IAdaptable element, final String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

	@Override
	public ICommand getParseCommand(final IAdaptable adaptable, final String newString, final int flags) {
		final OccurrenceSpecification occurrenceSpecification = getOccurrenceSpecification(adaptable);
		if (occurrenceSpecification == null) {
			return UnexecutableCommand.INSTANCE;
		}
		final TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(occurrenceSpecification);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		return new AbstractTransactionalCommand(editingDomain, Messages.OccurrenceSpecificationNameParser_SetOccurrenceSpecificationName, null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				if (InternationalizationPreferencesUtils.getInternationalizationPreference(occurrenceSpecification) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(occurrenceSpecification)) {
					UMLLabelInternationalization.getInstance().setLabel(occurrenceSpecification, newString, null);
				} else {
					occurrenceSpecification.setName(newString);
				}
				OccurrenceSpecificationUtils.setAutogeneratedName(occurrenceSpecification, false);
				return CommandResult.newOKCommandResult();
			}
		};
	}

	@Override
	public String getPrintString(final IAdaptable adaptable, final int flags) {
		final OccurrenceSpecification occurrenceSpecification = getOccurrenceSpecification(adaptable);
		if (occurrenceSpecification == null || OccurrenceSpecificationUtils.isAutogeneratedName(occurrenceSpecification)) {
			// don't display the auto-generated name (pollutes the view without any benefit)
			return ""; //$NON-NLS-1$
		}
		return UMLLabelInternationalization.getInstance().getLabel(occurrenceSpecification);
	}

	@Override
	public boolean isAffectingEvent(final Object event, final int flags) {
		return true;
	}

	@Override
	public IContentAssistProcessor getCompletionProcessor(final IAdaptable element) {
		return null;
	}

	private static OccurrenceSpecification getOccurrenceSpecification(final IAdaptable adaptable) {
		final Object adapter = adaptable.getAdapter(EObject.class);
		if (adapter instanceof OccurrenceSpecification) {
			return (OccurrenceSpecification) adapter;
		}
		return null;
	}

	private static String nonNullString(final String string) {
		if (string == null) {
			return ""; //$NON-NLS-1$
		}
		return string;
	}
}
