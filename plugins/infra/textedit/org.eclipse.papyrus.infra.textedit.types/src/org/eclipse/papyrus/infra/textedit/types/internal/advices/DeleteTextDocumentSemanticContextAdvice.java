/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.types.internal.advices;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage;

/**
 * This helper is in charge to delete the {@link TextDocument} associated to the deleted semantic element
 */
public class DeleteTextDocumentSemanticContextAdvice extends AbstractEditHelperAdvice {

	/**
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeDestroyDependentsCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		final EObject objectToDestroy = request.getElementToDestroy();
		if (objectToDestroy == null) {
			return null;
		}

		final ECrossReferenceAdapter crossReferencerAdapter = ECrossReferenceAdapter
				.getCrossReferenceAdapter(objectToDestroy);
		if (crossReferencerAdapter != null) {
			final Collection<Setting> settings = crossReferencerAdapter
					.getNonNavigableInverseReferences(objectToDestroy);
			CompositeCommand cmd = new CompositeCommand("Destroy TextDocument Command"); //$NON-NLS-1$
			for (Setting currentSetting : settings) {
				final EObject currentEObject = currentSetting.getEObject();
				final EStructuralFeature currentfeature = currentSetting.getEStructuralFeature();
				if (currentEObject instanceof TextDocument
						&& currentfeature == TextDocumentPackage.eINSTANCE
								.getTextDocument_SemanticContext()) {
					final TextDocument docTemplate = (TextDocument) currentEObject;
					final IElementEditService docTemplateProvider = ElementEditServiceUtils.getCommandProvider(docTemplate);

					// for a better behavior, we set the semantic context to null
					SetRequest setRequest = new SetRequest(docTemplate, TextDocumentPackage.eINSTANCE.getTextDocument_SemanticContext(), null);
					cmd.add(docTemplateProvider.getEditCommand(setRequest));

					// for a better behavior, we set the graphical context to null
					setRequest = new SetRequest(currentEObject, TextDocumentPackage.eINSTANCE.getTextDocument_GraphicalContext(), null);
					cmd.add(docTemplateProvider.getEditCommand(setRequest));

					final DestroyElementRequest destroyRequest = new DestroyElementRequest(docTemplate, false);
					cmd.add(docTemplateProvider.getEditCommand(destroyRequest));

				}
			}
			if (!cmd.isEmpty()) {
				return cmd;
			}
		}
		return null;
	}
}
