/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import static org.eclipse.papyrus.uml.diagram.common.Activator.log;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.FEATURE_TO_SET_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.util.EditPartUtil;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.tools.listeners.StereotypeElementListener.StereotypeExtensionNotification;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * Edit policy for stereotype property reference edge which delete edge when related Profile or Stereotype are unapplyed.
 *
 * @author Mickael ADAM
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeCleaningEditPolicy extends AbstractEditPolicy implements EditPolicy, NotificationListener {

	/** The feature related to the edge. */
	private String featureToSet;

	/** The stereotype qualify name of the source which contains the feature */
	private String stereotypeQualifyName;

	/**
	 * The Key of this edit policy.
	 */
	final public static String EDIT_POLICY_KEY = "REMOVE_INVALID_STEREOTYPE_REFERENCE_EDGE_EDIT_POLICY";//$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#activate()
	 */
	@Override
	public void activate() {
		// listen source to know if stereotype
		Edge edge = (Edge) getHost().getModel();

		EAnnotation eAnnotation = ((View) edge).getEAnnotation(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
		if (null != eAnnotation) {
			stereotypeQualifyName = eAnnotation.getDetails().get(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY);
			featureToSet = eAnnotation.getDetails().get(FEATURE_TO_SET_ANNOTATION_KEY);
		}

		DiagramEventBroker diagramEventBroker = getDiagramEventBroker();
		diagramEventBroker.addNotificationListener(getSourceElement(), this);
		diagramEventBroker.addNotificationListener(getTargetElement(), this);

		if (null == stereotypeQualifyName || null == featureToSet || !checkSourceStereotype() || !checkTargetStereotype()) {
			destroyView();
		}

	}

	/**
	 * Gets the diagram event broker from the editing domain.
	 *
	 * @return the diagram event broker
	 */
	protected DiagramEventBroker getDiagramEventBroker() {
		TransactionalEditingDomain theEditingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		if (theEditingDomain != null) {
			return DiagramEventBroker.getInstance(theEditingDomain);
		}
		return null;
	}

	/**
	 * Get the target element.
	 */
	protected Element getTargetElement() {
		Edge edge = (Edge) getHost().getModel();
		View target = edge.getTarget();
		return null == target ? null : (Element) target.getElement();
	}

	/**
	 * Get the source element.
	 */
	protected Element getSourceElement() {
		Edge edge = (Edge) getHost().getModel();
		View source = edge.getSource();
		return null == source ? null : (Element) source.getElement();
	}

	/**
	 * Check the target stereotype.
	 */
	private boolean checkTargetStereotype() {
		boolean targetOk = false;

		Stereotype sourceStereotype = getSourceElement().getApplicableStereotype(stereotypeQualifyName);
		Property attribute = sourceStereotype.getAttribute(featureToSet, null);
		if (null != attribute) {
			Type targetType = attribute.getType();
			if (targetType instanceof Stereotype) {
				// feature as stereotype reference
				targetOk = ElementUtil.hasStereotypeApplied(getTargetElement(), targetType.getQualifiedName());
			} else {
				targetOk = true;
			}
		}

		return targetOk;
	}

	/**
	 * Check the source stereotype.
	 */
	public boolean checkSourceStereotype() {
		return null != getSourceElement() && ElementUtil.hasStereotypeApplied(getSourceElement(), stereotypeQualifyName);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(final Notification notification) {

		if (notification instanceof StereotypeExtensionNotification) {
			if (getSourceElement() != null && getTargetElement() != null) {
				if (null == stereotypeQualifyName || null == featureToSet || !checkSourceStereotype() || !checkTargetStereotype()) {
					destroyView();
				}
			}
		}
	}

	/**
	 * Returns a {@link Command} to delete the supplied {@link View}.
	 *
	 * @param view
	 *            view to delete
	 * @return the command that destroys the specified view
	 */
	protected Command getDeleteViewCommand(final View view) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		return new ICommandProxy(new DeleteCommand(editingDomain, view));
	}

	/**
	 * Destroy obsolete view.
	 */
	protected void destroyView() {
		EditPart host = getHost();
		View view = (View) host.getModel();
		if (host instanceof IGraphicalEditPart) {
			Command deleteViewCommand = getDeleteViewCommand(view);
			if (deleteViewCommand != null && deleteViewCommand.canExecute()) {
				executeCommand(deleteViewCommand);
			}
		}
	}

	/**
	 * Executes the supplied command inside an <code>unchecked action</code>
	 *
	 * @param cmd
	 *            command that can be executed (i.e., cmd.canExecute() == true)
	 */
	protected void executeCommand(final Command cmd) {
		Map<String, Boolean> options = null;
		EditPart ep = getHost();
		boolean isActivating = true;
		// use the viewer to determine if we are still initializing the diagram
		// do not use the DiagramEditPart.isActivating since
		// ConnectionEditPart's
		// parent will not be a diagram edit part
		EditPartViewer viewer = ep.getViewer();
		if (viewer instanceof DiagramGraphicalViewer) {
			isActivating = ((DiagramGraphicalViewer) viewer).isInitializing();
		}

		if (isActivating || !EditPartUtil.isWriteTransactionInProgress((IGraphicalEditPart) getHost(), false, false)) {
			options = Collections.singletonMap(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
		}

		AbstractEMFOperation operation = new AbstractEMFOperation(((IGraphicalEditPart) getHost()).getEditingDomain(), StringStatics.BLANK, options) {

			@Override
			protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				cmd.execute();
				return Status.OK_STATUS;
			}

			@Override
			protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				cmd.undo();
				return Status.OK_STATUS;
			}
		};
		try {
			operation.execute(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			log.error(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#deactivate()
	 */
	@Override
	public void deactivate() {
		getDiagramEventBroker().removeNotificationListener(getSourceElement(), this);
		getDiagramEventBroker().removeNotificationListener(getTargetElement(), this);
	}

}
