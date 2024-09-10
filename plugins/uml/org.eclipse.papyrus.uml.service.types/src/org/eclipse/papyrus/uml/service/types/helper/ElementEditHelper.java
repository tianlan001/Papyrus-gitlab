/*****************************************************************************
 * Copyright (c) 2011, 2016, 2017 CEA LIST, Christian W. Damus, Esterel Technologies SAS and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *      Christian W. Damus - bug 458685
 * 		Christian W. Damus - bug 467016
 * 		Christian W. Damus - bug 459701
 * 		Sebastien Bordes (Esterel Technologies SAS) - bug 497800
 *		Benoit Maggi (CEA LIST) - bug 516513, 518406
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.commands.IConfigureCommandFactory;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.common.util.CrossReferencerUtil;
import org.eclipse.papyrus.uml.service.types.Activator;
import org.eclipse.papyrus.uml.service.types.command.StereotypeApplicationMoveCommand;
import org.eclipse.papyrus.uml.service.types.preferences.UMLElementTypePreferenceInitializer;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.papyrus.uml.types.core.edithelper.DefaultUMLEditHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <pre>
 * 
 * Edit helper class for {@link Element}
 * 
 * Expected behavior:
 * - Removes any stereotype application before deletion
 * 
 * The configure command allows contributions provided by the request parameters.
 * </pre>
 */
public class ElementEditHelper extends DefaultUMLEditHelper {

	private static final String CONTAINMENT_LINK_TYPE = "Element_ContainmentEdge"; //$NON-NLS-1$

	/**
	 * Obtains an edit command, if available, from the Papyrus Element Edit Service.
	 * 
	 * @param context
	 *            the context of the edit (usually the element to be edited, if only one)
	 * @param request
	 *            the edit request
	 * @return the command, which may be {@code null} if unavailable or possibly not executable even if available
	 */
	protected ICommand getEditServiceCommand(EObject context, IEditCommandRequest request) {
		ICommand result = null;

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(context);
		if (provider != null) {
			result = provider.getEditCommand(request);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(ConfigureRequest req) {
		if (req.getParameter(IConfigureCommandFactory.CONFIGURE_COMMAND_FACTORY_ID) != null) {
			IConfigureCommandFactory factory = (IConfigureCommandFactory) req.getParameter(IConfigureCommandFactory.CONFIGURE_COMMAND_FACTORY_ID);
			return factory.create(req);
		}
		return super.getConfigureCommand(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map<EClass, EReference> getDefaultContainmentFeatures() {
		return super.getDefaultContainmentFeatures();
	}

	@Override
	protected boolean approveRequest(IEditCommandRequest request) {
		boolean result = super.approveRequest(request);
		if (!result) {
			if (request instanceof CreateElementRequest) {
				// Bug 467016: Maybe the "containment" reference isn't actually a containment but subsets one?
				// e.g., BehavioredClassifier::classifierBehavior subsets BehavioredClassifier::ownedBehavior
				Object context = request.getEditHelperContext();
				if (context instanceof EObject) {
					EObject owner = (EObject) context;
					EReference reference = getContainmentFeature((CreateElementRequest) request);
					if (reference != null && reference.getEContainingClass() != null && reference.getEContainingClass().isSuperTypeOf(owner.eClass()) && !reference.isContainment()) {
						// Look for containment superset. UML2 will do the right thing and add the new
						// element implicitly to that superset
						for (EReference next : UmlUtils.getSupersets(reference)) {
							if (next.isContainment()) {
								result = true;
								break;
							}
						}
					}
				}
			}
		}

		return result;
	}


	/**
	 * If the owner of a moved object change then remove associated containment link.
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getMoveCommand(org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest)
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected ICommand getMoveCommand(MoveRequest req) {
		ICommand moveCommand = super.getMoveCommand(req);
		EObject targetContainer = req.getTargetContainer();
		if (targetContainer instanceof Element) {
			Map elementsToMove = req.getElementsToMove();
			if (elementsToMove != null) {
				for (Object object : elementsToMove.keySet()) {
					if (object instanceof Element) {
						Element element = (Element) object;
						ICommand deleteContainmentLinks = getDeleteContainmentLinkCommand(req.getEditingDomain(), element, (Element) targetContainer);
						if (deleteContainmentLinks.canExecute()) {
							moveCommand = CompositeCommand.compose(moveCommand, deleteContainmentLinks);
						}
						boolean alsoMoveStereotypeApplication = Activator.getDefault().getPreferenceStore().getBoolean(UMLElementTypePreferenceInitializer.MOVE_STEREOTYPEAPPLICATION_ELEMENT_IN_SAME_RESOURCE);
						if (alsoMoveStereotypeApplication) {
							ICommand moveStereotypeCommand = getMoveStereotypeCommand(req, element, element.eResource(),  targetContainer.eResource());
							if (moveStereotypeCommand.canExecute()) {
								moveCommand = CompositeCommand.compose(moveCommand, moveStereotypeCommand);
							}							
						}
					}
				}
			}
		}
		return moveCommand;
	}



	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected ICommand getSetCommand(SetRequest req) {
		ICommand setCommand = super.getSetCommand(req);
		EStructuralFeature feature = req.getFeature();
		if (UMLPackage.eINSTANCE.getPackage_PackagedElement().equals(feature) || UMLPackage.eINSTANCE.getClass_NestedClassifier().equals(feature)) {
			EObject elementToEdit = req.getElementToEdit();
			if (elementToEdit instanceof Element) {
				Object value = req.getValue();
				if (value instanceof List) {
					for (Object object : (List) value) {
						if (object instanceof Element) {
							ICommand deleteContainmentLinks = getDeleteContainmentLinkCommand(req.getEditingDomain(), (Element) object, (Element) elementToEdit);
							if (deleteContainmentLinks.canExecute()) {
								setCommand = CompositeCommand.compose(setCommand, deleteContainmentLinks);
							}
						}
					}
				}
			}
		}
		return setCommand;
	}


	/**
	 * Construct command to remove incoherent containment links in diagrams.
	 * 
	 * @param editingDomain
	 * @param element
	 * @param targetContainer
	 * @return
	 */
	private ICommand getDeleteContainmentLinkCommand(TransactionalEditingDomain editingDomain, Element element, Element targetContainer) {
		ICommand cc = new CompositeCommand("Remove all invalid containment links");
		if (targetContainer != element.getOwner()) {
			final Iterator<View> viewIt = CrossReferencerUtil.getCrossReferencingViews(element, null).iterator();
			while (viewIt.hasNext()) {
				for (Edge edge : (List<Edge>) ViewUtil.getTargetConnections(viewIt.next())) {
					if (CONTAINMENT_LINK_TYPE.equals(edge.getType())) {
						final DeleteCommand destroyViewsCommand = new DeleteCommand(editingDomain, edge);
						cc = CompositeCommand.compose(cc, destroyViewsCommand);
					}
				}
			}
		}
		return cc;
	}
	
	/**
	 *  Construct a command to move stereotype application if required
	 * @param request
	 * @param element
	 * @param source
	 * @param target
	 * @return
	 */
	private ICommand getMoveStereotypeCommand(MoveRequest request, Element element, Resource source, Resource target) {	
		if (target != null && !target.equals(source)) {
			return new StereotypeApplicationMoveCommand(request.getEditingDomain(), element, source, target);
		}
		return UnexecutableCommand.INSTANCE;
	}	
	
}
