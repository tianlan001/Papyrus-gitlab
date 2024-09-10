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
package org.eclipse.papyrus.uml.types.core.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.papyrus.uml.types.core.Activator;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * Command for Edit stereotype property reference.
 *
 * @author Mickael ADAM
 * @since 3.1
 */
public class EditStereotypePropertyReferenceCommand extends EditElementCommand {

	/** The stereotype. */
	private Stereotype stereotype;

	/** The value. */
	private EObject value;

	/** The attribute. */
	private Property attribute;

	/** The element to configure. */
	private EObject elementToConfigure;

	/** The feature to set. */
	private String featureToSet;

	/** The stereotype qualify name. */
	private String stereotypeQualifyName;

	/** The configuration. */
	private StereotypePropertyReferenceEdgeAdviceConfiguration configuration;

	/** The request. */
	private CreateRelationshipRequest request;


	/**
	 * Constructor.
	 *
	 * @param label
	 *            the command label
	 * @param request
	 *            the {@link CreateRelationshipRequest} request
	 * @param configuration
	 *            the configuration
	 */
	public EditStereotypePropertyReferenceCommand(final String label, final CreateRelationshipRequest request, final StereotypePropertyReferenceEdgeAdviceConfiguration configuration) {
		super(label, null, request);
		this.request = request;
		this.configuration = configuration;
		this.stereotypeQualifyName = this.configuration.getStereotypeQualifiedName();
		this.featureToSet = this.configuration.getFeatureToSet();
		init(request);
	}

	/**
	 * Initialize the instantiation.
	 *
	 * @param request
	 *            the request
	 */
	private void init(final CreateRelationshipRequest request) {
		EObject source = request.getSource();
		EObject target = request.getTarget();

		// retrieve eObject
		elementToConfigure = source;
		if (null != configuration && elementToConfigure instanceof Element) {

			TransactionalEditingDomain editingDomain = request.getEditingDomain();
			if (null != editingDomain) {

				Stereotype targetStereotype = ((Element) elementToConfigure).getApplicableStereotype(stereotypeQualifyName);

				if (null != targetStereotype) {
					attribute = targetStereotype.getAttribute(featureToSet, null);
					stereotype = UMLUtil.getAppliedSubstereotype((Element) elementToConfigure, targetStereotype);
					Type targetType = attribute.getType();
					// Gets
					value = null;
					if (targetType instanceof Stereotype) {
						value = getStereotypeApplication((Element) target, (Stereotype) targetType);
					} else {
						value = target;
					}
				}
			}
		}
	}

	/**
	 * @return true, if can execute
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return null != attribute && null != elementToConfigure;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) {
		try {
			if (1 == attribute.getUpper()) {
				// single ref cases
				((Element) elementToConfigure).setValue(stereotype, featureToSet, value);
				ICommand cleanStereotypePropertyReferenceLinkCommand = StereotypePropertyReferenceEdgeUtil.getCleanStereotypePropertyReferenceEdgeCommand(request, stereotypeQualifyName, featureToSet);
				if (null != cleanStereotypePropertyReferenceLinkCommand && cleanStereotypePropertyReferenceLinkCommand.canExecute()) {
					try {
						cleanStereotypePropertyReferenceLinkCommand.execute(null, null);
					} catch (ExecutionException e) {
						Activator.log.error(e);
					}
				}
			} else {
				// multiple references case
				Object list = ((Element) elementToConfigure).getValue(stereotype, featureToSet);
				if (list instanceof List) {
					((List) list).add(value);
				}
			}
		} catch (IllegalArgumentException e) {
			return CommandResult.newErrorCommandResult(e);
		}

		return CommandResult.newOKCommandResult();
	}


	/**
	 * Gets the stereotype application.
	 *
	 * @param umlElement
	 *            the uml element
	 * @param stereotype
	 *            the stereotype
	 * @return the stereotype application
	 */
	protected EObject getStereotypeApplication(final Element umlElement, final Stereotype stereotype) {
		Stereotype actual = (stereotype == null) ? null : UMLUtil.getAppliedSubstereotype(umlElement, stereotype);
		EObject stereotypeApplication = (actual == null) ? null : umlElement.getStereotypeApplication(actual);
		return stereotypeApplication;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * No element to edit here.
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#setElementToEdit(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void setElementToEdit(final EObject element) {
		throw new UnsupportedOperationException();
	}

}