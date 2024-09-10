/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.StringEditor;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.FilteredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * The value editor for stereotype qualify name.
 */
public class FeatureNameValueEditor extends StringEditor {

	/**
	 * Content provider for the feature viewer.
	 */
	protected class FeatureContentProvider extends FilteredContentProvider {

		/** current edited Eclass */
		protected EClass currentEClass;

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Object[] getElements(final Object inputElement) {
			List<EStructuralFeature> features = new ArrayList<EStructuralFeature>();
			if (inputElement instanceof EClass) {
				currentEClass = (EClass) inputElement;
				// create a new list with only non derived features
				for (EStructuralFeature feature : currentEClass.getEAllStructuralFeatures()) {
					if (feature instanceof EReference) {
						EReference reference = (EReference) feature;
						if (reference.isContainer() || reference.isContainment() || reference.isMany()) {
							continue; // Ignore containment and container features, as well as multi-valued references
						}
					}

					if (!feature.isDerived() && feature.isChangeable()) {
						features.add(feature);
					}
				}
			}
			return features.toArray();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#isFlat()
		 */
		@Override
		public boolean isFlat() {
			return true;
		}


	}

	/**
	 * A label provider for Stereotype Attribute.
	 */
	public class FeatureLabelProvider extends EMFLabelProvider implements IStyledLabelProvider {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(final Object element) {
			String text = null;

			if (element instanceof EStructuralFeature) {
				text = ((EStructuralFeature) element).getName() + ": " + ((EStructuralFeature) element).getEType().getName();//$NON-NLS-1$
			}
			return null != text ? text : super.getText(element);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
		 */
		@Override
		public StyledString getStyledText(final Object element) {

			// Add decorated text
			StyledString styledString = new StyledString(getText(element));
			if (element instanceof EStructuralFeature) {
				int nameLenght = ((EStructuralFeature) element).getName().length();
				styledString.setStyle(nameLenght, styledString.length() - nameLenght, StyledString.QUALIFIER_STYLER);
			}

			return styledString;
		}
	}

	public class FeatureStyledLabelProvider extends DelegatingStyledCellLabelProvider implements ILabelProvider {

		/**
		 * Constructor.
		 *
		 * @param labelProvider
		 */
		public FeatureStyledLabelProvider() {
			super(new FeatureLabelProvider());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(final Object element) {
			return getStyledStringProvider().getStyledText(element).getString();
		}

	}

	/** Browse icon */
	private static final String BROWSE_ICON = "/icons/browse_12x12.gif"; //$NON-NLS-1$

	/** Unique button. */
	private Button button = null;

	/** Source uml element. */
	private EClass sourceEClass;

	/**
	 * Default constructor.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public FeatureNameValueEditor(final Composite parent, final int style) {
		super(parent, style);
		((GridLayout) getLayout()).numColumns++;

		button = factory.createButton(this, null, SWT.PUSH);
		button.setImage(Activator.getDefault().getImage(BROWSE_ICON));
		button.setToolTipText(Messages.FeatureNameValueEditor_browseButtonTooltip);

		// Display menu when user select button
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				handleManageBrowseButtonPressed();
			}
		});
	}

	/**
	 * Handles action when user press the Manage bundle button in the combo area
	 */
	protected void handleManageBrowseButtonPressed() {
		TreeSelectorDialog dialog = new TreeSelectorDialog(getParent().getShell());

		dialog.setContentProvider(new FeatureContentProvider());
		dialog.setLabelProvider(new FeatureStyledLabelProvider());

		dialog.setTitle(Messages.FeatureNameValueEditor_dialogTitle);
		dialog.setMessage(Messages.FeatureNameValueEditor_dialogMessage);

		dialog.setInput(sourceEClass);

		if (Window.OK == dialog.open()) {
			Object[] values = dialog.getResult();
			if (1 != values.length) {
				Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "");//$NON-NLS-1$
				updateStatus(status);
			} else if (values[0] instanceof EStructuralFeature) {
				setValue(((EStructuralFeature) values[0]).getName());
				notifyChange();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		super.setReadOnly(readOnly);
		button.setEnabled(!readOnly);
	}

	/**
	 * Set the source uml element to apply this stereotype to filter stereotype list.
	 */
	public void setSourceEClass(final EClass sourceEClass) {
		this.sourceEClass = sourceEClass;
	}
}
