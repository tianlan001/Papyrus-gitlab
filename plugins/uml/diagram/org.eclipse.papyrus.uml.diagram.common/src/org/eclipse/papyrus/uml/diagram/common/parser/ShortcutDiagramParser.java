/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier(CEA LIST) Patrick.Tessier@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.uml.diagram.common.Messages;

/**
 * parser to display the name of diagram in the case of a short cut
 *
 */
public class ShortcutDiagramParser implements IParser {

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {
		Diagram diagram = doAdapt(element);
		if (diagram == null || diagram.eResource() == null) {
			return Messages.DiagramNotFound;
		}

		return LabelInternationalization.getInstance().getDiagramLabel(diagram);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		Diagram diagram = doAdapt(element);
		if (diagram == null || diagram.eResource() == null) {
			return UnexecutableCommand.INSTANCE;
		}
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(diagram);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		ICommand command = null;
		if (InternationalizationPreferencesUtils.getInternationalizationPreference(diagram) && null != LabelInternationalization.getInstance().getDiagramLabel(diagram)) {
			final ModelSet modelSet = (ModelSet) diagram.eResource().getResourceSet();
			command = new EMFtoGMFCommandWrapper(LabelInternationalization.getInstance().getSetDiagramLabelCommand(modelSet.getTransactionalEditingDomain(), diagram, newString, null));
		} else {
			command = new CompositeTransactionalCommand(editingDomain, Messages.SetNameDiagram);
			SetRequest request = new SetRequest(diagram, NotationPackage.eINSTANCE.getDiagram_Name(), newString);
			command.compose(new SetValueCommand(request));
		}
		return command;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Diagram diagram = doAdapt(element);
		if (diagram == null || diagram.eResource() == null) {
			return Messages.DiagramNotFound;
		}

		return LabelInternationalization.getInstance().getDiagramLabel(diagram);
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
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {

		return null;
	}

	/**
	 * used to obtain the diagram element.
	 *
	 * @param element
	 *            the given IAdaptable
	 * @return the diagram or null if it can't be found.
	 */
	protected Diagram doAdapt(IAdaptable element) {
		Object obj = EMFHelper.getEObject(element);
		if (obj instanceof Diagram) {
			return (Diagram) obj;
		}
		return null;
	}
}
