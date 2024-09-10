/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 517462
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.service.types.messages.Messages;
import org.eclipse.papyrus.uml.service.types.utils.ConnectorUtils;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;

/**
 * 
 * This command manage the connector reorient at the semantic level
 * 
 */
public class ConnectorReorientSemanticCommand extends EditElementCommand {

	/**
	 * the opposite part (which is not retargeted)
	 */
	private EObject oppositeEnd;

	/**
	 * the new part
	 */
	private EObject newEnd;

	/**
	 * the direction for the retarget
	 */
	protected final int reorientDirection;

	/**
	 * the new part with port
	 */
	private Property newPartWithPort;

	/**
	 * the opposite part with port (which is not changed here)
	 */
	private Property oppositePartWithPort;


	/**
	 * Constructor.
	 */
	public ConnectorReorientSemanticCommand(final ReorientRelationshipRequest request) {
		this(request, request.getRelationship());
	}

	/**
	 * Constructor.
	 */
	public ConnectorReorientSemanticCommand(final ReorientReferenceRelationshipRequest request) {
		this(request, null);
	}

	/**
	 * Constructor.
	 */
	private ConnectorReorientSemanticCommand(ReorientRequest request, EObject element2Edit) {
		super(request.getLabel(), element2Edit, request);
		this.reorientDirection = request.getDirection();
		this.newEnd = request.getNewRelationshipEnd();
		setupFields();
	}

	/**
	 * setup command fields
	 * 
	 * @param request
	 */
	private void setupFields() {
		this.oppositeEnd = null;
		this.oppositePartWithPort = null;
		if (getElementToEdit() instanceof Connector) {
			ConnectorEnd source = getLink().getEnds().get(0);
			ConnectorEnd target = getLink().getEnds().get(1);
			this.oppositeEnd = isSourceRedirect() ? target.getRole() : source.getRole();
			this.oppositePartWithPort = isSourceRedirect() ? target.getPartWithPort() : source.getPartWithPort();
		}
		initFields();
	}	
	/**
	 * @return
	 * 		<code>true</code> if the source end is redirecting
	 *         <code>false</code> if the target end is redirecting
	 */
	private boolean isSourceRedirect() {
		return getLink().getEnds().get(0).getRole() == ((ReorientRequest) getRequest()).getOldRelationshipEnd();
	}	
	
	/**
	 * This method allows to init the fields which can't be initialized in the constructor
	 */
	protected void initFields() {
		this.newPartWithPort = (Property) getRequest().getParameter(ConnectorUtils.PART_WITH_PORT);
		this.oppositePartWithPort = (Property) getRequest().getParameter(ConnectorUtils.OPPOSITE_PART_WITH_PORT);
	}

	/**
	 * Get the link to re-orient.
	 * 
	 * @return the edited {@link Connector}
	 */
	protected Connector getLink() {
		return (Connector) getElementToEdit();
	}

