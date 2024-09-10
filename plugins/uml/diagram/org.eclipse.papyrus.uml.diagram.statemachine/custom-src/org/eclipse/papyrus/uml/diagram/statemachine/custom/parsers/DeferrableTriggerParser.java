/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.util.TriggerUtil;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This class define the parser for the DeferrableTrigger feature of the State
 *
 */
public class DeferrableTriggerParser implements IParser, ISemanticParser {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String SPACE_STRING = " "; //$NON-NLS-1$

	/**
	 * @see org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#getSemanticElementsBeingParsed(org.eclipse.emf.ecore.EObject)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public List<Trigger> getSemanticElementsBeingParsed(EObject element) {
		if (element instanceof Trigger) {
			final Trigger trigger = ((Trigger) element);
			return Collections.singletonList(trigger);
		}
		return Collections.emptyList();
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#areSemanticElementsAffected(org.eclipse.emf.ecore.EObject, java.lang.Object)
	 *
	 * @param listener
	 * @param notification
	 * @return
	 */
	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		return true;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 *
	 * @param element
	 * @param flags
	 * @return
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {
		Object obj = element.getAdapter(Behavior.class);
		if (obj instanceof Trigger) {
			final Trigger trigger = ((Trigger) obj);
			return UMLLabelInternationalization.getInstance().getLabel(trigger);
		}
		return EMPTY_STRING;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 *
	 * @param element
	 * @param editString
	 * @return
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return new ParserEditStatus(org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin.ID, IStatus.OK, ""); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 *
	 * @param element
	 * @param newString
	 * @param flags
	 * @return
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		final Trigger trigger = (Trigger) ((EObjectAdapter) element).getRealObject();
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(trigger);
		final String newStringResult = newString;
		if (editingDomain != null && editingDomain instanceof TransactionalEditingDomain) {
			AbstractTransactionalCommand cmd = new AbstractTransactionalCommand((TransactionalEditingDomain) editingDomain, "Set new name in " + trigger.getName(), null) { //$NON-NLS-1$
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					if (InternationalizationPreferencesUtils.getInternationalizationPreference(trigger) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(trigger)) {
						UMLLabelInternationalization.getInstance().setLabel(trigger, newStringResult, null);
					} else {
						trigger.setName(newStringResult);
					}
					return CommandResult.newOKCommandResult();
				}
			};
			if (cmd != null && cmd.canExecute()) {
				return cmd;
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 *
	 * @param element
	 * @param flags
	 * @return
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Object obj = element.getAdapter(EObject.class);
		View view = element.getAdapter(View.class);
		if (obj instanceof Trigger) {
			Trigger trigger = (Trigger) obj;
			StringBuilder result = new StringBuilder();
			if (trigger.getEvent() != null) {
				result.append(TriggerUtil.getTextForTrigger(view, trigger));
			}
			result.append(SPACE_STRING);
			result.append(Messages.DeferrableTriggerParser_DEFER_KEYWORD);
			return result.toString();
		}
		return EMPTY_STRING;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent(java.lang.Object, int)
	 *
	 * @param event
	 * @param flags
	 * @return
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		if (event instanceof Notification) {
			int notificationType = ((Notification) event).getEventType();
			if (Notification.ADD == notificationType || Notification.SET == notificationType || Notification.REMOVE == notificationType) {
				Object feature = ((Notification) event).getFeature();
				if (feature instanceof EStructuralFeature) {
					EStructuralFeature eStrucFeature = (EStructuralFeature) feature;
					if (UMLPackage.Literals.STATE__DEFERRABLE_TRIGGER.equals(eStrucFeature)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

}
