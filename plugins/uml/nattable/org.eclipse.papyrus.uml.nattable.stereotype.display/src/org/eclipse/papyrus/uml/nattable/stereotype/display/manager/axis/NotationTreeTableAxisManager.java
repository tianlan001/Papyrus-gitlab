/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.hideshow.RowHideShowLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.command.RowHideCommand;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectTreeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusGridLayer;
import org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderHierarchicalLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.ui.util.WorkbenchPartHelper;
import org.eclipse.papyrus.uml.nattable.stereotype.display.Activator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Element;

public class NotationTreeTableAxisManager extends EObjectTreeAxisManagerForEventList
		implements IAxisManagerForEventList {

	/**
	 * The list of selected objects.
	 */
	private Collection<Object> selectionList = null;

	public NotationTreeTableAxisManager() {

	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#fillChildrenForSemanticElement(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param axis
	 */
	@Override
	protected void fillChildrenForSemanticElement(ITreeItemAxis axis) {

		super.fillChildrenForSemanticElement(axis);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#fillChildrenForTreeFillingConfiguration(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param axis
	 */
	@Override
	protected void fillChildrenForTreeFillingConfiguration(ITreeItemAxis axis) {

		super.fillChildrenForTreeFillingConfiguration(axis);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#fillListWithGrandSon(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param element
	 */
	@Override
	public void fillListWithGrandSon(ITreeItemAxis element) {

		super.fillListWithGrandSon(element);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#fillListWithRoots()
	 *
	 */
	@Override
	public void fillListWithRoots() {
		if (null != getTableEditingDomain() && getRepresentedContentProvider().getAxis().isEmpty()) {
			// we are creating the table and not opening an existing table!
			IEditorPart part = WorkbenchPartHelper.getCurrentActiveEditorPart();
			DiagramEditor editor = null;
			if (part instanceof IAdaptable) {
				editor = part.getAdapter(DiagramEditor.class);
			} else if (part instanceof DiagramEditor) {
				editor = (DiagramEditor) part;
			}
			selectionList = new ArrayList<>();
			if (editor != null && !editor.getDiagramGraphicalViewer().getSelection().isEmpty()) {
				IStructuredSelection selection = (IStructuredSelection) editor.getDiagramGraphicalViewer().getSelection();
				Iterator<?> iter = selection.iterator();
				while (iter.hasNext()) {
					Object obj = iter.next();
					if (obj instanceof IAdaptable) {
						View v = ((IAdaptable) obj).getAdapter(View.class);
						if (v != null) {
							if (isStereotypedElement(v)) {
								selectionList.add(v);
							}
						}
					}
					if (obj instanceof View) {
						if (isStereotypedElement((View) obj)) {
							selectionList.add(obj);
						}
					}
				}
			}

			final Command cmd = getAddAxisCommand(getTableEditingDomain(), selectionList);
			if (cmd != null) {
				try {
					GMFUnsafe.write(getTableEditingDomain(), cmd);
				} catch (InterruptedException e) {
					Activator.log.error(e);
				} catch (RollbackException e) {
					Activator.log.error(e);
				}
			}
			// because event will be propagated and children will be set!
			return;
		}
		super.fillListWithRoots();
	}

	/**
	 * Check is the element of the view is stereotyped.
	 *
	 * @param view
	 *            The view.
	 * @return <code>true</code> if the element of view is stereotyped, <code>false</code> otherwise.
	 */
	protected boolean isStereotypedElement(final View view) {

		return (view.getElement() instanceof Element && !((Element) view.getElement()).getAppliedStereotypes().isEmpty());
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#isAlreadyManaged(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAlreadyManaged(Object object) {

		return super.isAlreadyManaged(object);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#updateManagedList(java.util.List, java.util.List)
	 *
	 * @param toAdd
	 * @param toRemove
	 */
	@Override
	protected void updateManagedList(List<Object> toAdd, List<Object> toRemove) {
		super.updateManagedList(toAdd, toRemove);
		getTableManager().refreshNatTable();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#fillListWithChildren(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param parentAxis
	 */
	@Override
	public void fillListWithChildren(ITreeItemAxis parentAxis) {

		super.fillListWithChildren(parentAxis);

	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageSetNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageSetNotification(Notification notification) {

		super.manageSetNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageUnsetNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageUnsetNotification(Notification notification) {

		super.manageUnsetNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageAddITreeItemAxisForSemanticElement(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param axis
	 */
	@Override
	protected void manageAddITreeItemAxisForSemanticElement(ITreeItemAxis axis) {

		super.manageAddITreeItemAxisForSemanticElement(axis);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageAddNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageAddNotification(Notification notification) {

		super.manageAddNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageAddManyNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageAddManyNotification(Notification notification) {

		super.manageAddManyNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageMoveNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageMoveNotification(Notification notification) {

		super.manageMoveNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageRemoveITreeItemAxisForSemanticElement(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param axis
	 */
	@Override
	protected void manageRemoveITreeItemAxisForSemanticElement(ITreeItemAxis axis) {

		super.manageRemoveITreeItemAxisForSemanticElement(axis);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageRemoveNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageRemoveNotification(Notification notification) {

		super.manageRemoveNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageRemoveSemanticElement(java.lang.Object)
	 *
	 * @param object
	 */
	@Override
	protected void manageRemoveSemanticElement(Object object) {
		super.manageRemoveSemanticElement(object);

	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageRemoveManyNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageRemoveManyNotification(Notification notification) {

		super.manageRemoveManyNotification(notification);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#updateChildren(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param semanticElementRepresentation
	 */
	@Override
	protected void updateChildren(ITreeItemAxis semanticElementRepresentation) {

		super.updateChildren(semanticElementRepresentation);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#updateSemanticElement(java.lang.Object)
	 *
	 * @param semanticElement
	 */
	@Override
	protected void updateSemanticElement(Object semanticElement) {
		super.updateSemanticElement(semanticElement);

	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#managedHideShowCategoriesForDepth(java.util.List, java.util.List)
	 *
	 * @param depthToHide
	 * @param depthToShow
	 */
	@Override
	public void managedHideShowCategoriesForDepth(final List<Integer> depthToHide, final List<Integer> depthToShow) {
		super.managedHideShowCategoriesForDepth(depthToHide, depthToShow);
		// Hide the first level elements
		if (null != selectionList && !selectionList.isEmpty()) {
			final RowHideShowLayer layer = getRowHideShowLayer();
			final NatTable natTable = (NatTable) getTableManager().getAdapter(NatTable.class);
			if (natTable == null) {
				return;
			}
			final TreeLayer treeLayer = getTreeLayer();
			if (null != treeLayer) {
				for (int cpt = selectionList.size() - 1; cpt >= 0; cpt--) {
					// Expand the tree item
					treeLayer.expandTreeRow(cpt);
					// Hide the first level
					natTable.doCommand(new RowHideCommand(layer, cpt));
				}
			}
		}
	}

	/**
	 * Get the tree layer.
	 *
	 * @return
	 *         the tree layer
	 */
	private TreeLayer getTreeLayer() {
		NatTable natTable = (NatTable) getTableManager().getAdapter(NatTable.class);
		ILayer layer = natTable.getLayer();
		if (layer instanceof PapyrusGridLayer) {
			PapyrusGridLayer gridLayer = (PapyrusGridLayer) layer;
			ILayer rowLayer = gridLayer.getRowHeaderLayer();
			if (rowLayer instanceof RowHeaderHierarchicalLayerStack) {
				return ((RowHeaderHierarchicalLayerStack) rowLayer).getTreeLayer();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#dispose()
	 */
	@Override
	public void dispose() {
		if (null != selectionList) {
			selectionList.clear();
			selectionList = null;
		}
		super.dispose();
	}
}
