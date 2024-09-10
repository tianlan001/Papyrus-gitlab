/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	 Florian Noyrit  (CEA) florian.noyrit@cea.fr - Initial API and Implementation
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - reconciler to add floating label
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.migration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.InsertFloatingLabelFromMapCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;

/**
 * Class Diagram Reconciler from 1.0.0 to 1.1.0
 *
 * @since 3.0
 */
public class ClassReconciler_1_1_0 extends DiagramReconciler {

	private final static String CONTAINMENT_LINK_OLD_VISUAL_ID = "4022"; //$NON-NLS-1$
	private final static String CONTAINMENT_LINK_NEW_VISUAL_ID = "4023"; //$NON-NLS-1$
	private final static String CONTAINMENT_LINK_AFFIXEDNODE_OLD_VISUAL_ID = "3032"; //$NON-NLS-1$

	private final static String ClassEditPart_VISUAL_ID = "2008"; //$NON-NLS-1$
	private final static String ClassFloatingNameEditPart_VISUAL_ID = "8510"; //$NON-NLS-1$
	private final static String AssociationClassEditPart_VISUAL_ID = "2013"; //$NON-NLS-1$
	private final static String AssociationClassFloatingNameEditPart_VISUAL_ID = "8504"; //$NON-NLS-1$
	private final static String AssociationNodeEditPart_VISUAL_ID = "2015"; //$NON-NLS-1$
	private final static String AssociationFloatingNameEditPart_VISUAL_ID = "8521"; //$NON-NLS-1$
	private final static String ClassEditPartCN_VISUAL_ID = "3010"; //$NON-NLS-1$
	private final static String ClassFloatingNameEditPartCN_VISUAL_ID = "8518"; //$NON-NLS-1$
	private final static String ComponentEditPart_VISUAL_ID = "2002"; //$NON-NLS-1$
	private final static String ComponentFloatingNameEditPart_VISUAL_ID = "8503"; //$NON-NLS-1$
	private final static String ComponentEditPartCN_VISUAL_ID = "3021"; //$NON-NLS-1$
	private final static String ComponentFloatingNameEditPartCN_VISUAL_ID = "8513"; //$NON-NLS-1$
	private final static String DataTypeEditPart_VISUAL_ID = "2010"; //$NON-NLS-1$
	private final static String DataTypeFloatingNameEditPart_VISUAL_ID = "8502"; //$NON-NLS-1$
	private final static String DataTypeEditPartCN_VISUAL_ID = "3027"; //$NON-NLS-1$
	private final static String DataTypeFloatingNameEditPartCN_VISUAL_ID = "8520"; //$NON-NLS-1$
	private final static String DependencyNodeEditPart_VISUAL_ID = "2014"; //$NON-NLS-1$
	private final static String DependencyFloatingNameEditPart_VISUAL_ID = "8522"; //$NON-NLS-1$
	private final static String EnumerationEditPart_VISUAL_ID = "2006"; //$NON-NLS-1$
	private final static String EnumerationFloatingNameEditPart_VISUAL_ID = "8508"; //$NON-NLS-1$
	private final static String EnumerationEditPartCN_VISUAL_ID = "3025"; //$NON-NLS-1$
	private final static String EnumerationFloatingNameEditPartCN_VISUAL_ID = "8516"; //$NON-NLS-1$
	private final static String InformationItemEditPart_VISUAL_ID = "2099"; //$NON-NLS-1$
	private final static String InformationItemFloatingNameEditPart_VISUAL_ID = "8512"; //$NON-NLS-1$
	private final static String InformationItemEditPartCN_VISUAL_ID = "3040"; //$NON-NLS-1$
	private final static String InformationItemFloatingNameEditPartCN_VISUAL_ID = "8517"; //$NON-NLS-1$
	private final static String InstanceSpecificationEditPart_VISUAL_ID = "2001"; //$NON-NLS-1$
	private final static String InstanceSpecificationFloatingNameEditPart_VISUAL_ID = "8505"; //$NON-NLS-1$
	private final static String InstanceSpecificationEditPartCN_VISUAL_ID = "3020"; //$NON-NLS-1$
	private final static String InstanceSpecificationFloatingNameEditPartCN_VISUAL_ID = "8509"; //$NON-NLS-1$
	private final static String InterfaceEditPart_VISUAL_ID = "2004"; //$NON-NLS-1$
	private final static String InterfaceFloatingNameEditPart_VISUAL_ID = "8507"; //$NON-NLS-1$
	private final static String InterfaceEditPartCN_VISUAL_ID = "3023"; //$NON-NLS-1$
	private final static String InterfaceFloatingNameEditPartCN_VISUAL_ID = "8515"; //$NON-NLS-1$
	private final static String PrimitiveTypeEditPart_VISUAL_ID = "2009"; //$NON-NLS-1$
	private final static String PrimitiveTypeFloatingNameEditPart_VISUAL_ID = "8511"; //$NON-NLS-1$
	private final static String PrimitiveTypeEditPartCN_VISUAL_ID = "3026"; //$NON-NLS-1$
	private final static String PrimitiveTypeFloatingNameEditPartCN_VISUAL_ID = "8519"; //$NON-NLS-1$
	private final static String SignalEditPart_VISUAL_ID = "2003"; //$NON-NLS-1$
	private final static String SignalFloatingNameEditPart_VISUAL_ID = "8506"; //$NON-NLS-1$
	private final static String SignalEditPartCN_VISUAL_ID = "3022"; //$NON-NLS-1$
	private final static String SignalFloatingNameEditPartCN_VISUAL_ID = "8514"; //$NON-NLS-1$

