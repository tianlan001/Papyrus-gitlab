/*****************************************************************************
 * Copyright (c) 2014, 2015, 2020 Christian W. Damus and others.
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
 *   Ansgar Radermacher - Bug 526156, add postfix, if generating DI element types
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.uml.profile.types.generator.ui.internal.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Ordering;

/**
 * Composable block of widgets for selection of the element types set on which to base the generated element types model.
 */
class BaseElementTypeSetBlock {

	private static final String DIAGRAM = "diagram"; //$NON-NLS-1$

	private static final String SUPPRESS_SEMANTIC_SUPERTYPES = "suppressSemanticSupertypes"; //$NON-NLS-1$

	/**
	 * Add a .di postfix, if DI element types are created
	 */
	private static final String ADD_DI_POSTFIX = "addDiPostfix"; //$NON-NLS-1$

	protected static final String UML_ELEMENT_TYPE_SET = "org.eclipse.papyrus.uml.service.types.UMLElementTypeSet"; //$NON-NLS-1$

	protected static final String UMLDI_ELEMENT_TYPE_SET = "org.eclipse.papyrus.umldi.service.types.UMLDIElementTypeSet"; //$NON-NLS-1$

	private final GeneratorWizardModel model;

	private final BiMap<String, ElementTypeSetConfiguration> elementTypeSets;

	public BaseElementTypeSetBlock(GeneratorWizardModel model) {
		super();

		this.model = model;

		String clientContextId = "";
		try {
			clientContextId = TypeContext.getDefaultContext().getId();
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		this.elementTypeSets = HashBiMap.create(ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations().get(clientContextId));
	}

	public void createControl(Composite parent) {
		new Label(parent, SWT.NONE).setText("Base element types set:");

		ComboViewer combo = new ComboViewer(new Combo(parent, SWT.READ_ONLY | SWT.DROP_DOWN));
		combo.setLabelProvider(new DiagramListLabelProvider(parent));

		/*
		 * FIXME(Bug 468668): Reinstate this when we can integrate a new Mars (or later) release of the TableCombo.
		 * cf. https://bugs.eclipse.org/bugs/show_bug.cgi?id=468668
		 *
		 * // Use a two-column table because styled label providers don't work in the TableComboViewer
		 * TableComboViewer combo = new TableComboViewer(parent, SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL);
		 * combo.getTableCombo().defineColumns(2);
		 * combo.getTableCombo().setDisplayColumnIndex(0);
		 * combo.setLabelProvider(new DiagramTableLabelProvider(parent));
		 *
		 */

		combo.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setContentProvider(ArrayContentProvider.getInstance());

		// TableCombo doesn't use a ViewerComparator even when one is set
		combo.setInput(getOrdering().sortedCopy(elementTypeSets.values()));

		final Button suppressSemanticSuperElementTypes = new Button(parent, SWT.CHECK);
		suppressSemanticSuperElementTypes.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 2, 1));
		suppressSemanticSuperElementTypes.setText("Suppress semantic parent in diagram-specific element types");
		suppressSemanticSuperElementTypes.setSelection(model.getDialogSettings().getBoolean(SUPPRESS_SEMANTIC_SUPERTYPES));

