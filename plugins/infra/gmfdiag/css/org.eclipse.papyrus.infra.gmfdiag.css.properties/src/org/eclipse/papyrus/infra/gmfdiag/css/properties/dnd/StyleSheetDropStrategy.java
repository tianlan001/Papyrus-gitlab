/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.css.properties.dnd;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.notation.EObjectListValueStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSStyles;
import org.eclipse.papyrus.infra.gmfdiag.css.properties.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.properties.databinding.AddCSSStyleSheetCommand;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StylesheetsFactory;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.swt.graphics.Image;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Strategy for dropping CSS resources onto a diagram to add a stylesheet reference.
 */
public class StyleSheetDropStrategy extends TransactionalDropStrategy {
	public static final String ID = Activator.PLUGIN_ID + ".drop"; //$NON-NLS-1$

	public StyleSheetDropStrategy() {
		super();
	}

	@Override
	public String getLabel() {
		return "Add Stylesheet Reference";
	}

	@Override
	public String getDescription() {
		return "Adds a stylesheet to the diagram by reference to the dropped CSS resource";
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		Command result = null;

		// Only diagram supports stylesheet reference
		View targetView = NotationHelper.findView(targetEditPart);
		targetView = (targetView != null) ? targetView.getDiagram() : targetView;
		if (targetView != null) {
			// The DropObjectsRequest has only EObjects if it contains anything, so go directly to the source
			List<?> objects;
			ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
			if (selection instanceof IStructuredSelection) {
				objects = ((IStructuredSelection) selection).toList();
			} else {
				objects = Collections.emptyList();
			}

			EObjectListValueStyle existingReferences = (EObjectListValueStyle) targetView.getNamedStyle(NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY);
			List<String> cssPaths = Lists.newArrayListWithCapacity(objects.size());
			for (Object next : objects) {
				IFile file = AdapterUtils.adapt(next, IFile.class, null);
				if ((file != null) && "css".equals(file.getFileExtension())) {
					// Maybe we have this one already?
					final String path = file.getFullPath().toString();
					boolean found = false;
					if (existingReferences != null) {
						for (StyleSheetReference ref : Iterables.filter(existingReferences.getEObjectListValue(), StyleSheetReference.class)) {
							if (path.equals(ref.getPath())) {
								found = true;
								break;
							}
						}
					}

					if (!found) {
						cssPaths.add(path);
					}
				} else {
					// All-or-nothing drop
					cssPaths.clear();
					break;
				}
			}

			if (!cssPaths.isEmpty()) {
				CompoundCommand compound = new CompoundCommand(getLabel());
				TransactionalEditingDomain domain = getTransactionalEditingDomain(targetEditPart);
				for (String next : cssPaths) {
					StyleSheetReference ref = StylesheetsFactory.eINSTANCE.createStyleSheetReference();
					ref.setPath(next);
					AddCSSStyleSheetCommand command = new AddCSSStyleSheetCommand(domain, targetView,
							CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY,
							NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE__EOBJECT_LIST_VALUE,
							ref) {

						@Override
						public String getLabel() {
							return StyleSheetDropStrategy.this.getLabel();
						}
					};
					compound.add(EMFtoGEFCommandWrapper.wrap(command));
				}

				result = compound.unwrap();
			}
		}

		return result;
	}

}
