/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 */
package org.eclipse.papyrus.uml.diagram.clazz.custom.figure;

import org.eclipse.papyrus.uml.diagram.common.figure.edge.DashedEdgeFigure;
import org.eclipse.swt.SWT;

/**
 * this is the link that is a template binding it contains a label that can be edited in multiline
 *
 * @author Patrick Tessier
 *
 */
public class TemplateBindingFigure extends DashedEdgeFigure {

	protected BindingSubstitutionFigure bindingSubstitutionFigure;

	public TemplateBindingFigure() {
		setAntialias(SWT.ON);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.edge.UMLEdgeFigure#createContents()
	 *
	 */
	@Override
	protected void createContents() {
		super.createContents();
		bindingSubstitutionFigure = new BindingSubstitutionFigure();
		this.add(bindingSubstitutionFigure);
	}

	/**
	 *
	 * @return the label multiline
	 */
	public BindingSubstitutionFigure getBindingSubstitutionFigure() {
		return bindingSubstitutionFigure;
	}
}
