/*****************************************************************************
 * Copyright (c) 2016, 2017, 2021 CEA LIST, Esterel Technologies SAS and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515491
 *   Sebastien Gabel (Esterel Technologies SAS) - Bug 519143 (Fix NPE)
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 521908
 *   Asma SMAOUI (CEA LIST) asma.smaoui@cea.fr - Bug 576650
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.databinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor.EDataTypeCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.ui.emf.utils.Constants;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.AbstractStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.BooleanSelector;
import org.eclipse.papyrus.infra.widgets.selectors.IntegerSelector;
import org.eclipse.papyrus.infra.widgets.selectors.RealSelector;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.infra.widgets.selectors.StringSelector;
import org.eclipse.papyrus.infra.widgets.selectors.UnlimitedNaturalSelector;
import org.eclipse.swt.SWT;

/**
 *
 * Editing Support used to edit EObservableValue on a TreeViewer.
 *
 */
public class EObjectObservableValueEditingSupport extends EditingSupport {

	/** Proposals for boolean */
	protected final String[] booleanProposals = new String[] { "true", "false" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Constructor.
	 *
	 * @param viewer
	 *            Viewer in which editors will open.
	 * @param factory
	 *            The reference value factory.
	 */
	public EObjectObservableValueEditingSupport(final ColumnViewer viewer, final ReferenceValueFactory factory) {
		super(viewer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canEdit(final Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CellEditor getCellEditor(final Object element) {
		if (element instanceof EObjectObservableValue) {
			Object valueType = ((EObjectObservableValue) element).getValueType();
			if (valueType instanceof EStructuralFeature) {
				EStructuralFeature feature = (EStructuralFeature) valueType;

				if (feature instanceof EReference) {
					return null;
				}

				EClassifier eType = feature.getEType();
				if (feature.isMany()) {
					return createMultipleCellEditor(feature, (EObjectObservableValue) element);
				} else {
					if (eType instanceof EEnum) {
						return createEnumerationEditor(feature);
					} else {
						String eTypeName = eType.getName();
						if (eTypeName.equals("Boolean")) { //$NON-NLS-1$
							return createBooleanEditor();
						}
					}

					if (eType instanceof EDataType) {
						return new EDataTypeCellEditor((EDataType) eType, ((TreeViewer) getViewer()).getTree());
					}
				}
			}
		}
		return null;
	}

	/**
	 * Create a cell editor which opened a dialog to select multiple value.
	 *
	 * @return The CellEditor
	 */
	private CellEditor createMultipleCellEditor(EStructuralFeature feature, EObjectObservableValue element) {
		EClassifier eType = feature.getEType();
		MultipleCellEditor multiEditor = new MultipleCellEditor(((TreeViewer) getViewer()).getTree(), element.getObserved(), feature);
		if (eType instanceof EEnum) {
			ReferenceSelector referenceSelector = new ReferenceSelector(true);
			referenceSelector.setContentProvider(new AbstractStaticContentProvider() {

				@Override
				public Object[] getElements() {
					return ((EEnum) eType).getELiterals().toArray();
				}
			});

			multiEditor.setSelector(referenceSelector);
		} else {
			String eTypeName = eType.getName();
			switch (eTypeName) {
			case "Integer": //$NON-NLS-1$
				multiEditor.setSelector(new IntegerSelector());
				break;
			case "Boolean": //$NON-NLS-1$
				BooleanSelector booleanSelector = new BooleanSelector();
				multiEditor.setSelector(booleanSelector);
				break;
			case "String": //$NON-NLS-1$
				multiEditor.setSelector(new StringSelector());
				break;
			case "Real": //$NON-NLS-1$
				multiEditor.setSelector(new RealSelector());
				break;
			case "UnlimitedNatural": //$NON-NLS-1$
				multiEditor.setSelector(new UnlimitedNaturalSelector());
				break;
			default:
				break;
			}
		}
		return multiEditor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object getValue(final Object element) {
		if (element instanceof EObjectObservableValue) {
			EObjectObservableValue observableValue = (EObjectObservableValue) element;
			EStructuralFeature feature = (EStructuralFeature) observableValue.getValueType();
			if (feature instanceof EReference) {
				return null;
			}
			EClassifier eType = feature.getEType();
			Object object = observableValue.getValue();

			if (feature.isMany()) {
				String eTypeName = eType.getName();
				if (eTypeName.equals("UnlimitedNatural")) { //$NON-NLS-1$
					return getMultiUnlimitedNaturalValue(object);
				}

				return object;
			} else {
				if (eType instanceof EEnum) {
					return getEnumerationValue((EEnum) eType, object);
				} else {
					String eTypeName = eType.getName();
					if (eTypeName.equals("Boolean")) { //$NON-NLS-1$
						return getBooleanValue(object);
					}
					return object;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the value from the feature.
	 *
	 * @param feature
	 *            The feature to edit.
	 * @param object
	 *            The object to search.
	 * @return the boolean value
	 */
	protected Object getEnumerationValue(final EEnum eType, final Object object) {
		List<EEnumLiteral> literals = eType.getELiterals();
		if (object == null) {
			return 0;
		} else {
			return literals.indexOf(object);
		}
	}

	/**
	 * Return Multi value when the feature is UnlimitedNatural. Replace '-1' by '*'.
	 *
	 * @param object
	 *            The old value.
	 * @return The value to return.
	 * @since 2.1
	 */
	protected Object getMultiUnlimitedNaturalValue(final Object objects) {
		if (objects instanceof List<?>) {
			List<Object> objectToReturn = new ArrayList<>();
			if (objects instanceof List<?>) {
				for (int i = 0; i < ((List<?>) objects).size(); i++) {
					Object object = ((List<?>) objects).get(i);
					String string = object.toString();
					if (string.equals(Constants.INFINITE_MINUS_ONE)) {
						objectToReturn.add(new String(Constants.INFINITY_STAR));
					} else {
						objectToReturn.add(object);
					}
				}
			}
			return objectToReturn;
		}

		return objects;
	}

	/**
	 * Returns the value from the feature.
	 *
	 * @return the boolean value.
	 */
	protected Object getBooleanValue(final Object object) {
		List<String> booleans = Arrays.asList(booleanProposals);
		if (object == null || object.equals("")) {
			return 0;
		}
		return booleans.indexOf(object.toString());
	}


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void setValue(final Object element, final Object value) {
		if (element instanceof EObjectObservableValue) {
			EObjectObservableValue observableValue = (EObjectObservableValue) element;

			EStructuralFeature feature = (EStructuralFeature) observableValue.getValueType();
			if (feature instanceof EReference) {
				// setReferenceValue(element, value);
			} else {
				EClassifier eType = feature.getEType();
				if (eType instanceof EEnum) {
					setEnumerationValue(observableValue, value);
				} else {
					String eTypeName = eType.getName();
					if (eTypeName.equals("Boolean")) { //$NON-NLS-1$
						setBooleanValue(observableValue, value);
					} else {
						Object oldValue = observableValue.getValue();
						// Just set the new value if it is different from the current or if it's a new observable
						if (oldValue == null || !oldValue.equals(value)) {
							observableValue.setValue(value);
						}
					}
				}
			}

			getViewer().update(element, null);
		}
	}

	/**
	 * This allows to set the enumeration value.
	 *
	 * @param element
	 *            The element to manage.
	 * @param value
	 *            The value to set.
	 */
	@SuppressWarnings("unchecked")
	protected void setEnumerationValue(final EObjectObservableValue element, final Object value) {
		EStructuralFeature feature = (EStructuralFeature) element.getValueType();
		EEnum eType = (EEnum) feature.getEType();
		List<EEnumLiteral> literals = eType.getELiterals();

		if (null == value) {
			element.setValue(null);
		} else {
			if (value instanceof Collection<?>) {
				List<Enumerator> literalsToSet = new ArrayList<>();
				for (Object object : ((Collection<Object>) value)) {
					if (object instanceof EEnumLiteral) {
						//bug 576650 : get(index).getInstance(), (works with static and dynamic profile)
						literalsToSet.add(((EEnumLiteral) object).getInstance());
					} else if (object instanceof Integer) {
						// retrieve the index of the current value in the list
						int index = (Integer) value;
						if (index >= 0 && index < literals.size()) {
							//bug 576650 : get(index).getInstance(), (works with static and dynamic profile)
							literalsToSet.add(literals.get(index).getInstance());
						}
					}
				}

				element.setValue(literalsToSet);

			} else {
				// Retrieve the index of the current value in the list
				int index = (Integer) value;

				// Just set the new value if it is different from the old one
				if (index >= 0 && index < literals.size() && !element.getValue().toString().equals(literals.get(index).getLiteral())) {
					//bug 576650 : get(index).getInstance(), (works with static and dynamic profile)
					element.setValue(literals.get(index).getInstance());
				}
			}
		}
	}

	/**
	 * Sets the new boolean value on the given element.
	 *
	 * @param element
	 *            The model element.
	 * @param value
	 *            The new value.
	 */
	@SuppressWarnings("unchecked")
	protected void setBooleanValue(final EObjectObservableValue element, final Object value) {
		if (value instanceof Collection<?>) {
			element.setValue(value);
		} else {
			if (null == value) {
				// Do nothing
			} else if (value.equals(0)) {
				// Just set the new value if it is differ from the old one
				if (!element.getValue().toString().equals(this.booleanProposals[0])) {
					element.setValue(Boolean.valueOf(this.booleanProposals[0]));
				}
			} else if (value.equals(1)) {
				// Just set the new value if it is differ from the old one
				if (!element.getValue().toString().equals(this.booleanProposals[1])) {
					element.setValue(Boolean.valueOf(this.booleanProposals[1]));
				}
			} else {
				Activator.log.error("Impossible to set boolean value " + value, null); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Creates and return a combobox cell editor for a boolean type
	 *
	 * @return the newly created combo box cell editor
	 */
	protected CellEditor createBooleanEditor() {
		return new ComboBoxCellEditor(((TreeViewer) getViewer()).getTree(), booleanProposals, SWT.READ_ONLY);
	}

	/**
	 * Creates and return a combobox cell editor for an enumeration type
	 *
	 * @param feature
	 *            the feature to edit
	 * @return the newly created combo box cell editor
	 */
	protected CellEditor createEnumerationEditor(final EStructuralFeature feature) {
		EEnum eType = (EEnum) feature.getEType();
		List<EEnumLiteral> literals = eType.getELiterals();
		String[] proposals = new String[literals.size()];
		for (int i = 0; i < literals.size(); i++) {
			proposals[i] = literals.get(i).getLiteral();
		}
		return new ComboBoxCellEditor(((TreeViewer) getViewer()).getTree(), proposals, SWT.READ_ONLY);
	}

}
