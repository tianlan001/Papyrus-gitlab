/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Quentin Le Menez quentin.lemenez@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.templaterepository;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.diagram.wizards.transformation.IGenerator;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.uml2.uml.Package;


public class ImportUMLPrimitiveTypes implements IGenerator {
	// This class is used when the element is checked inside SelectModelTemplateComposite's templateTableViewer

	private ModelSet modelSet;

	private org.eclipse.uml2.uml.Package rootElement;

	private EObject primitiveTypes;

	private EObject umlMetamodel;

	public void execute() {
		Resource umlResource = UmlUtils.getUmlModel(modelSet).getResource();

		// Select the root element to add the wanted imports
		if (umlResource.getContents().isEmpty()) {
			return;
		}

		// Always required
		URI primitiveTypesURI = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml", true).appendFragment("_0");
		primitiveTypes = modelSet.getEObject(primitiveTypesURI, true);
		rootElement = (Package) umlResource.getContents().get(0);

		if (umlResource.getContents().get(0) instanceof org.eclipse.uml2.uml.Profile) {
			URI umlMetamodelURI = URI.createURI("pathmap://UML_METAMODELS/UML.metamodel.uml", true).appendFragment("_0");
			umlMetamodel = modelSet.getEObject(umlMetamodelURI, true);
		}

		// Creates the import packages at the root of the model (elements of type packageImport)
		this.getCommandStack(modelSet).execute(getImportCommand());

	}

	private RecordingCommand getImportCommand() {
		return new RecordingCommand(this.modelSet.getTransactionalEditingDomain()) {

			@Override
			protected void doExecute() {

				// Creates the import packages at the root of the model (elements of type packageImport)
				if (!rootElement.getImportedPackages().contains(primitiveTypes)) {
					rootElement.createPackageImport((org.eclipse.uml2.uml.Package) primitiveTypes);
				}

				if (umlMetamodel != null) {
					if (!rootElement.getImportedPackages().contains(umlMetamodel)) {
						rootElement.createPackageImport((org.eclipse.uml2.uml.Package) umlMetamodel);
					}
				}

			}
		};
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.wizards.transformation.IGenerator#setModelSet(org.eclipse.papyrus.uml.diagram.wizards.transformation.ModelSet)
	 *
	 * @param modelSet
	 */
	public void setModelSet(ModelSet modelSet) {
		this.modelSet = modelSet;
	}

	protected final CommandStack getCommandStack(ModelSet modelSet) {
		return modelSet.getTransactionalEditingDomain().getCommandStack();
	}

	public ModelSet getModelSet() {
		return this.modelSet;
	}

}
