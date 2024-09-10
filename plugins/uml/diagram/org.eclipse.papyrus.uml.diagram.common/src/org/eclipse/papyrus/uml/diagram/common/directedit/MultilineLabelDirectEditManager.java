/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Benoit Maggi  benoit.maggi@cea.fr - Bug 444063
 *  Vincent Lorenzo - vincent.lorenzo@cea.fr - Bug 463550 : change this class into a wrapper to extends TextDirectEditManager and keep the previous bugfixes.
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.directedit;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase;
import org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager;
import org.eclipse.swt.graphics.Point;

/**
 * Inspired from {@link org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager} but use static to avoid concurrency problem on restore (Bug 444063)
 *
 *
 *
 */
public class MultilineLabelDirectEditManager extends TextDirectEditManager {

	/**
	 * the MultilineLabelDirectEditManager which really do the work
	 */
	private MultilineLabelDirectEditManagerWrapped r;

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 * @param editorType
	 * @param locator
	 */
	public MultilineLabelDirectEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator) {
		super(source, editorType, locator);
		r = new MultilineLabelDirectEditManagerWrapped(source, editorType, locator);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 */
	public MultilineLabelDirectEditManager(ITextAwareEditPart source) {
		super(source);
		r = new MultilineLabelDirectEditManagerWrapped(source);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager#setEditText(java.lang.String)
	 *
	 * @param toEdit
	 */
	@Override
	public void setEditText(String toEdit) {
		r.setEditText(toEdit);
	}

	/**
	 *
	 * @see org.eclipse.gef.tools.DirectEditManager#setLocator(org.eclipse.gef.tools.CellEditorLocator)
	 *
	 * @param locator
	 */
	@Override
	public void setLocator(CellEditorLocator locator) {
		r.setLocator(locator);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase#show(char)
	 *
	 * @param initialChar
	 */
	@Override
	public void show(char initialChar) {
		r.show(initialChar);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase#show()
	 *
	 */
	@Override
	public void show() {
		r.show();
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase#show(org.eclipse.swt.graphics.Point)
	 *
	 * @param location
	 */
	@Override
	public void show(Point location) {
		r.show(location);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase#showFeedback()
	 *
	 */
	@Override
	public void showFeedback() {
		r.showFeedback();
	}



	/**
	 * @param source
	 *            the <code>GraphicalEditPart</code> that is used to determine
	 *            which <code>CellEditor</code> class to use.
	 * @return the <code>Class</code> of the <code>CellEditor</code> to use for
	 *         the text editing.
	 */
	public static Class getTextCellEditorClass(GraphicalEditPart source) {
		return MultilineLabelDirectEditManagerWrapped.getTextCellEditorClass(source);
	}

	/**
	 * @param source
	 *            the <code>ITextAwareEditPart</code> to determine the cell
	 *            editor for
	 * @return the <code>CellEditorLocator</code> that is appropriate for the
	 *         source <code>EditPart</code>
	 */
	public static CellEditorLocator getTextCellEditorLocator(final ITextAwareEditPart source) {
		return DirectEditManagerBase.getCellEditorLocator(source);
	}



}