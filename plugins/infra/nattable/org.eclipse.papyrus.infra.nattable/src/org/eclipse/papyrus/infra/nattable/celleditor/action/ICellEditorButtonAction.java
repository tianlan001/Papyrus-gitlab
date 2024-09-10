/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.celleditor.action;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.celleditor.IActionCellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Common interface to describe an additional button for a cell editor
 *
 * @since 6.6
 */
public interface ICellEditorButtonAction {

	/**
	 *
	 * @param editor
	 *            the initial cell editor
	 * @param parent
	 *            the parent composite
	 * @param originalCanonicalValue
	 *            the original cell value
	 * @param layerCell
	 *            the edited cell
	 * @param configRegistry
	 *            the {@link IConfigRegistry}
	 * @return <code>true</code> if the button isEnable after the configuration and <code>false</code> otherwise (basically it must return the result of the isEnabled() method
	 */
	public boolean configureAction(final IActionCellEditor editor, final Composite parent, final Object originalCanonicalValue, final ILayerCell layerCell, final IConfigRegistry configRegistry);

	/**
	 * Sets the image for the button.
	 *
	 * @param image
	 *            the new image for the button
	 */
	public void setImage(Image image);

	/**
	 * Sets the text for the button.
	 *
	 * @param text
	 *            the new text for the button
	 */
	public void setText(String text);

	/**
	 * Sets the tooltip text for the button.
	 *
	 * @param tooltipText
	 *            the new tooltip text for the button
	 */
	public void setTooltipText(String tooltipText);

	/**
	 *
	 * @return
	 *         <code>true</code> if the action can be run
	 */
	public boolean isEnabled();

	/**
	 *
	 * @param parent
	 *            the parent composite
	 * @param style
	 *            the style for the created control
	 * @return
	 *         the created root controls, can't be <code>null</code>
	 */
	public List<Control> createControl(final Composite parent, final int style);

}
