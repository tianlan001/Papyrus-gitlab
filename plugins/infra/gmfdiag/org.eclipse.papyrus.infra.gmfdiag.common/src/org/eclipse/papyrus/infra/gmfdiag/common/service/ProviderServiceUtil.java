/*****************************************************************************
 * Copyright (c) 2018, 2022 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation (Bug 533701)
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Bug 577845
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.common.service;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;

/**
 * <p>
 * Utility methods to use the {@link ViewProviderService} and {@link EditPolicyProviderService}
 * </p>
 *
 * @since 3.100
 */
public class ProviderServiceUtil {

	/**
	 * this method allows to identify if the editpart is associated to a Papyrus GMF Diagram (diagram.eContainer()==null)
	 *
	 * @param editPart
	 *            an edit part
	 * @return
	 *         <code>true</code> if the editPart probably concerns the Papyrus GMF Diagram
	 *         <code>false</code> otherwise (editPart is <code>null</code> or diagram.eContainer()!=null in case of a Papyrus Sirius Diagram)
	 *
	 */
	private static final boolean isPapyrusGMFPart(final EditPart editPart) {
		if (editPart != null) {
			final Object model = editPart.getModel();
			if (model instanceof View) {
				return isPapyrusGMFView((View) model);
			}
		}
		return false;
	}

	/**
	 * this method allows to identify if the view is associated to a Papyrus GMF Diagram (diagram.eContainer()==null)
	 *
	 * @param view
	 *            a view
	 * @return
	 *         <code>true</code> if the view probably concerns the Papyrus GMF Diagram
	 *         <code>false</code> otherwise (view is <code>null</code> or diagram.eContainer()!=null in case of a Papyrus Sirius Diagram)
	 *
	 */
	private static final boolean isPapyrusGMFView(final View view) {
		if (view != null) {
			final Diagram d = view.getDiagram();
			if (d != null) {
				return DiagramUtils.isPapyrusGMFDiagram(d);
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Tests if the given edit part is a Papyrus Edit Part, by testing if a Papyrus {@link ServicesRegistry}
	 * is present.
	 * </p>
	 *
	 * @param editPart
	 *            The edit part to test
	 * @return
	 *         <code>true</code> if this edit part is part of a Papyrus environment (Using a Papyrus {@link ServicesRegistry}), <code>false</code> otherwise
	 */
	public static boolean isPapyrusPart(EditPart editPart) {
		if (!isPapyrusGMFPart(editPart)) {
			return false;
		}
		try {
			return ServiceUtilsForEditPart.getInstance().getServiceRegistry(editPart) != null;
		} catch (Exception ex) {
			// Ignore & return
			return false;
		}
	}

	/**
	 * <p>
	 * Tests if the given View is a Papyrus View, by testing if a Papyrus {@link ServicesRegistry}
	 * is present.
	 * </p>
	 *
	 * @param view
	 *            The view to test
	 * @return
	 *         <code>true</code> if this view is part of a Papyrus environment (Using a Papyrus {@link ServicesRegistry}), <code>false</code> otherwise
	 */
	public static boolean isPapyrusView(View view) {
		if (!isPapyrusGMFView(view)) {
			return false;
		}
		try {
			return ServiceUtilsForEObject.getInstance().getServiceRegistry(view) != null;
		} catch (Exception ex) {
			// Ignore & return
			return false;
		}
	}

	/**
	 * <p>
	 * Helper method to use the {@link ViewProviderService}.
	 * </p>
	 * <p>
	 * This methods verifies that the edit part is a Papyrus Edit Part (Via {@link #isPapyrusPart(EditPart)}),
	 * and then calls {@link ViewProviderService#isEnabled(IViewProvider, EditPart)}
	 * </p>
	 *
	 * @param provider
	 * @param editPart
	 * @return
	 *
	 * @see ViewProviderService#isEnabled(IViewProvider, EditPart)
	 */
	public static boolean isEnabled(IViewProvider provider, View view) {
		if (isPapyrusView(view)) {
			try {
				ViewProviderService service = ServiceUtilsForEObject.getInstance().getService(ViewProviderService.class, view);
				return service.isEnabled(provider, view);
			} catch (Exception ex) {
				// Ignore & return
				return false;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Helper method to use the {@link EditPolicyProviderService}.
	 * </p>
	 * <p>
	 * This methods verifies that the edit part is a Papyrus Edit Part (Via {@link #isPapyrusPart(EditPart)}),
	 * and then calls {@link EditPolicyProviderService#isEnabled(IEditPolicyProvider, EditPart)}
	 * </p>
	 *
	 * @param provider
	 * @param editPart
	 * @return
	 *
	 * @see {@link EditPolicyProviderService#isEnabled(IEditPolicyProvider, EditPart)}
	 */
	public static boolean isEnabled(IEditPolicyProvider provider, EditPart editPart) {
		if (isPapyrusPart(editPart)) {
			try {
				EditPolicyProviderService service = ServiceUtilsForEditPart.getInstance().getService(EditPolicyProviderService.class, editPart);
				return service.isEnabled(provider, editPart);
			} catch (Exception ex) {
				// Ignore & return
				return false;
			}
		}

		return false;
	}

}
