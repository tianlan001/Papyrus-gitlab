/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 509383
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.service.types.command.ConnectorReorientCommand;
import org.eclipse.papyrus.uml.service.types.utils.ConnectorUtils;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterUtils;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;

/**
 * Edit helper class for binary {@link Connector}
 */
public class ConnectorEditHelper extends ElementEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		return new ConnectorReorientCommand(req);
	}

	/**
	 * Test if the relationship creation is allowed.
	 * 
	 * @param source
	 *        the relationship source can be null
	 * @param target
	 *        the relationship target can be null
	 * @param sourceView
	 *        the relationship graphical source can be null
	 * @param targetView
	 *        the relationship graphical target can be null
	 * @return true if the creation is allowed
	 */
	protected boolean canCreate(EObject source, EObject target, View sourceView, View targetView) {

		if((source != null) && !(source instanceof ConnectableElement)) {
			return false;
		}

		if((target != null) && !(target instanceof ConnectableElement)) {
			return false;
		}

		if((sourceView != null) && (targetView != null)) {
			// Cannot create a self connector on a view
			if(sourceView == targetView) {
				return false;
			}

			// Cannot create a connector from a view representing a Part to its own Port (or the opposite)
			if((sourceView.getChildren().contains(targetView)) || (targetView.getChildren().contains(sourceView))) {
				return false;
			}

			// Cannot connect a Part to one of its (possibly indirect) containment, must connect to one of its Port.
			if(getStructureContainers(sourceView).contains(targetView) || getStructureContainers(targetView).contains(sourceView)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req) {
		EObject source = req.getSource();
		EObject target = req.getTarget();

		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);
		if(noSourceOrTarget && !noSourceAndTarget) {
			
			if (!(source instanceof ConnectableElement)){
				return UnexecutableCommand.INSTANCE;
			} 
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			return IdentityCommand.INSTANCE;			

		}
		
		if (source != null && target != null){			
			// Propose a semantic container for the new Connector.
			StructuredClassifier proposedContainer = deduceContainer(req);
			if(proposedContainer == null) {
				return UnexecutableCommand.INSTANCE;
			}
			
			EObject container = req.getContainer();
			if (container != proposedContainer){
				req.setContainer(proposedContainer);
				return new CreateRelationshipCommand(req);
			}
			
		}
	
		boolean umlStrict = true;		
		if (!req.getParameters().isEmpty()){
				Object umlStrictParameter = req.getParameter(org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants.UML_STRICT);
				if (umlStrictParameter instanceof Boolean){
					umlStrict = (Boolean) umlStrictParameter;		
				}
			}

		boolean canCreate = true;
		
		if (umlStrict){
			canCreate = canCreate(source, target, RequestParameterUtils.getSourceView(req), RequestParameterUtils.getTargetView(req));
		}
		if(!noSourceAndTarget && !canCreate) {
			// Abort creation.
			return UnexecutableCommand.INSTANCE;
		}
		return new CreateRelationshipCommand(req);
	}

	/**
	 * This method provides the source role provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 */
	private ConnectableElement getSourceRole(IEditCommandRequest req) {
		ConnectableElement result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.SOURCE);
		if(paramObject instanceof ConnectableElement) {
			result = (ConnectableElement)paramObject;
		}

		return result;
	}

	/**
	 * This method provides the target role provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 */
	private ConnectableElement getTargetRole(IEditCommandRequest req) {
		ConnectableElement result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.TARGET);
		if(paramObject instanceof ConnectableElement) {
			result = (ConnectableElement)paramObject;
		}

		return result;
	}

	/**
	 * This method provides the source partWithPort provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target partWithPort
	 */
	private Property getSourcePartWithPort(IEditCommandRequest req) {
		Property result = null;
		if(getSourceRole(req) instanceof Port) {
			// Only look for PartWithPort if the role is a Port.

			View parentView = ViewUtil.getContainerView(RequestParameterUtils.getSourceView(req));
			EObject semanticParent = parentView.getElement();
			if((semanticParent instanceof Property) && !(semanticParent instanceof Port)) {
				// Only add PartWithPort for assembly (not for delegation)
				if(!EcoreUtil.isAncestor(parentView, RequestParameterUtils.getTargetView(req))) {
					result = (Property)semanticParent;
				}
			}

		}
		return result;
	}

	/**
	 * This method provides the target partWithPort provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target partWithPort
	 */
	private Property getTargetPartWithPort(IEditCommandRequest req) {
		Property result = null;
		if(getTargetRole(req) instanceof Port) {
			// Only look for PartWithPort if the role is a Port.

			View parentView = ViewUtil.getContainerView(RequestParameterUtils.getTargetView(req));
			EObject semanticParent = parentView.getElement();
			if((semanticParent instanceof Property) && !(semanticParent instanceof Port)) {
				// Only add PartWithPort for assembly (not for delegation)
				if(!EcoreUtil.isAncestor(parentView, RequestParameterUtils.getSourceView(req))) {
					result = (Property)semanticParent;
				}
			}

		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(final ConfigureRequest req) {

		final Connector connector = (Connector)req.getElementToConfigure();
		final ConnectableElement sourceRole = getSourceRole(req);
		final ConnectableElement targetRole = getTargetRole(req);
		final Property sourcePartWithPort = getSourcePartWithPort(req);
		final Property targetPartWithPort = getTargetPartWithPort(req);

		ICommand configureCommand = new ConfigureElementCommand(req) {

			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

				// Add source connector end
				ConnectorEnd sourceEnd = connector.createEnd();
				sourceEnd.setRole(sourceRole);
				sourceEnd.setPartWithPort(sourcePartWithPort);

				// Add target connector end
				ConnectorEnd targetEnd = connector.createEnd();
				targetEnd.setRole(targetRole);
				targetEnd.setPartWithPort(targetPartWithPort);

				return CommandResult.newOKCommandResult(connector);
			}
		};

		return CompositeCommand.compose(configureCommand, super.getConfigureCommand(req));
	}

	private StructuredClassifier deduceContainer(CreateRelationshipRequest request) {
		return new ConnectorUtils().deduceContainer(RequestParameterUtils.getSourceView(request), RequestParameterUtils.getTargetView(request));
	}

	private List<View> getStructureContainers(View view) {
		return new ConnectorUtils().getStructureContainers(view);
	}
}
