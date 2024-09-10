/*****************************************************************************
 * Copyright (c) 2010, 2017, 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bugs 435420, 417409
 *  Christian W. Damus - bugs 485220, 515257
 *  Patrick Tessier (CEA LIST) -bug 568329 
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.xwt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintFactory;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSourceFactory;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.papyrus.infra.widgets.editors.EditorParentComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * An Implementation of ISection for the TabbedPropertyView framework.
 * The XWTSection uses an XWT File to display the SWT Controls, and
 * a DataSource for DataBinding
 *
 * @author Camille Letavernier
 */
public class XWTSection extends AbstractPropertySection implements IChangeListener {

	private Section section;

	private DataSource source;

	private View view;

	private Composite self;

	private DisplayEngine display;

	private Set<Constraint> constraints;

	/**
	 * Constructor.
	 *
	 * @param section
	 *            The Section object containing the Metadata for the XWTSection
	 * @param view
	 *            The view this section belongs to
	 * @param display
	 *            The display engine that will generate the SWT Controls
	 */
	public XWTSection(Section section, View view, DisplayEngine display) {
		this.section = section;
		this.view = view;
		this.display = display;
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		self = new EditorParentComposite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		self.setLayout(layout);

		layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 5;
		parent.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		self.setLayoutData(data);

		self.setBackground(parent.getBackground());
		self.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection == getSelection()) {
			return;
		}

		// Sets the initial input, *or* changes the input for the same view : we need to clean the cache
		DataSourceFactory.instance.removeFromCache(getSelection(), view);

		super.setInput(part, selection);

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			setSource(DataSourceFactory.instance.createDataSourceFromSelection(structuredSelection, view));
		}
	}

	private void setSource(DataSource source) {
		final DataSource oldSource = this.source;

		if (oldSource != source) {
			if (oldSource != null) {
				if (section.getConstraints().size() == 0) {
					oldSource.removeChangeListener(this);
					oldSource.autoRelease();
				}
			}

			this.source = source;

			if (source != null) {
				source.retain();
				if (section.getConstraints().size() > 0) {
					source.addChangeListener(this);
				}
			}
		}
	}

	/**
	 * Displays the section
	 *
	 * @param refresh
	 *            If true, and the section has already been displayed, the controls will be
	 *            regenerated. If false, the section will only be displayed if it hasn't been
	 *            displayed yet, or if the display engine allows duplication of sections
	 */
	public void display(boolean refresh) {
		if (self.isDisposed()) {
			Activator.log.debug("Error : widget is disposed"); //$NON-NLS-1$
			dispose();
			return;
		}

		if (!isApplied()) {
			display.storeConstraintevalutionForSource(section, source);
			hide();
			return;
		}

		self.setRedraw(false); // Avoid flickering during refresh
		if (refresh) {
			display.refreshSection(self, section, source);
		} else {
			display.createSection(self, section, source);
		}
		self.setRedraw(true);
	}

	private void hide() {
		display.removeSection(self);
	}

	@Override
	public void refresh() {
		display(false);
	}

	/**
	 * Tests if this section is applied. A section is applied if it doesn't have
	 * any constraint, or if at least one of its constraints match the current selection
	 *
	 * @return
	 *         True if the section should be displayed
	 */
	protected boolean isApplied() {
		if (getConstraints().isEmpty()) {
			return true;
		}

		ISelection selection = getSelection();
		List<?> selectionList = ((IStructuredSelection) selection).toList();

		// Return true only if at least one constraint matches the selection

		for (Constraint constraint : getConstraints()) {
			if (constraint.match(selectionList)) {
				return true;
			}
		}

		return false;
	}

	protected Set<Constraint> getConstraints() {
		if (constraints == null) {
			constraints = new HashSet<>();
			for (ConstraintDescriptor constraintDescriptor : section.getConstraints()) {
				Constraint constraint = ConstraintFactory.getInstance().createFromModel(constraintDescriptor);
				if (constraint != null) {
					constraints.add(constraint);
				}
			}
		}

		return constraints;
	}

	@Override
	public void dispose() {
		// Release the DataSource
		if (source != null) {
			source.removeChangeListener(this);
			source.release();
		}

		// Dispose the SWT Composite
		if (self != null) {
			self.dispose();
		}

		// Clean the DataSource cache
		DataSourceFactory.instance.removeFromCache(getSelection(), view);
		super.dispose();
	}

	@Override
	public IStructuredSelection getSelection() {
		return (IStructuredSelection) super.getSelection();
	}

	@Override
	public String toString() {
		return "XWTSection : " + section.getName(); //$NON-NLS-1$
	}

	@Override
	public void handleChange(ChangeEvent event) {
		display(true);
	}

}