	/**
	 * Test if the command can be executed.
	 */
	@Override
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof Connector)) {
			return false;
		}
		// FIXME in fact, in UML ends>2 is allowed, but it is forbidden in SysML
		if (getLink().getEnds().size() != 2) {
			return false;
		}

		return canReorient(newEnd, oppositeEnd);
	}

	/**
	 *
	 * @param newRole
	 * @param oppositeRole
	 * @return
	 * 		<code>true</code> if the newRole can be used as role for connector
	 */
	private boolean canReorient(final EObject newRole, final EObject oppositeRole) {
		if (newRole == null) {
			return true;// we allow to reinitialize the role
		}
		// the new role must be a connectable element
		if (!(newRole instanceof ConnectableElement)) {
			return false;
		}

		if (oppositeRole != null) {

			// UML Standard, p.181 : [3] The ConnectableElements attached as roles to each ConnectorEnd owned by a Connector must be roles
			// of the Classifier, that owned the Connector, or they must be ports of such roles. (p.181)
			// in SysML this rules is not applied, that's why we are testing the context of this action
			if (ConnectorUtils.applyUMLRulesForConnector(getLink())) {
				final StructuredClassifier newContainer = deduceParentConnector(getLink(), (ConnectableElement) oppositeRole, (ConnectableElement) newRole, this.oppositePartWithPort, this.newPartWithPort);
				if (ConnectorUtils.applyUMLRulesForConnector(getLink()) && !ConnectorUtils.getUMLPossibleRoles(newContainer).contains(newRole)) {
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor,
	 *      org.eclipse.core.runtime.IAdaptable)
	 * 
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}

		final ConnectorEnd editedConnectorEnd;
		final ConnectorEnd oppositeEnd;
		if (isSourceRedirect()) {
			editedConnectorEnd = getLink().getEnds().get(0);
			oppositeEnd = getLink().getEnds().get(1);
		} else {
			editedConnectorEnd = getLink().getEnds().get(1);
			oppositeEnd = getLink().getEnds().get(0);
		}
		if (editedConnectorEnd != null) {
			reorientEnd(editedConnectorEnd, oppositeEnd, (ConnectableElement) newEnd, newPartWithPort, oppositePartWithPort);
			if (ConnectorUtils.applyUMLRulesForConnector(getLink())) {
				final StructuredClassifier newContainer = deduceParentConnector(getLink(), (ConnectableElement) this.oppositeEnd, (ConnectableElement) this.newEnd, this.oppositePartWithPort, this.newPartWithPort);
				replaceOwner(getLink(), newContainer);
			}
			return CommandResult.newOKCommandResult();
		}
		throw new IllegalStateException();
	}

	/**
	 * 
	 * @param end
	 * @param oppositeEnd
	 * @param role
	 * @param partWithPort
	 * @param oppositePartWithPort
	 * @return
	 * @throws ExecutionException
	 */
	protected CommandResult reorientEnd(final ConnectorEnd end, ConnectorEnd oppositeEnd, final ConnectableElement role, final Property partWithPort, final Property oppositePartWithPort) {
		end.setRole(role);
		end.setPartWithPort(partWithPort);
		oppositeEnd.setPartWithPort(oppositePartWithPort);
		return CommandResult.newOKCommandResult();
	}



	/**
	 * 
	 * @param newPartWithPort
	 */
	public void setNewPartWithPort(final Property newPartWithPort) {
		this.newPartWithPort = newPartWithPort;
	}

	/**
	 * 
	 * @param connector
	 *            the connector
	 * @param newOwner
	 *            the new owner for the connector
	 */
	protected void replaceOwner(final Connector connector, final StructuredClassifier newOwner) {
		// Change owner and Connector name (possibly already exists in new container)
		if (newOwner != connector.getOwner()) {
			if (newOwner.getOwnedConnector(connector.getName()) != null) {
				String replacementName = NamedElementUtil.getDefaultNameWithIncrementFromBase(Messages.ConnectorReorientSemanticCommand_0, newOwner.eContents()); // //$NON-NLS-0$ //$NON-NLS-1$
				connector.setName(replacementName);
			}
			// Replace connector owner
			newOwner.getOwnedConnectors().add(connector);
		}
	}

	/**
	 * 
	 * @param connector
	 *            the edited connector
	 * @param role1
	 *            a role of this connector
	 * @param role2
	 *            the 2nd role for this connector
	 * @param partWithPort1
	 *            the part with port for the first role (could be <code>null</code>
	 * @param partWithPort2
	 *            the part with port for the second role (could be <code>null</code>
	 * @return
	 * 		the new parent for the connector
	 */
	protected StructuredClassifier deduceParentConnector(final Connector connector, final ConnectableElement role1, final ConnectableElement role2, final Property partWithPort1, final Property partWithPort2) {
		// Ownership is implied by the part exposing the port, if it is a port on a part
		StructuredClassifier class1 = ((role1 instanceof Port) && (partWithPort1 != null))
				? TypeUtils.as(partWithPort1.getOwner(), StructuredClassifier.class)
				: TypeUtils.as(role1.getOwner(), StructuredClassifier.class);
		StructuredClassifier class2 = ((role2 instanceof Port) && (partWithPort2 != null))
				? TypeUtils.as(partWithPort2.getOwner(), StructuredClassifier.class)
				: TypeUtils.as(role2.getOwner(), StructuredClassifier.class);

		if (class1 == class2) {
			// Simple case: within a single composite
			return class1;
		}

		// The connected elements are in different structured classifiers. This doesn't
		// make sense, so just leave it as it is (we earlier chose one somehow)
		return TypeUtils.as(connector.getOwner(), StructuredClassifier.class);
	}

	/**
	 * Setter for {@link #oppositeEnd}
	 * 
	 * @param oppositeEnd
	 *            the opposite end
	 */
	public final void setOppositeEnd(final EObject oppositeEnd) {
		this.oppositeEnd = oppositeEnd;
	}

	/**
	 * Setter for {@link #newEnd}
	 * 
	 * @param newEnd
	 *            the new end
	 */
	public final void setNewEnd(final EObject newEnd) {
		this.newEnd = newEnd;
	}

	/**
	 * Setter for {@link #oppositePartWithPort}
	 * 
	 * @param oppositePartWithPort
	 *            the opposite part with port
	 */
	public final void setOppositePartWithPort(Property oppositePartWithPort) {
		this.oppositePartWithPort = oppositePartWithPort;
	}

	/**
	 * @return the oppositeEnd
	 */
	public EObject getOppositeEnd() {
		return oppositeEnd;
	}


	/**
	 * @return the newEnd
	 */
	public EObject getNewEnd() {
		return newEnd;
	}


	/**
	 * @return the newPartWithPort
	 */
	protected Property getNewPartWithPort() {
		return newPartWithPort;
	}


	/**
	 * @return the oppositePartWithPort
	 */
	protected Property getOppositePartWithPort() {
		return oppositePartWithPort;
	}
}
