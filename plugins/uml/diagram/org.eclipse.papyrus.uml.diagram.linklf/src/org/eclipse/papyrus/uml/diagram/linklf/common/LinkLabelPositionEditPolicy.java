/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.linklf.common;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusLabelEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.locator.PapyrusLabelLocator;

public class LinkLabelPositionEditPolicy extends AbstractEditPolicy {

	public static final String KEY = "LinkLabelPositionEditPolicy"; //$NON-NLS-1$

	@Override
	public PapyrusLabelEditPart getHost() {
		return (PapyrusLabelEditPart)super.getHost();
	}

	private View getView() {
		return getHost().getNotationView();
	}

	@Override
	public Command getCommand(Request request) {
		if (!understandsRequest(request)) {
			return null;
		}
		NamedStyle style = getView().getNamedStyle(NotationPackage.eINSTANCE.getBooleanValueStyle(), PapyrusLabelLocator.IS_UPDATED_POSITION); 
		Boolean updated = style == null ? false : (Boolean) style.eGet(NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue());
		if (updated) {
			return null;
		}

		Point refOldPoint = getHost().getBaseReferencePoint();
		Point refNewPoint = getHost().getUpdatedRefencePoint();

		Point moveDelta = new Point(refOldPoint.x - refNewPoint.x, refOldPoint.y - refNewPoint.y);
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);

		req.setMoveDelta(moveDelta);
		req.setEditParts(getHost());
		req.setSizeDelta(new Dimension());

		View view = getHost().getNotationView();
		SetBooleanStyleTransactionalCommand cmd = new SetBooleanStyleTransactionalCommand(getHost().getEditingDomain(), view, true);
		CompoundCommand compound = new CompoundCommand();
		compound.add(getHost().getCommand(req));
		compound.add(GMFtoGEFCommandWrapper.wrap(cmd));
		return compound;
	}

	@Override
	public boolean understandsRequest(Request request) {
		if (request instanceof BendpointRequest) {
			return true;
		}
		return false;
	}

	private static class SetBooleanStyleTransactionalCommand extends AbstractTransactionalCommand {

		private final boolean myValue;

		private final View myView;

		public SetBooleanStyleTransactionalCommand(TransactionalEditingDomain domain, View view, boolean value) {
			super(domain, "Set boolean style", null); //$NON-NLS-1$
			myValue = value;
			myView = view;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			NamedStyle valueStyle = myView.getNamedStyle(NotationPackage.eINSTANCE.getBooleanValueStyle(), PapyrusLabelLocator.IS_UPDATED_POSITION);
			boolean createStyle = valueStyle == null;
			if (createStyle) {
				valueStyle = createStyle();
			}

			valueStyle.eSet(NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(), myValue);
			return new CommandResult(new Status(IStatus.OK, Activator.ID, "New style created")); //$NON-NLS-1$
		}

		protected NamedStyle createStyle() {
			NamedStyle result = (NamedStyle) myView.createStyle(NotationPackage.eINSTANCE.getBooleanValueStyle());
			result.setName(PapyrusLabelLocator.IS_UPDATED_POSITION);
			return result;
		}
	}
}