	@Override
	public ICommand getReconcileCommand(Diagram diagram) {
		CompositeCommand cc = new CompositeCommand("Migrate Class diagram and derivated diagrams");
		updateContainmentLinks(diagram, cc);

		// Adds command to insert floating label to Nodes which provide it
		cc.add(new InsertFloatingLabelFromMapCommand(diagram, getFloatingLabelMap()));

		return cc;
	}

	/**
	 * Gets the floating label map to add.
	 *
	 * @return the floating label map
	 */
	private Map<String, String> getFloatingLabelMap() {
		Map<String, String> map = new HashMap<>();
		map.put(ClassEditPart_VISUAL_ID, ClassFloatingNameEditPart_VISUAL_ID);
		map.put(AssociationClassEditPart_VISUAL_ID, AssociationClassFloatingNameEditPart_VISUAL_ID);
		map.put(AssociationNodeEditPart_VISUAL_ID, AssociationFloatingNameEditPart_VISUAL_ID);
		map.put(ClassEditPartCN_VISUAL_ID, ClassFloatingNameEditPartCN_VISUAL_ID);
		map.put(ComponentEditPart_VISUAL_ID, ComponentFloatingNameEditPart_VISUAL_ID);
		map.put(ComponentEditPartCN_VISUAL_ID, ComponentFloatingNameEditPartCN_VISUAL_ID);
		map.put(DataTypeEditPart_VISUAL_ID, DataTypeFloatingNameEditPart_VISUAL_ID);
		map.put(DataTypeEditPartCN_VISUAL_ID, DataTypeFloatingNameEditPartCN_VISUAL_ID);
		map.put(DependencyNodeEditPart_VISUAL_ID, DependencyFloatingNameEditPart_VISUAL_ID);
		map.put(EnumerationEditPart_VISUAL_ID, EnumerationFloatingNameEditPart_VISUAL_ID);
		map.put(EnumerationEditPartCN_VISUAL_ID, EnumerationFloatingNameEditPartCN_VISUAL_ID);
		map.put(InformationItemEditPart_VISUAL_ID, InformationItemFloatingNameEditPart_VISUAL_ID);
		map.put(InformationItemEditPartCN_VISUAL_ID, InformationItemFloatingNameEditPartCN_VISUAL_ID);
		map.put(InstanceSpecificationEditPart_VISUAL_ID, InstanceSpecificationFloatingNameEditPart_VISUAL_ID);
		map.put(InstanceSpecificationEditPartCN_VISUAL_ID, InstanceSpecificationFloatingNameEditPartCN_VISUAL_ID);
		map.put(InterfaceEditPart_VISUAL_ID, InterfaceFloatingNameEditPart_VISUAL_ID);
		map.put(InterfaceEditPartCN_VISUAL_ID, InterfaceFloatingNameEditPartCN_VISUAL_ID);
		map.put(PrimitiveTypeEditPart_VISUAL_ID, PrimitiveTypeFloatingNameEditPart_VISUAL_ID);
		map.put(PrimitiveTypeEditPartCN_VISUAL_ID, PrimitiveTypeFloatingNameEditPartCN_VISUAL_ID);
		map.put(SignalEditPart_VISUAL_ID, SignalFloatingNameEditPart_VISUAL_ID);
		map.put(SignalEditPartCN_VISUAL_ID, SignalFloatingNameEditPartCN_VISUAL_ID);

		return map;
	}

	protected void updateContainmentLinks(Diagram diagram, CompositeCommand cc) {
		cc.add(new UpdateContainmentLinksCommand(diagram));

		// Remove affixed Nodes of containment links
		TreeIterator<EObject> allContentIterator = diagram.eAllContents();

		while (allContentIterator.hasNext()) {
			EObject eObject = allContentIterator.next();

			if (eObject instanceof View) {
				if (((View) eObject).getType().equals(CONTAINMENT_LINK_AFFIXEDNODE_OLD_VISUAL_ID)) {
					DeleteCommand cmd = new DeleteCommand((View) eObject);
					cc.add(cmd);
				}
			}
		}
	}

	protected class UpdateContainmentLinksCommand extends AbstractCommand {

		protected final Diagram diagram;

		public UpdateContainmentLinksCommand(Diagram diagram) {
			super("Update containementLinks in Class diagram and derivated diagrams");
			this.diagram = diagram;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

			TreeIterator<EObject> allContentIterator = diagram.eAllContents();

			while (allContentIterator.hasNext()) {
				EObject eObject = allContentIterator.next();

				if (eObject instanceof Edge) {
					if (((Edge) eObject).getType().equals(CONTAINMENT_LINK_OLD_VISUAL_ID)) {
						Edge edge = ((Edge) eObject);

						View source = edge.getSource();
						EObject sourceContainer = source.eContainer();

						if (sourceContainer instanceof View) {
							// update source of the connector to the root node instead of the affixedNode
							edge.setSource((View) sourceContainer);
							// Update the type of the connector 4022 -> 4023
							edge.setType(CONTAINMENT_LINK_NEW_VISUAL_ID);
						}
					}
				}
			}

			return CommandResult.newOKCommandResult();
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public boolean canRedo() {
			return false;
		}

		@Override
		protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			throw new ExecutionException("Should not be called, canRedo false"); //$NON-NLS-1$
		}

		@Override
		protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			throw new ExecutionException("Should not be called, canUndo false"); //$NON-NLS-1$
		}
	}

}
