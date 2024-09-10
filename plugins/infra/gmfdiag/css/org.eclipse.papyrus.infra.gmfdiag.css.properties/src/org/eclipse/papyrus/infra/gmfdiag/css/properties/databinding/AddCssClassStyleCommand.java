/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.databinding;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringListValueStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSStyles;


/**
 * EMF Command to add a new style 
 * Stored in Notation with CSSStyles.CSS_GMF_CLASS_KEY
 */
public class AddCssClassStyleCommand extends RecordingCommand implements Command {

	private Collection<View> views;
	
	private String style;

	/**
	 * Constructor.
	 *
	 * @param namedStyle
	 * @param newValue
	 */
	public AddCssClassStyleCommand(TransactionalEditingDomain domain, View view, String newValue) {
		this(domain, Collections.singleton(view), newValue);
	}

	public AddCssClassStyleCommand(TransactionalEditingDomain domain, Collection<View> views, String style) {
		super(domain);
		this.views = views;
		this.style = style;
	}


	/**
	 * Add the style to the existing list or initialize a new list with it
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		for (View view : views) {
			StringListValueStyle namedStyle = (StringListValueStyle) view.getNamedStyle(NotationPackage.eINSTANCE.getStringListValueStyle(), CSSStyles.CSS_GMF_CLASS_KEY);
			if (namedStyle == null) {
				namedStyle = (StringListValueStyle) view.createStyle(NotationPackage.eINSTANCE.getStringListValueStyle());
				namedStyle.setName(CSSStyles.CSS_GMF_CLASS_KEY);
			}
			namedStyle.getStringListValue().add(style);			
		}
		

	}

}
