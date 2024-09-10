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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/



package org.eclipse.papyrus.uml.diagram.profile.custom.helper;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.diagram.common.helper.ElementHelper;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfilePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfilePackageableElementCompartmentEditPartTN;
import org.eclipse.papyrus.uml.service.types.ui.util.ExtensionHelper;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.ExtensionEnd;
import org.eclipse.uml2.uml.Profile;

/**
 * Helper for Metaclasses
 *
 * @author Vincent Lorenzo
 *
 */
public class MetaclassHelper extends ElementHelper {

	/**
	 * This parameter is for tests only.
	 * When present in request parameters, the value for this key will be silently
	 * returned from the code that normally opens the dialog.
	 * It allows to emulate different user actions in tests, and should reduce the
	 * usage of @InteractiveTest annotation.
	 */
	private static final String PARAM_SUPPRESS_DIALOG_WITH_VALUE = MetaclassHelper.class.getName() + ":" + "ImportElementSelectionDialogResult";

	public static boolean shouldSuppressDialog(IEditCommandRequest request) {
		return request.getParameters().containsKey(PARAM_SUPPRESS_DIALOG_WITH_VALUE);
	}

	public static boolean shouldSuppressDialog(CreateViewRequest request) {
		return request.getExtendedData().containsKey(PARAM_SUPPRESS_DIALOG_WITH_VALUE);
	}

	@SuppressWarnings("unchecked")
	public static List<Object> getSuppressedDialogResult(IEditCommandRequest request) {
		Object result = request.getParameter(PARAM_SUPPRESS_DIALOG_WITH_VALUE);
		return result == null ? null : (List<Object>) result;
	}

	@SuppressWarnings("unchecked")
	public static void setupSuppressDialogRequest(CreateViewRequest request, List<Object> importedElements) {
		request.getExtendedData().put(PARAM_SUPPRESS_DIALOG_WITH_VALUE, importedElements);
	}

	/**
	 *
	 * @param metaclassContainer
	 * @return the uml profile if it find it
	 *         null if not
	 */

	static public Profile getParentProfile(EditPart metaclassContainer) {
		Profile parentProfile = null;
		EditPart profileEP = null;
		if ((metaclassContainer instanceof ProfilePackageableElementCompartmentEditPartTN) || (metaclassContainer instanceof ProfilePackageableElementCompartmentEditPartCN)) {
			profileEP = metaclassContainer.getParent();
			Object shape = profileEP.getModel();
			if (shape instanceof Node) {
				EObject obj = ((Node) shape).getElement();
				if (obj instanceof Profile) {
					// parentProfile = (Profile)((Shape)profileEP.getModel());
					parentProfile = (Profile) obj;
				}
			}
			// /!\ ModelEditPart is the diagram! (ID=1000)
		} else if (metaclassContainer instanceof ProfileDiagramEditPart) {
			Object objectDiag = metaclassContainer.getModel();
			if (objectDiag instanceof Diagram) {
				if (((Diagram) objectDiag).getElement() instanceof Profile) {
					parentProfile = (Profile) ((Diagram) objectDiag).getElement();
				}
			}
		}

		return parentProfile;
	}

	/**
	 * Returns the command to destroy the metaclass view and the associated {@link ElementImport}.
	 *
	 * @param metaclass
	 *            the metaclass to destroy
	 * @param parentProfile
	 *            the parent Profile
	 * @return
	 *         the command to destroy the metaclass view, the corresponding {@link ElementImport}, the {@link Extension}, the {@link ExtensionEnd} and
	 *         the associated properties
	 *
	 */
	static public Command getDestroyMetaclassCommand(org.eclipse.uml2.uml.Class metaclass, EObject parentProfile) {
		CompoundCommand cc = new CompoundCommand("Destroy Metaclass"); //$NON-NLS-1$

		String qName = metaclass.getQualifiedName();

		/*
		 * get the root profile
		 */

		EObject rootProfile = parentProfile;
		while (parentProfile.eContainer() != null) {
			rootProfile = parentProfile.eContainer();
			parentProfile = parentProfile.eContainer();
		}

		/*
		 * Destroy the ElementImport
		 */
		EList<ElementImport> importedElements = ((Profile) rootProfile).getElementImports();
		for (ElementImport elementImport : importedElements) {
			if (elementImport.getImportedElement().getQualifiedName().equals(qName)) {
				DestroyElementRequest destroyImportedElementRequest = new DestroyElementRequest(elementImport, false);
				DestroyElementCommand destroyImportedElementCommand = new DestroyElementCommand(destroyImportedElementRequest);
				if (destroyImportedElementCommand != null && destroyImportedElementCommand.canExecute()) {
					cc.add(new ICommandProxy(destroyImportedElementCommand));

				}
			}
		}

		/*
		 * Destroy the extensions
		 */
		cc.add(ExtensionHelper.getDestroyExtensionCommand(metaclass));

		return cc;
	}



}
