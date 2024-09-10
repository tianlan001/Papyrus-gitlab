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
package org.eclipse.papyrus.uml.types.core.advices.stereotypereferencelink;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice;
import org.eclipse.papyrus.uml.tools.utils.ElementUtil;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.commands.EditStereotypePropertyReferenceCommand;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * Advice for the {@link StereotypePropertyReferenceEdgeAdviceConfiguration}.
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeAdviceEditHelperAdvice extends AbstractEditHelperAdvice implements IStereotypePropertyReferenceEdgeAdvice {

	/** The configuration. */
	protected StereotypePropertyReferenceEdgeAdviceConfiguration configuration;

	/** The stereotype to set Qualify name. */
	protected String stereotypeQualifiedName;

	/** The feature to set. */
	protected String featureToSet;

	/** The edge label. */
	protected String edgeLabel;


	/**
	 * Instantiates a new stereotype property reference edge advice edit helper advice.
	 *
	 * @param configuration
	 *            the configuration
	 */
	public StereotypePropertyReferenceEdgeAdviceEditHelperAdvice(final StereotypePropertyReferenceEdgeAdviceConfiguration configuration) {
		if (null != configuration) {
			this.configuration = configuration;
			this.stereotypeQualifiedName = this.configuration.getStereotypeQualifiedName();
			this.featureToSet = this.configuration.getFeatureToSet();
			this.edgeLabel = this.configuration.getEdgeLabel();
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeEditContextCommand(org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest)
	 */
	@Override
	protected ICommand getBeforeEditContextCommand(final GetEditContextRequest request) {
		GetEditContextCommand command = new GetEditContextCommand(request);
		command.setEditContext(request.getEditHelperContext());
		return command;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 */
	@Override
	public boolean approveRequest(final IEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			// Check the configuration
			if (configuration == null) {
				return false;
			}

			EObject source = ((CreateRelationshipRequest) request).getSource();
			EObject target = ((CreateRelationshipRequest) request).getTarget();

			if (null == target && source instanceof Element) {
				return ElementUtil.hasStereotypeApplied((Element) source, stereotypeQualifiedName);
			} else {
				return canCreate(source, target);
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterCreateRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest)
	 */
	@Override
	protected ICommand getAfterCreateRelationshipCommand(final CreateRelationshipRequest request) {
		ICommand command = null;
		EObject source = request.getSource();
		EObject target = request.getTarget();

		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);

		if (!noSourceOrTarget && !canCreate(source, target)) {
			command = UnexecutableCommand.INSTANCE;
		} else if (noSourceOrTarget && !noSourceAndTarget) {
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			command = IdentityCommand.INSTANCE;
		} else {
			command = new CompositeCommand("Create stereotype property reference link");//$NON-NLS-1$
			((CompositeCommand) command).add(new EditStereotypePropertyReferenceCommand("Set stereotype property reference", request, configuration));//$NON-NLS-1$
		}
		return command;
	}

	/**
	 * @param source
	 *            The source {@link EObject}.
	 * @param target
	 *            The target {@link EObject}.
	 * @return true if a Reference link can be create and the target can be reference into the source.
	 */
	private boolean canCreate(final EObject source, final EObject target) {
		boolean canCreate = false;

		if (source instanceof Element && target instanceof Element) {

			// Test if the Source have the good applied stereotype
			if (ElementUtil.hasStereotypeApplied((Element) source, stereotypeQualifiedName)) {
				// Source is ok: test if the target have the good stereotype
				Stereotype sourceStereotype = ((Element) source).getApplicableStereotype(stereotypeQualifiedName);
				Property attribute = sourceStereotype.getAttribute(featureToSet, null);
				if (null != attribute) {
					Type targetType = attribute.getType();
					if (targetType instanceof Stereotype) {
						// feature as stereotype reference
						canCreate = ElementUtil.hasStereotypeApplied((Element) target, targetType.getQualifiedName());
					} else {
						EObject stereotypeApplication = ((Element) source).getStereotypeApplication(UMLUtil.getAppliedSubstereotype((Element) source, sourceStereotype));
						EStructuralFeature eStructuralFeature = stereotypeApplication.eClass().getEStructuralFeature(UML2Util.getValidJavaIdentifier(featureToSet));
						if (null != eStructuralFeature) {
							EClassifier metaclass = eStructuralFeature.getEType();
							if (metaclass instanceof EClassifier) {
								canCreate = ((EClassifier) metaclass).isInstance(target);
							}
						}
					}
				}
			}
		}
		return canCreate;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice#getFeatureToSet()
	 */
	@Override
	public String getFeatureToSet() {
		return featureToSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice#getStereotypeQualifiedName()
	 */
	@Override
	public String getStereotypeQualifiedName() {
		return stereotypeQualifiedName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice#getEdgeLabel()
	 */
	@Override
	public String getEdgeLabel() {
		return edgeLabel;
	}

}
