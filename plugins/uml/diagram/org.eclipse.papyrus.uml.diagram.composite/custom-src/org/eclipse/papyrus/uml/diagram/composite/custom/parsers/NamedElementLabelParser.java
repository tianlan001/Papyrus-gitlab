/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *   Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Semantic Parser for {@link NamedElement} name.
 */
public class NamedElementLabelParser implements ISemanticParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {
		String editString = "";

		EObject eObject = EMFHelper.getEObject(element);
		if ((eObject != null) && (eObject instanceof NamedElement)) {
			NamedElement semElement = (NamedElement) eObject;
			if (semElement.isSetName()) {
				editString = UMLLabelInternationalization.getInstance().getLabel(semElement);
			}
		}
		return editString;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {

		ICommand command = UnexecutableCommand.INSTANCE;

		EObject objectToEdit = EMFHelper.getEObject(element);
		if (objectToEdit == null) {
			return UnexecutableCommand.INSTANCE;
		}

		try {
			if(InternationalizationPreferencesUtils.getInternationalizationPreference(objectToEdit) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML((NamedElement)objectToEdit)){
				final ModelSet modelSet = (ModelSet)objectToEdit.eResource().getResourceSet();
				command = new EMFtoGMFCommandWrapper(UMLLabelInternationalization.getInstance().getSetLabelCommand(modelSet.getTransactionalEditingDomain(), (NamedElement)objectToEdit, newString, null));
			}else{
				IClientContext context = TypeContext.getContext(objectToEdit);
				command = ElementEditServiceUtils.getEditServiceProvider(context).getEditService(objectToEdit).getEditCommand(new SetRequest(objectToEdit, UMLPackage.eINSTANCE.getNamedElement_Name(), newString));
			}
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		return command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		String result = "";
		EObject eObject = EMFHelper.getEObject(element);

		if (eObject instanceof NamedElement) {
			return UMLLabelInternationalization.getInstance().getLabel((NamedElement) eObject);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {

		if (event instanceof Notification) {
			Object feature = ((Notification) event).getFeature();
			if (feature instanceof EStructuralFeature) {
				return UMLPackage.eINSTANCE.getNamedElement_Name().equals(feature);
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EObject> getSemanticElementsBeingParsed(EObject element) {
		List<EObject> semanticElementsBeingParsed = new ArrayList<EObject>();
		semanticElementsBeingParsed.add(element);

		return semanticElementsBeingParsed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		return true;
	}

}