		final Button addDiPostfix = new Button(parent, SWT.CHECK);
		addDiPostfix.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 2, 1));
		addDiPostfix.setText("Add .di postfix to identifier in diagram-specific element types");
		if (model.getDialogSettings().get(ADD_DI_POSTFIX) != null) {
			addDiPostfix.setSelection(model.getDialogSettings().getBoolean(ADD_DI_POSTFIX));
		} else {
			// set by default
			addDiPostfix.setSelection(true);
		}

		ElementTypeSetConfiguration initialSelection = getInitialSelection();
		if (initialSelection != null) {
			combo.setSelection(new StructuredSelection(initialSelection));
			elementTypeSetSelectionChanged((IStructuredSelection) combo.getSelection());
		}
		combo.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				elementTypeSetSelectionChanged((IStructuredSelection) event.getSelection());
				suppressSemanticSuperElementTypes.setEnabled(!UML_ELEMENT_TYPE_SET.equals(model.getSelectedElementTypeSet()));
				addDiPostfix.setEnabled(UMLDI_ELEMENT_TYPE_SET.equals(model.getSelectedElementTypeSet()));
				model.validatePage();
			}
		});

		setSuppressSemanticSupertypes(suppressSemanticSuperElementTypes.getSelection());
		suppressSemanticSuperElementTypes.setEnabled(!UML_ELEMENT_TYPE_SET.equals(model.getSelectedElementTypeSet()));
		addDiPostfix.setEnabled(UMLDI_ELEMENT_TYPE_SET.equals(model.getSelectedElementTypeSet()));
		model.setAddDiPostfix(addDiPostfix.getSelection());

		suppressSemanticSuperElementTypes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSuppressSemanticSupertypes(suppressSemanticSuperElementTypes.getSelection());
			}
		});

		addDiPostfix.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.err.println(addDiPostfix.getSelection());
				model.setAddDiPostfix(addDiPostfix.getSelection());
			}
		});
	}

	void elementTypeSetSelectionChanged(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			setElementTypeSet(null);
		} else {
			setElementTypeSet((ElementTypeSetConfiguration) selection.getFirstElement());
		}
	}

	void setElementTypeSet(ElementTypeSetConfiguration elementTypeSet) {
		model.setSelectedElementTypeSet((elementTypeSet == null) ? null : elementTypeSets.inverse().get(elementTypeSet));
	}

	void setSuppressSemanticSupertypes(boolean suppress) {
		model.setSuppressSemanticSuperElementTypes(suppress);
	}

	void save() {
		model.getDialogSettings().put(DIAGRAM, model.getSelectedElementTypeSet());
		model.getDialogSettings().put(SUPPRESS_SEMANTIC_SUPERTYPES, model.isSuppressSemanticSuperElementTypes());
		model.getDialogSettings().put(ADD_DI_POSTFIX, model.isAddDiPostfix());
	}

	private ElementTypeSetConfiguration getInitialSelection() {
		ElementTypeSetConfiguration result = null;

		String id = model.getDialogSettings().get(DIAGRAM);
		if (id != null) {
			result = elementTypeSets.get(id);
		}

		if (result == null) {
			result = elementTypeSets.get(UML_ELEMENT_TYPE_SET);
		}

		return result;
	}

	static String getName(ElementTypeSetConfiguration elementTypeSet) {
		String result = elementTypeSet.getName();

		if (Strings.isNullOrEmpty(result)) {
			// Infer a name from the URI
			URI uri = elementTypeSet.eResource().getURI();
			result = uri.trimFileExtension().lastSegment();
		}

		return result;
	}

	private static Function<ElementTypeSetConfiguration, String> getNameFunction() {
		return new Function<>() {
			@Override
			public String apply(ElementTypeSetConfiguration input) {
				return getName(input);
			}
		};
	}

	static String getLocation(ElementTypeSetConfiguration elementTypeSet) {
		URI uri = elementTypeSet == null || elementTypeSet.eResource() == null ? null : elementTypeSet.eResource().getURI();
		if (uri == null) {
			return null;
		}
		String result;

		if (uri.isPlatformResource()) {
			result = uri.trimSegments(1).toPlatformString(true);
		} else if (uri.isPlatformPlugin()) {
			result = uri.segment(1);
		} else {
			result = uri.toString();
		}

		return result;
	}

	private static Function<ElementTypeSetConfiguration, String> getLocationFunction() {
		return new Function<>() {
			@Override
			public String apply(ElementTypeSetConfiguration input) {
				return getLocation(input);
			}
		};
	}

	static Ordering<ElementTypeSetConfiguration> getOrdering() {
		Ordering<Object> strings = Ordering.from(Policy.getComparator());

		return strings.onResultOf(getNameFunction()).compound(strings.onResultOf(getLocationFunction()));
	}

	//
	// Nested types
	//

	private static class DiagramListLabelProvider extends LabelProvider implements IStyledLabelProvider {
		private ResourceManager images;

		DiagramListLabelProvider(Control owner) {
			super();

			// Because we specify an owner, the owner will take care of clean-up, so we
			// don't need a dispose() method of our own
			images = new LocalResourceManager(JFaceResources.getResources(), owner);
		}

		@Override
		public StyledString getStyledText(Object element) {
			ElementTypeSetConfiguration elementTypeSet = (ElementTypeSetConfiguration) element;
			return new StyledString(getName(elementTypeSet)) //
					.append(NLS.bind(" - {0}", getLocation(elementTypeSet)), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
		}

		@Override
		public String getText(Object element) {
			ElementTypeSetConfiguration elementTypeSet = (ElementTypeSetConfiguration) element;
			return NLS.bind("{0} - {1}", getName(elementTypeSet), getLocation(elementTypeSet));
		}

		@Override
		public Image getImage(Object element) {
			Image result = null;

			ElementTypeSetConfiguration set = (ElementTypeSetConfiguration) element;
			URI uri = set.eResource().getURI();

			if (uri.isPlatformPlugin()) {
				result = (Image) images.get(Activator.getInstance().getIcon("obj16/plugin.gif")); //$NON-NLS-1$
			} else if (uri.isPlatformResource()) {
				result = (Image) images.get(Activator.getInstance().getIcon("obj16/project.png")); //$NON-NLS-1$
			}

			return result;
		}
	}

	/*
	 * FIXME(Bug 468668): Reinstate this when we can integrate a new Mars (or later) release of the TableCombo.
	 * cf. https://bugs.eclipse.org/bugs/show_bug.cgi?id=468668
	 *
	 * private static class DiagramTableLabelProvider extends LabelProvider implements IStyledLabelProvider, ITableLabelProvider, ITableColorProvider {
	 * private static final int COLUMN_NAME = 0;
	 * private static final int COLUMN_LOCATION = 1;
	 *
	 * private ResourceManager images;
	 *
	 * DiagramTableLabelProvider(Control owner) {
	 * super();
	 *
	 * // Because we specify an owner, the owner will take care of clean-up, so we
	 * // don't need a dispose() method of our own
	 * images = new LocalResourceManager(JFaceResources.getResources(), owner);
	 * }
	 *
	 * @Override
	 * public StyledString getStyledText(Object element) {
	 * return new StyledString(getColumnText(element, COLUMN_NAME)) //
	 * .append(NLS.bind(" - {0}", getColumnText(element, COLUMN_LOCATION)), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
	 * }
	 *
	 * @Override
	 * public String getColumnText(Object element, int column) {
	 * ElementTypeSetConfiguration elementTypeSet = (ElementTypeSetConfiguration) element;
	 * String result;
	 *
	 * switch (column) {
	 * case COLUMN_NAME:
	 * result = getName(elementTypeSet);
	 * break;
	 * case COLUMN_LOCATION:
	 * result = getLocation(elementTypeSet);
	 * break;
	 * default:
	 * throw new IllegalArgumentException("no such column: " + column); //$NON-NLS-1$
	 * }
	 *
	 * return result;
	 * }
	 *
	 * @Override
	 * public Image getColumnImage(Object element, int column) {
	 * Image result = null;
	 *
	 * if (column == COLUMN_NAME) {
	 * ElementTypeSetConfiguration set = (ElementTypeSetConfiguration) element;
	 * URI uri = set.eResource().getURI();
	 *
	 * if (uri.isPlatformPlugin()) {
	 * result = (Image) images.get(Activator.getInstance().getIcon("obj16/plugin.gif")); //$NON-NLS-1$
	 * } else if (uri.isPlatformResource()) {
	 * result = (Image) images.get(Activator.getInstance().getIcon("obj16/project.png")); //$NON-NLS-1$
	 * }
	 * }
	 *
	 * return result;
	 * }
	 *
	 * @Override
	 * public Color getForeground(Object element, int column) {
	 * Color result = null;
	 *
	 * if (column == COLUMN_LOCATION) {
	 * result = JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR);
	 * }
	 *
	 * return result;
	 * }
	 *
	 * @Override
	 * public Color getBackground(Object element, int columnIndex) {
	 * return null;
	 * }
	 * }
	 *
	 */
}
