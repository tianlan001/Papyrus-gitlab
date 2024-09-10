/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST and Others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	CEA LIST - Initial API and implementation
 *	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 491816
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.widget;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStyleValueCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Activator;
import org.eclipse.papyrus.infra.gmfdiag.properties.messages.Messages;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * The button used to reset the preference of the diagram to the defaut values
 *
 */
public class DiagramPreferencesDefaultButton extends Composite {

	private Button button;

	private SelectionListener listener;

	protected DataSource input;

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public DiagramPreferencesDefaultButton(Composite parent, int style) {
		super(parent, style);
		GridLayout layout = new GridLayout(1, false);
		setLayout(layout);
		createButton();
	}

	/**
	 * create the reset button
	 */
	protected void createButton() {
		button = new Button(this, SWT.NONE);
		button.setText(Messages.DiagramPreferencesDefaultButton_ResetDefault);
		this.listener = createListener();
		button.addSelectionListener(this.listener);
	}

	/**
	 * Sets the input DataSource for this Property editor.
	 *
	 * @param input
	 */
	public void setInput(DataSource input) {
		this.input = input;
	}

	/**
	 * @return the input DataSource for this Property editor
	 */
	public DataSource getInput() {
		return input;
	}

	/**
	 *
	 * @return the listener to use for the button
	 */
	private SelectionListener createListener() {
		final SelectionListener listener = new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				resetProperties();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do
			}
		};
		return listener;
	}

	/**
	 *
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.button.removeSelectionListener(this.listener);
		this.input = null;
	}

	/**
	 * Reset the properties to their default values
	 */
	protected void resetProperties() {
		final IPreferenceStore globalPreferenceStore = Activator.getDefault().getPreferenceStore();
		final String diagramType = getDiagramType();

		Diagram diagram = getDiagram();

		// Reset properties of "Rulers and Grid"
		if (null != diagram) {
			CompoundCommand compoundCommand = new CompoundCommand(diagramType);

			boolean viewGridPref = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.VIEW_GRID));
			boolean viewGridStyle = NotationUtils.getBooleanValue(diagram, PreferencesConstantsHelper.VIEW_GRID_CONSTANT, viewGridPref);
			if (viewGridPref != viewGridStyle) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, viewGridPref, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						PreferencesConstantsHelper.VIEW_GRID_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			boolean viewRulersPref = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.VIEW_RULER));
			boolean viewRulersStyle = NotationUtils.getBooleanValue(diagram, PreferencesConstantsHelper.VIEW_RULERS_CONSTANT, viewRulersPref);
			if (viewRulersPref != viewRulersStyle) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, viewRulersPref, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						PreferencesConstantsHelper.VIEW_RULERS_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			boolean gridOrderPref = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_ORDER));
			boolean gridOrderStyle = NotationUtils.getBooleanValue(diagram, PreferencesConstantsHelper.GRID_ORDER_CONSTANT, gridOrderPref);
			if (gridOrderPref != gridOrderStyle) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, gridOrderPref, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						PreferencesConstantsHelper.GRID_ORDER_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			int gridColorPref = FigureUtilities.RGBToInteger(PreferenceConverter.getColor(globalPreferenceStore, PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_LINE_COLOR)));
			int gridColorStyle = NotationUtils.getIntValue(diagram, PreferencesConstantsHelper.GRID_LINE_COLOR_CONSTANT, gridColorPref);
			if (gridColorPref - gridColorStyle != 0) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, gridColorPref, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
						PreferencesConstantsHelper.GRID_LINE_COLOR_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			int gridLinePref = globalPreferenceStore.getInt(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_LINE_STYLE));
			int gridLineStyle = NotationUtils.getIntValue(diagram, PreferencesConstantsHelper.GRID_LINE_STYLE_CONSTANT, gridLinePref);
			if (gridLinePref - gridLineStyle != 0) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, gridLinePref, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
						PreferencesConstantsHelper.GRID_LINE_STYLE_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			double gridSpacingPref = globalPreferenceStore.getDouble(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_SPACING));
			double gridSpacingStyle = NotationUtils.getDoubleValue(diagram, PreferencesConstantsHelper.GRID_SPACING_CONSTANT, gridSpacingPref);
			if (gridSpacingPref != gridSpacingStyle) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, gridSpacingPref, NotationPackage.eINSTANCE.getDoubleValueStyle(), NotationPackage.eINSTANCE.getDoubleValueStyle_DoubleValue(),
						PreferencesConstantsHelper.GRID_SPACING_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}
			boolean snapToGridPref = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.SNAP_TO_GRID));
			boolean snapToGridStyle = NotationUtils.getBooleanValue(diagram, PreferencesConstantsHelper.SNAP_TO_GRID_CONSTANT, snapToGridPref);
			if (snapToGridPref != snapToGridStyle) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, snapToGridPref, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						PreferencesConstantsHelper.SNAP_TO_GRID_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			boolean snapToGeometryPref = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.SNAP_TO_GEOMETRY));
			boolean snapToGeometryStyle = NotationUtils.getBooleanValue(diagram, PreferencesConstantsHelper.SNAP_TO_GEOMETRY_CONSTANT, snapToGeometryPref);
			if (snapToGeometryPref != snapToGeometryStyle) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, snapToGeometryPref, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						PreferencesConstantsHelper.SNAP_TO_GEOMETRY_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			int rulerUnitsPref = globalPreferenceStore.getInt(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.RULER_UNITS));
			int rulerUnitsStyle = NotationUtils.getIntValue(diagram, PreferencesConstantsHelper.RULER_UNITS_CONSTANT, rulerUnitsPref);
			if (rulerUnitsPref - rulerUnitsStyle != 0) {
				CustomStyleValueCommand customStyleValueCommand = new CustomStyleValueCommand(diagram, rulerUnitsPref, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
						PreferencesConstantsHelper.RULER_UNITS_CONSTANT);
				compoundCommand.append(customStyleValueCommand);
			}

			if (!compoundCommand.getCommandList().isEmpty()) {
				EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(diagram);
				editingDomain.getCommandStack().execute(compoundCommand);
			}
		}
	}

	/**
	 *
	 * @return the preference store
	 */
	protected IPreferenceStore getPreferencesStore() {
		final IStructuredSelection selection = this.input.getSelection();
		final Object firstElement = selection.getFirstElement();
		if (firstElement instanceof EditPart) {
			return DiagramEditPartsUtil.getDiagramWorkspacePreferenceStore((EditPart) firstElement);
		}
		return null;
	}

	/**
	 * Return the type of the Diagram.
	 *
	 * @return
	 * 		the type of the diagram
	 */
	protected String getDiagramType() {
		Diagram diagram = getDiagram();
		String diagramType = ""; //$NON-NLS-1$
		if (null != diagram) {
			diagramType = diagram.getType();
		}

		return diagramType;
	}

	/**
	 * Return the Diagram corresponding to the Selection.
	 *
	 * @return The Diagram
	 */
	protected Diagram getDiagram() {
		Diagram diagram = null;

		final IStructuredSelection selection = this.input.getSelection();
		final Object firstElement = selection.getFirstElement();
		if (firstElement instanceof EditPart) {
			final DiagramEditPart diagramEditPart = DiagramEditPartsUtil.getDiagramEditPart((EditPart) firstElement);
			diagram = (Diagram) diagramEditPart.getAdapter(Diagram.class);
		}

		return diagram;
	}
}
