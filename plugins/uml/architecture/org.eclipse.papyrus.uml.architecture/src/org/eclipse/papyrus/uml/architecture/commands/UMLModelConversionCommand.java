/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Maged Elaasar - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.architecture.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.papyrus.infra.gmfdiag.style.StyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.uml.architecture.UMLArchitectureContextIds;
import org.eclipse.papyrus.uml.architecture.UMLDiagramKinds;
import org.eclipse.papyrus.uml.architecture.UMLDiagramTypes;
import org.eclipse.papyrus.uml.architecture.UMLTableKinds;
import org.eclipse.papyrus.uml.diagram.common.commands.AbstractModelConversionCommand;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.StructuredClassifier;

/**
 * A command to convert a model from an arbitrary context to the UML language context
 */
public class UMLModelConversionCommand extends AbstractModelConversionCommand {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.commands.ModelConversionCommandBase#doConvertModel(org.eclipse.papyrus.infra.core.resource.ModelSet)
	 *
	 * @param modelSet
	 */
	@Override
	public void doConvertModel(ModelSet modelSet) {
		final Resource notationResource = NotationUtils.getNotationResource(modelSet);
		if (notationResource != null) {
			for (EObject eObject : notationResource.getContents()) {
				if (eObject instanceof Diagram)
					convertDiagram((Diagram)eObject);
				else if (eObject instanceof Table)
					convertTable((Table)eObject);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void convertDiagram(Diagram diagram) {
		// get the existing diagram kind id if any
		String diagramKindId = null;
		PapyrusDiagramStyle pvs = DiagramUtils.getPapyrusDiagramStyle(diagram);
		if (pvs != null) {
			diagramKindId = pvs.getDiagramKindId();
		}
		
		// return if the existing diagram kind is already in the UML language
		if (diagramKindId != null) {
			PapyrusDiagram kind = getDiagramKindById(diagramKindId);
			if (kind != null && UMLArchitectureContextIds.UML.equals(kind.getLanguage().getId())) {
				return;
			}
		}
		
		// Choose a UML diagram kind based on the diagram's type (and the diagram's element)
		PapyrusDiagram diagramKind = null;
		if (UMLDiagramTypes.ACTIVITY_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.ACTIVITY_DIAGRAM);
		} else if (UMLDiagramTypes.CLASS_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.CLASS_DIAGRAM);
		} else if (UMLDiagramTypes.COMMUNICATION_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.COMMUNICATION_DIAGRAM);
		} else if (UMLDiagramTypes.COMPONENT_DIAGRAM.equals(diagram.getType())) {
			if (diagram.getElement() instanceof Component) {
				diagramKind = getDiagramKindById(UMLDiagramKinds.COMPONENT_DIAGRAM_IN_COMPONENT);
			} else if (diagram.getElement() instanceof Package) {
				diagramKind = getDiagramKindById(UMLDiagramKinds.COMPONENT_DIAGRAM_IN_PACKAGE);
			}
		} else if (UMLDiagramTypes.COMPOSITE_STRUCTURE_DIAGRAM.equals(diagram.getType())) {
			if (diagram.getElement() instanceof StructuredClassifier) {
				diagramKind = getDiagramKindById(UMLDiagramKinds.COMPOSITE_STRUCTURE_DIAGRAM_IN_STRUCTURED_CLASSIFIER);
			} else if (diagram.getElement() instanceof Package) {
				diagramKind = getDiagramKindById(UMLDiagramKinds.COMPOSITE_STRUCTURE_DIAGRAM_IN_PACKAGE);
			}
		} else if (UMLDiagramTypes.DEPLOYMENT_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.DEPLOYMENT_DIAGRAM);
		} else if (UMLDiagramTypes.INTERACTION_OVERVIEW_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.INTERACTION_OVERVIEW_DIAGRAM);
		} else if (UMLDiagramTypes.PROFILE_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.PROFILE_DIAGRAM);
		} else if (UMLDiagramTypes.SEQUENCE_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.SEQUENCE_DIAGRAM);
		} else if (UMLDiagramTypes.STATE_MACHINE_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.STATE_MACHINE_DIAGRAM);
		} else if (UMLDiagramTypes.TIMING_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.TIMING_DIAGRAM);
		} else if (UMLDiagramTypes.USE_CASE_DIAGRAM.equals(diagram.getType())) {
			diagramKind = getDiagramKindById(UMLDiagramKinds.USE_CASE_DIAGRAM);
		}
		
		// set the new diagram kind
		if (diagramKind != null) {
			if (pvs == null){
				pvs = StyleFactory.eINSTANCE.createPapyrusDiagramStyle();
				pvs.setOwner(diagram.getElement());
				diagram.getStyles().add(pvs);
			}
			pvs.setDiagramKindId(diagramKind.getId());
		}
	}
	
	protected void convertTable(Table table) {
		// get the existing diagram kind id if any
		String tableKindId = table.getTableKindId();
		
		// return if the existing table kind is already in the UML language
		if (tableKindId != null) {
			RepresentationKind kind = getTableKindById(tableKindId);
			if (kind != null && UMLArchitectureContextIds.UML.equals(kind.getLanguage().getId())) {
				return;
			}
		}
		
		// set the new table kind to a generic table (to make it visible in the model explorer)
		// this does not by itself make the table readable under the UML context
		PapyrusTable kind = getTableKindById(UMLTableKinds.GENERIC_TABLE);
		if (kind != null) {
			table.setTableKindId(kind.getId());
			table.setTableConfiguration(kind.getConfiguration());
		}
	}

	/**
	 * Gets a diagram kind that matches the given id
	 */
	private PapyrusDiagram getDiagramKindById(String id) {
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		RepresentationKind kind = manager.getRepresentationKindById(id);
		return (kind instanceof PapyrusDiagram)? (PapyrusDiagram) kind : null;
	}

	/**
	 * Gets a sync table kind that matches the given id
	 */
	private PapyrusTable getTableKindById(String id) {
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		RepresentationKind kind = manager.getRepresentationKindById(id);
		return (kind instanceof PapyrusTable)? (PapyrusTable) kind : null;
	}

}
