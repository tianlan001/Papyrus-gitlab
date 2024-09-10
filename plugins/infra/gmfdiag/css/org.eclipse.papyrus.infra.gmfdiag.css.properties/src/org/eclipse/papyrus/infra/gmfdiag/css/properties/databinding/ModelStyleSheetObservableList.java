/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Micka�l Adam (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.databinding;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.EObjectListValueStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ModelCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.resource.CSSNotationResource;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.ModelStyleSheets;
import org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList;


/**
 *
 * EMFObservableList containing stylesheets of the model.
 *
 * @author Mickael ADAM
 *
 */
public class ModelStyleSheetObservableList extends EMFObservableList implements IChangeListener {

	/** The notation resource. */
	private Resource notationResource;

	/** The domain. */
	private TransactionalEditingDomain domain;

	/** The listener. */
	private CustomModelStyleSheetListener listener;

	/**
	 * Constructor.
	 *
	 * @param notationResource
	 *            the notation resource
	 * @param wrappedList
	 *            the wrapped list
	 * @param domain
	 *            the domain
	 * @param source
	 *            the source
	 * @param feature
	 *            the feature
	 */
	public ModelStyleSheetObservableList(Resource notationResource, List<?> wrappedList, TransactionalEditingDomain domain, EObject source, EStructuralFeature feature) {
		super(wrappedList, domain, source, feature);
		this.notationResource = notationResource;
		this.domain = domain;

		source.eAdapters().add(listener = new CustomModelStyleSheetListener(notationResource, (ModelStyleSheets) source, this));
	}

	/**
	 * Used to add manually.
	 *
	 * @param values
	 *            the values
	 * @return the command to add values
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getAddAllCommand(java.util.Collection)
	 */
	@Override
	public Command getAddAllCommand(Collection<?> values) {
		CompoundCommand compoundCommand = new CompoundCommand();

		if (source.eResource() == null) {
			compoundCommand.append(new RecordingCommand(domain, "Create ModelStylesheet") {
				/**
				 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
				 *
				 */
				@Override
				protected void doExecute() {
					notationResource.getContents().add(source);
				}

			});

			compoundCommand.append(new AbstractCommand("Initialize ModelCSSEngine Adapter") {

				public void redo() {
					execute();
				}

				public void execute() {
					ExtendedCSSEngine engine = CSSNotationResource.getEngine(notationResource);
					if (engine instanceof ModelCSSEngine) {
						((ModelCSSEngine) engine).initAdapter();
					}
				}

				/**
				 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
				 *
				 * @return
				 */
				@Override
				public boolean canUndo() {
					return true;
				}

				/**
				 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
				 *
				 */
				@Override
				public void undo() {
					ExtendedCSSEngine engine = CSSNotationResource.getEngine(notationResource);
					if (engine instanceof ModelCSSEngine) {
						((ModelCSSEngine) engine).disposeAdapter();
					}
				}

				/**
				 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
				 *
				 * @return
				 */
				@Override
				protected boolean prepare() {
					return true;
				}
			});
		}

		compoundCommand.append(super.getAddAllCommand(values));
		compoundCommand.append(new AddAllModelStyleSheetCommand(domain, notationResource, values));
		return compoundCommand;
	}

	/**
	 * Used to remove manually styleSheet.
	 *
	 * @param value
	 *            the value
	 * @return the removes command
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getRemoveCommand(java.lang.Object)
	 */
	@Override
	public Command getRemoveCommand(Object value) {
		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(super.getRemoveCommand(value));

		// Retrieve all instance of EObjectListValueStyleImpl on all CSSDiagramImpl to
		EList<EObject> objectsFromResource = notationResource.getContents();
		for (Object objectFromResource : objectsFromResource) {
			// For each CSSDiagramImpl of the resource look for EObjectListValueStyleImpl
			if (objectFromResource instanceof CSSDiagram) {
				EList<EObject> objectsFromDiagram = ((CSSDiagram) objectFromResource).getStyles();
				for (Object objectFromDiagram : objectsFromDiagram) {
					// For each EObjectListValueStyleImpl of each diagram
					if (objectFromDiagram instanceof EObjectListValueStyle) {
						for (Object styleSheetReference : ((EObjectListValueStyle) objectFromDiagram).getEObjectListValue()) {
							// If the current style sheet to delete from model exist on a diagram, add it on the root
							if (value == styleSheetReference) {
								compoundCommand.append(new RemoveObjectCommand(domain, (EObject) styleSheetReference));
								compoundCommand.append(new AddModelStyleSheetCommand(domain, notationResource, (EObject) styleSheetReference));
							}
						}
					}
				}
			}
		}
		return compoundCommand;
	}

	/**
	 * Used to remove all StyleSheets before replace the new list.
	 *
	 * @param values
	 *            the values
	 * @return the removes the all command
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getRemoveAllCommand(java.util.Collection)
	 */
	@Override
	public Command getRemoveAllCommand(Collection<?> values) {
		return new RemoveAllModelStyleSheetValueCommand(domain, notationResource, values);
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#dispose()
	 *
	 */
	@Override
	public void dispose() {
		source.eAdapters().remove(listener);
		listener.dispose();
		listener = null;
		super.dispose();
	}

}
