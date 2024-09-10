/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   J�r�mie TATIBOUET (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.PrimitiveType;
import org.junit.Assert;
import org.junit.BeforeClass;

public abstract class AbstractTestPinDerivation extends AbstractPapyrusTest {

	protected static final String UML_PRIMITIVE_TYPES_PATH = "platform:/plugin/org.eclipse.uml2.uml.resources/libraries/UMLPrimitiveTypes.library.uml";

	/**
	 * Model set used to register any resource used during tests execution
	 */
	protected ModelSet modelSet;

	/**
	 * UML model used in the different test cases
	 */
	protected Model umlTestModel;

	/**
	 * Mock editing domain
	 */
	protected TransactionalEditingDomain editingDomain;

	/**
	 * Map referencing primitive types (key is the primitive type name)
	 */
	private Map<String, PrimitiveType> primtiveTypesMap;

	/**
	 * Constructor.
	 */
	public AbstractTestPinDerivation() {
		this.modelSet = new ModelSet();
		this.umlTestModel = UMLFactory.eINSTANCE.createModel();
		this.primtiveTypesMap = new HashMap<String, PrimitiveType>();
	}

	/**
	 * Retrieve the advice identified by the provided identifier
	 * 
	 * @param adviceIdentifier
	 *            identifier of the searched advice
	 * @return the advice or null
	 */
	protected AbstractAdviceBindingConfiguration getAdvice(final String adviceIdentifier) {
		ElementTypeSetConfiguration elementTypeSet = this.getActivityDiagramElementTypeConfiguration();
		List<AbstractAdviceBindingConfiguration> adviceBindings = elementTypeSet.getAdviceBindingsConfigurations();
		Iterator<AbstractAdviceBindingConfiguration> adviceBindingIterator = adviceBindings.listIterator();
		AbstractAdviceBindingConfiguration advice = null;
		while (advice == null && adviceBindingIterator.hasNext()) {
			AbstractAdviceBindingConfiguration currentAdvice = adviceBindingIterator.next();
			if (currentAdvice.getIdentifier().equals(adviceIdentifier)) {
				advice = currentAdvice;
			}
		}
		return advice;
	}

	/**
	 * Retrieve element type set declared for the activity diagram.
	 * 
	 * @return the element type set
	 */
	protected ElementTypeSetConfiguration getActivityDiagramElementTypeConfiguration() {
		String clientContextId = "";
		try {
			clientContextId = TypeContext.getDefaultContext().getId();
		} catch (ServiceException e) {
			Assert.fail("Default client context cannot be found");
		}
		ElementTypeSetConfigurationRegistry instance = ElementTypeSetConfigurationRegistry.getInstance();
		Map<String, Map<String, ElementTypeSetConfiguration>> elementTypeSetConfigurations = instance.getElementTypeSetConfigurations();
		Map<String, ElementTypeSetConfiguration> map = elementTypeSetConfigurations.get(clientContextId);
		return map.get("org.eclipse.papyrus.uml.diagram.activity.elementTypeSet");
	}

	/**
	 * Force loading of declared element type configurations
	 */
	@BeforeClass
	public static void loadActvityDiagramElementTypes() {
		String clientContextId = "";
		try {
			clientContextId = TypeContext.getDefaultContext().getId();
		} catch (ServiceException e) {
			Assert.fail("Default client context cannot be found");
		}
		ElementTypeSetConfigurationRegistry instance = ElementTypeSetConfigurationRegistry.getInstance();
		Map<String, Map<String, ElementTypeSetConfiguration>> elementTypeSetConfigurations = instance.getElementTypeSetConfigurations();
		Map<String, ElementTypeSetConfiguration> map = elementTypeSetConfigurations.get(clientContextId);
		ElementTypeSetConfiguration elementTypeSetConfiguration = map.get("org.eclipse.papyrus.uml.diagram.activity.elementTypeSet");
		Assert.assertNotNull("The element types set definition for activity model could not be found", elementTypeSetConfiguration);
	}

	/**
	 * Convenience method to easily access a primitive type in sub-classes
	 * 
	 * @param primitiveTypeName
	 *            the name of the primitive type
	 * @return the primitive type
	 */
	public PrimitiveType getPrimitiveType(final String primitiveTypeName) {
		return this.primtiveTypesMap.get(primitiveTypeName);
	}

	/**
	 * Build a model set with only primitive types model loaded. This method is intended to be overridden by sub-classes.
	 */
	public void populateBaseTestModel() {
		Resource primitiveTypesResource = this.modelSet.getResource(URI.createURI(UML_PRIMITIVE_TYPES_PATH), true);
		if (primitiveTypesResource == null) {
			Assert.fail("UML primitive types could not be loaded within the model set");
		} else {
			this.editingDomain = new TransactionalEditingDomainImpl(null, this.modelSet);
			Iterator<EObject> contentIterator = primitiveTypesResource.getAllContents();
			while (contentIterator.hasNext()) {
				EObject modelElement = contentIterator.next();
				if (modelElement instanceof PrimitiveType) {
					this.primtiveTypesMap.put(((PrimitiveType) modelElement).getName(), (PrimitiveType) modelElement);
				}
			}
			
			org.eclipse.uml2.uml.Package umlPackage = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(primitiveTypesResource.getContents(), UMLPackage.Literals.PACKAGE);
			umlTestModel.createPackageImport(umlPackage);
		}
	}
}
