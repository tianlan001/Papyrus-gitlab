/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.configuration.handler;

import static org.eclipse.papyrus.infra.gmfdiag.css.configuration.helper.DiagramTypeHelper.getDiagramType;

import java.util.List;
import java.util.Map;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.gmfdiag.css.configuration.providers.ExistingStyleContentProvider;
import org.eclipse.papyrus.infra.gmfdiag.css.configuration.providers.StylesheetLabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.AttributeSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ClassSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CssSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.css_declaration;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ruleset;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.selector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.simple_selector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.stylesheet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * A dialog for editing an existing CSS Style, based on a selected element
 *
 * @author Camille Letavernier
 */
// TODO: When a condition or property doesn't exist on the selected element,
// but exists in the selected style, add it to #declarations/#conditions
// TODO: Delete an existing style?
// TODO: Use a combo-box + ListViewer instead of a TreeViewer
// TODO: Remove read-only stylesheets (Or add a warning/Error when a ruleset from a readonly stylesheet is selected)
// TODO: Load an external stylesheet (Which is not yet applied on the diagram)
// TODO: Support embedded stylesheets
public class StyleEditionDialog extends AbstractStyleDialog implements ISelectionChangedListener {

	protected Composite styleSelectionPanel;

	protected Composite styleEditionPanel;

	protected ruleset ruleset;

	/**
	 *
	 * @param shell
	 * @param conditions
	 *            inout
	 * @param declarations
	 *            inout
	 * @param selectorName
	 */
	public StyleEditionDialog(Shell shell, Map<AttributeSelector, Boolean> conditions, Map<css_declaration, Boolean> declarations, String selectorName, View context) {
		super(shell, conditions, declarations, selectorName, context);
	}

	@Override
	public void create() {
		super.create();
		createStyleSelectionPanel();
		getShell().setText("Edit an existing style");
		// getShell().pack();
	}

	@Override
	public Composite createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		SashForm sash = new SashForm(dialogArea, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// GridLayout parentLayout = (GridLayout)dialogArea.getLayout();
		// parentLayout.numColumns = 2;
		// parentLayout.makeColumnsEqualWidth = false;

		styleSelectionPanel = new Composite(sash, SWT.NONE);

		GridData data = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
		data.widthHint = 250;
		styleSelectionPanel.setLayoutData(data);
		styleSelectionPanel.setLayout(new GridLayout(1, true));

		styleEditionPanel = new Composite(sash, SWT.NONE);
		styleEditionPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(1, true);
		// layout.marginWidth = 0;
		// layout.marginHeight = 0;
		styleEditionPanel.setLayout(layout);

		sash.setWeights(new int[] { 2, 5 });

		return styleEditionPanel;
	}

	protected void createStyleSelectionPanel() {
		// styleSelectionPanel.setBackground(styleSelectionPanel.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		// styleSelectionPanel.setBackgroundMode(SWT.INHERIT_DEFAULT);
		TreeViewer viewer = new TreeViewer(styleSelectionPanel, SWT.BORDER);
		viewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setContentProvider(new ExistingStyleContentProvider(contextView));
		viewer.setLabelProvider(new StylesheetLabelProvider());
		viewer.addSelectionChangedListener(this);
		viewer.setInput(new Object());
		styleSelectionPanel.layout();
		styleSelectionPanel.getParent().layout();
	}

	protected stylesheet getStylesheet() {
		if (ruleset != null) {
			return (stylesheet) ruleset.eContainer();
		}
		return null;
	}

	public ruleset getSelectedRuleset() {
		return ruleset;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			// TODO: Conditions + Declaration -> Readonly
		}

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			Object selectedElement = sSelection.getFirstElement();
			if (selectedElement instanceof ruleset) {
				handleSelectionChanged((ruleset) selectedElement);
			} else {
				ruleset = null;
			}
		}

		updateButtons();
	}

	protected void handleSelectionChanged(ruleset selectedRuleset) {
		this.ruleset = selectedRuleset;

		// Sets all conditions to false. They will be set to true if the selected style contains the same condition
		for (AttributeSelector attribute : conditions.keySet()) {
			conditions.put(attribute, false);
		}

		// Sets all properties to false. They will be set to true if the selected style contains the same property
		for (css_declaration declaration : declarations.keySet()) {
			declarations.put(declaration, false);
		}

		diagramRestriction = false;

		useSelectorName = false;

		cssClass = null;

		for (selector selector : selectedRuleset.getSelectors()) {
			handleSelector(selector);
		}

		handleDeclarations(selectedRuleset.getDeclarations());

		updateContents();
		updateSelectorLabel();
	}

	protected void handleDeclarations(List<css_declaration> declarations) {
		for (css_declaration declaration : declarations) {
			for (css_declaration currentDeclaration : this.declarations.keySet()) {
				if (equals(currentDeclaration.getProperty(), declaration.getProperty())) {
					this.declarations.put(currentDeclaration, true);
					break;
				}
			}
		}
	}

	protected void handleSelector(simple_selector selector) {
		if (getDiagramType(contextView.getDiagram()).equals(getElementName(selector))) {
			diagramRestriction = true;
		}

		if (contextView.getElement().eClass().getName().equals(getElementName(selector))) {
			useSelectorName = true;
		}

		for (CssSelector condition : selector.getSubSelectors()) {
			if (condition instanceof AttributeSelector) {
				AttributeSelector existingAttribute = (AttributeSelector) condition;
				for (AttributeSelector selectedAttribute : conditions.keySet()) {
					if (existingAttribute.getName().equals(selectedAttribute.getName())) {
						conditions.put(selectedAttribute, true);
						break;
					}
				}
			} else if (condition instanceof ClassSelector) {
				cssClass = ((ClassSelector) condition).getName();
			}
		}
	}

	protected void updateContents() {
		disposeContents(conditionsContainer);
		createConditions(conditionsContainer);

		disposeContents(declarationsContainer);
		createDeclarations(declarationsContainer);

		conditionsContainer.layout();
		declarationsContainer.layout();
		tabFolder.layout();
		getDialogArea().layout();
	}

	protected void disposeContents(Composite composite) {
		for (Control control : composite.getChildren()) {
			control.dispose();
		}
	}

	protected void handleSelector(selector selector) {
		if (selector == null) {
			return;
		}

		for (simple_selector simpleSelector : selector.getSimpleselectors()) {
			handleSelector(simpleSelector);
		}

		handleSelector(selector.getSelector());
	}

	@Override
	protected boolean isValid() {
		boolean result = true;

		if (ruleset == null) {
			setError("You must select an existing Style");
			result = false;
		}

		return super.isValid() && result;
	}

}
