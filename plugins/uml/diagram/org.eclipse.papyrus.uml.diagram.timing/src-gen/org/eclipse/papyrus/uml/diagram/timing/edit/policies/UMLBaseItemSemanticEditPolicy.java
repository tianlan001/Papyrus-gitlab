/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.timing.edit.policies;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractBaseItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;

/**
 * @generated
 */
public class UMLBaseItemSemanticEditPolicy extends AbstractBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected UMLBaseItemSemanticEditPolicy(IElementType elementType) {
		super(elementType);
	}

	/**
	 * @generated
	 */
	@Override
	protected String getVisualIdFromView(View view) {
		return UMLVisualIDRegistry.getVisualID(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected IElementType getContextElementType(IEditCommandRequest request) {
		IElementType requestContextElementType = UMLElementTypes.getElementType(getVisualID(request));
		return requestContextElementType != null ? requestContextElementType : getBaseElementType();
	}

	/**
	 * @generated
	 */
	public static LinkConstraints getLinkConstraints() {
		LinkConstraints cached = UMLDiagramEditorPlugin.getInstance().getLinkConstraints();
		if (cached == null) {
			UMLDiagramEditorPlugin.getInstance().setLinkConstraints(cached = new LinkConstraints());
		}
		return cached;
	}

	/**
	 * @generated
	 */
	public static class LinkConstraints {

		/**
		 * @generated
		 */
		public LinkConstraints() {
			// use static method #getLinkConstraints() to access instance
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_SynchEdge(Interaction container, MessageEnd source, MessageEnd target) {
			return canExistMessage_SynchEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_AsynchEdge(Interaction container, MessageEnd source, MessageEnd target) {
			return canExistMessage_AsynchEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_ReplyEdge(Interaction container, MessageEnd source, MessageEnd target) {
			return canExistMessage_ReplyEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_CreateEdge(Interaction container, MessageEnd source, MessageEnd target) {
			return canExistMessage_CreateEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_DeleteEdge(Interaction container, MessageEnd source, MessageEnd target) {
			return canExistMessage_DeleteEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_LostEdge(Interaction container, MessageEnd source, Element target) {
			return canExistMessage_LostEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateMessage_FoundEdge(Interaction container, Element source, MessageEnd target) {
			return canExistMessage_FoundEdge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_SynchEdge(Interaction container, Message linkInstance, MessageEnd source, MessageEnd target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_AsynchEdge(Interaction container, Message linkInstance, MessageEnd source, MessageEnd target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_ReplyEdge(Interaction container, Message linkInstance, MessageEnd source, MessageEnd target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_CreateEdge(Interaction container, Message linkInstance, MessageEnd source, MessageEnd target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_DeleteEdge(Interaction container, Message linkInstance, MessageEnd source, MessageEnd target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_LostEdge(Interaction container, Message linkInstance, MessageEnd source, Element target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistMessage_FoundEdge(Interaction container, Message linkInstance, Element source, MessageEnd target) {
			return true;
		}
	}
}
