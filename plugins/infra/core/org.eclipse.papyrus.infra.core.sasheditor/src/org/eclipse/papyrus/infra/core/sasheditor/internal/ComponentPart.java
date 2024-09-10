/*****************************************************************************
 * Copyright (c) 2009, 2016, 2019 CEA LIST & LIFL, Christian W. Damus, and others
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bugs 469188, 494543
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 546686
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import java.util.function.Supplier;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.core.sasheditor.Activator;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IComponentModel;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IComponentPage;
import org.eclipse.papyrus.infra.core.sasheditor.internal.dnd.IDropTarget;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;


/**
 * This is a controler/part for an SWT Control. It is associated to a {@link IComponentModel}.
 * This Part encapsulate a SWT Control.
 *
 * @author dumoulin
 *
 */
public class ComponentPart extends PagePart implements IComponentPage {

	/**
	 * The model representing the control.
	 */
	private IComponentModel partModel;

	/**
	 * The SWT Control.
	 */
	private Composite editorControl;

	/**
	 * Constructor.
	 *
	 * @param partModel
	 *            The model of the editor.
	 */
	public ComponentPart(TabFolderPart parent, IComponentModel partModel, Object rawModel) {
		super(parent, rawModel);
		this.partModel = partModel;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		Supplier<T> fallback = () -> super.getAdapter(adapter);
		return PlatformHelper.getAdapter(partModel, adapter, fallback);
	}

	/**
	 * Create the control of this Part, and children's controls.
	 *
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		try {
			// Initialize it and create its controls.
			editorControl = createEditorPartControl(parent);
		} catch (PartInitException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getLocalizedMessage()));
		}
	}

	/**
	 * Create the controls required by the editor.
	 * Init the editor.
	 *
	 * @param viewer
	 * @param editorInput
	 * @param model
	 * @return
	 * @throws PartInitException
	 */
	private Composite createEditorPartControl(Composite parentControl) throws PartInitException {
		Composite editorParent = new Composite(parentControl, SWT.NONE);
		editorParent.setLayout(new FillLayout());
		partModel.createPartControl(editorParent);

		return editorParent;
	}

	/**
	 * Dispose all resources used by this part. <br/>
	 * The Part should not be used after it has been disposed.
	 */
	@Override
	public void dispose() {

		if (!editorControl.isDisposed()) {
			// dispose the SWT root control
			editorControl.dispose();
		}
		// clean up properties to help GC
		partModel = null;
		rawModel = null;
	}

	/**
	 * Dispose this part and all its children.
	 * The method is called recursively on children of the part. <br/>
	 * SWT resources have already been disposed. We don't need to dispose them again.
	 *
	 */
	@Override
	public void disposeThisAndChildren() {

		// clean up properties to help GC
		partModel = null;
	}


	/**
	 * As we are a final Tile, we should be the requested part.
	 * Return this TilePart.
	 *
	 * @param toFind
	 * @return
	 */
	public PagePart findPart(Point toFind) {
		return this;
	}

	/**
	 * Locates the part that intersects the given point and that have the expected type
	 *
	 * @param toFind
	 * @return
	 */
	@Override
	public PagePart findPartAt(Point toFind, Class<?> expectedTileType) {

		if (expectedTileType == this.getClass()) {
			return this;
		}

		// Not found !!
		// The tile contains the position, but the type is not found.
		throw new UnsupportedOperationException("Tile match the expected position '" + toFind + "' but there is no Tile of requested type '" + expectedTileType.getClass().getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @param control
	 * @return
	 */
	public PagePart findPart(Object control) {
		if (getControl() == control) {
			return this;
		}

		// Not found
		return null;
	}

	/**
	 * Get associated SWT Control.
	 *
	 * @return
	 */
	@Override
	public Composite getControl() {
		return editorControl;
	}


	/**
	 * This is a container method. Not necessary in Leaf Tile.
	 *
	 * @param draggedObject
	 * @param sourcePart
	 * @param position
	 * @return
	 */
	public IDropTarget getDropTarget(Object draggedObject, TabFolderPart sourcePart, Point position) {
		return null;
	}


	/**
	 * Change the parent of the Tile. The parent is changed, and the control is
	 * attached to the parent control. Change garbage state to {@link GarbageState.REPARENTED}.
	 * Do not detach the Tile from its old parent.
	 *
	 * @param newParent
	 *            The tilePart that should be used as part parent.
	 * @param compositeParent
	 *            The composite that should be used as parent.
	 */
	@Override
	public void reparent(TabFolderPart newParent) {

		// Change the tile parent
		this.parent = newParent;
		// Change the SWT parent.
		editorControl.setParent(newParent.getControl());

		// Change state
		if (garbageState == GarbageState.UNVISITED || garbageState == GarbageState.ORPHANED || garbageState == GarbageState.CREATED) {
			garbageState = GarbageState.REPARENTED;
		} else {
			// Bad state, this is an internal error
			// TODO : log a warning ?
			throw new IllegalStateException("Try to change state from " + garbageState.toString() + " to REPARENTED. This is forbidden."); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}


	/**
	 * Asks this part to take focus within the workbench.
	 * Set the focus on the active nested part if the part is a container.
	 */
	@Override
	public void setFocus() {
		if (editorControl != null && !editorControl.isDisposed()) {
			editorControl.setFocus();
		}
	}


	/**
	 * Synchronize the Part, and its children. PartMap contains a snapshot of the available part before
	 * the synchronization. After synchronization, unreachable parts should be marked "orphaned" (= no
	 * parent).
	 * Do nothing in this implementation, as we are a final leaf, and there is nothing to synchronize
	 * with the underlying model.
	 *
	 * @param partMap
	 */
	public void synchronize2(PartLists partMap) {

	}


	/**
	 * Garbage this part.
	 * The part is already marked as ORPHANED. It is not used anymore. It is already detached
	 * from its parent.
	 *
	 */
	@Override
	public void garbage() {
		dispose();
		// fire appropriate life cycle event
		getSashWindowContainer().getLifeCycleEventProvider().firePageClosedEvent(this);
	}


	/**
	 * Accept the provided visitor.
	 * Call the corresponding accept method in the visitor.
	 *
	 * @param visitor
	 * @return
	 */
	@Override
	public boolean visit(IPartVisitor visitor) {
		return visitor.accept(this);
	}

	/**
	 * Visit the children of this Tile.
	 * There is no child, so do nothing.
	 *
	 * @param visitor
	 */
	public boolean visitChildren(IPartVisitor visitor) {
		return true;
	}


	/**
	 * Show item status.
	 */
	protected void showStatus() {
		System.out.printf("ComponentPart: disposed=%-5b, visible=%-5b, garbState=%-10s, %s, %s\n", editorControl.isDisposed(), (editorControl.isDisposed() ? false : editorControl.isVisible()), garbageState, getPageTitle(), this); //$NON-NLS-1$
	}

	/**
	 * Get the title for this part. {@inheritDoc}
	 */
	@Override
	public String getPageTitle() {
		try {
			return partModel.getTabTitle();
		} catch (Exception ex) {
			Activator.log.error(ex);
			return "Error"; //$NON-NLS-1$
		}
	}

	/**
	 * Return an icon for this part. {@inheritDoc}
	 */
	@Override
	public Image getPageIcon() {
		try {
			return partModel.getTabIcon();
		} catch (Exception ex) {
			Activator.log.error(ex);
			return null;
		}
	}
}
