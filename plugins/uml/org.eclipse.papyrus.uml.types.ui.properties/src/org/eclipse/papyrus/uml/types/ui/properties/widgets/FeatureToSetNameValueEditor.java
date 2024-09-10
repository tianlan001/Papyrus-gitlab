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

package org.eclipse.papyrus.uml.types.ui.properties.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.StringEditor;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.FilteredContentProvider;
import org.eclipse.papyrus.uml.diagram.common.parser.PropertyLabelParser;
import org.eclipse.papyrus.uml.types.ui.properties.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The value editor for stereotype qualify name.
 */
public class FeatureToSetNameValueEditor extends StringEditor {

	/**
	 * Content provider for the feature viewer.
	 */
	protected class StereotypeAttributesContentProvider extends FilteredContentProvider {

		/** current edited EClass */
		protected EClass currentEClass;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getElements(final Object inputElement) {
			List<Property> attributes = new ArrayList<Property>();

			if (inputElement instanceof Stereotype) {
				Stereotype stereotype = (Stereotype) inputElement;
				EList<Property> ownedAttributes = stereotype.getAllAttributes();
				for (Property property : ownedAttributes) {
					if (!property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
						attributes.add(property);
					}
				}
			}
			return attributes.toArray();
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

	public class StereotypeAttributesStyledLabelProvider extends DelegatingStyledCellLabelProvider implements ILabelProvider {

		/**
		 * Constructor.
		 */
		public StereotypeAttributesStyledLabelProvider() {
			super(new StereotypeAttributesLabelProvider());
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

	/**
	 * A label provider for Stereotype Attribute.
	 */
	public class StereotypeAttributesLabelProvider extends EMFLabelProvider implements IStyledLabelProvider {

		/** The label parser for {@link Property} */
		PropertyLabelParser labelParser = new PropertyLabelParser();

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(final Object element) {
			String text = Messages.undefined;

			if (element instanceof Property) {
				text = labelParser.getPrintString(new EObjectAdapter((EObject) element), 0);
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

			// Get the original text
			String text = getText(element);

			// Add decorated text
			StyledString styledString = new StyledString(text);
			if (element instanceof Property) {

				int offset = ((Property) element).getName().length();
				styledString.setStyle(offset, text.length() - offset, StyledString.QUALIFIER_STYLER);

			}

			return styledString;
		}
	}

	/** Browse icon */
	private static final String BROWSE_ICON = "/icons/browse_12x12.gif"; //$NON-NLS-1$

	/** Unique button. */
	private Button button = null;

	/** Source uml element. */
	private Stereotype sourceStereotype;

	/**
	 * Default constructor.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public FeatureToSetNameValueEditor(final Composite parent, final int style) {
		super(parent, style);
		((GridLayout) getLayout()).numColumns++;

		button = factory.createButton(this, null, SWT.PUSH);
		button.setImage(Activator.getDefault().getImage(BROWSE_ICON));
		button.setToolTipText(Messages.StereotypeAttributesNameValueEditor_ButtonTooltip);

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

		dialog.setContentProvider(new StereotypeAttributesContentProvider());
		dialog.setLabelProvider(new StereotypeAttributesStyledLabelProvider());

		dialog.setTitle(Messages.StereotypeAttributesNameValueEditor_DialogTitile);
		dialog.setMessage(Messages.StereotypeAttributesNameValueEditor_DialogMessage);

		dialog.setInput(sourceStereotype);

		if (Window.OK == dialog.open()) {
			Object[] values = dialog.getResult();
			if (1 != values.length) {
				Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "");//$NON-NLS-1$
				updateStatus(status);
			} else if (values[0] instanceof Property) {
				setValue(((Property) values[0]).getName());
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
	public void setSourceStereotype(final Stereotype sourceStereotype) {
		this.sourceStereotype = sourceStereotype;
	}
}
