/******************************************************************************
 * Copyright (c) 2021 CEA LIST, Artal Technologies
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.Activator;
import org.eclipse.sirius.diagram.model.business.internal.spec.DNodeContainerSpec;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeContainerNameEditPart;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

/**
 * The Class XtextSiriusDNodeContainerNameEditPart.
 *
 * @author Yann Binot (Artal Technologies) <yann.binot@artal.fr>
 */
@SuppressWarnings("restriction")
public class XtextSiriusDNodeContainerNameEditPart extends DNodeContainerNameEditPart implements ITextAwareEditPart {

	/** The configuration. */
	protected IDirectEditorConfiguration configuration;

	/**
	 * Instantiates a new xtext sirius D node container name edit part.
	 *
	 * @param view the view
	 */
	public XtextSiriusDNodeContainerNameEditPart(View view) {
		super(view);
	}

	/**
	 * Perform direct edit request.
	 *
	 * @param request
	 *            the request
	 * @see org.eclipse.sirius.diagram.ui.internal.edit.parts.AbstractGeneratedDiagramNameEditPart#performDirectEditRequest(org.eclipse.gef.Request)
	 */

	@Override
	protected void performDirectEditRequest(final Request request) {

		EObject resolveSemanticElement = resolveSemanticElement();
		if (resolveSemanticElement instanceof DNodeContainerSpec) {
			EObject target = ((DNodeContainerSpec) resolveSemanticElement).getTarget();
			final String languagePreferred = Activator.getDefault().getPreferenceStore().getString(IDirectEditorsIds.EDITOR_FOR_ELEMENT + target.eClass().getInstanceClassName());
			if (languagePreferred != null && !languagePreferred.equals("")) { //$NON-NLS-1$
				configuration = DirectEditorsUtil.findEditorConfiguration(languagePreferred, target, this);
			} else {
				configuration = DirectEditorsUtil.findEditorConfiguration(IDirectEditorsIds.UML_LANGUAGE, target, this);
			}
			configuration.preEditAction(target);
		}

		if (configuration instanceof ICustomDirectEditorConfiguration) {
			setManager(((ICustomDirectEditorConfiguration) configuration).createDirectEditManager(this));
			initializeDirectEditManager(request);
		}
	}

	/**
	 * Initialize direct edit manager.
	 *
	 * @param request
	 *            the request
	 */
	protected void initializeDirectEditManager(final Request request) {
		// initialize the direct edit manager
		try {
			getEditingDomain().runExclusive(new Runnable() {
				@Override
				public void run() {
					if (isActive() && isEditable()) {
						if (request.getExtendedData()
								.get(RequestConstants.REQ_DIRECTEDIT_EXTENDEDDATA_INITIAL_CHAR) instanceof Character) {
							Character initialChar = (Character) request.getExtendedData()
									.get(RequestConstants.REQ_DIRECTEDIT_EXTENDEDDATA_INITIAL_CHAR);
							performDirectEdit(initialChar.charValue());
						} else {
							performDirectEdit();
						}
					}
				}
			});
		} catch (InterruptedException e) {
			Activator.log.error("Unexpected Error",e); //$NON-NLS-1$
		}
	}

	/**
	 * Perform direct edit.
	 *
	 * @param initialCharacter
	 *            the initial character
	 */
	protected void performDirectEdit(char initialCharacter) {
		if (getManager() instanceof TextDirectEditManager) {
			((TextDirectEditManager) getManager()).show(initialCharacter);
		} else {
			performDirectEdit();
		}
	}

	/**
	 * Perform direct edit.
	 *
	 * @see org.eclipse.sirius.diagram.ui.internal.edit.parts.AbstractGeneratedDiagramNameEditPart#performDirectEdit()
	 */

	@Override
	protected void performDirectEdit() {
		BusyIndicator.showWhile(Display.getDefault(), new java.lang.Runnable() {

			@Override
			public void run() {
				getManager().show();
			}
		});
	}

	
}
