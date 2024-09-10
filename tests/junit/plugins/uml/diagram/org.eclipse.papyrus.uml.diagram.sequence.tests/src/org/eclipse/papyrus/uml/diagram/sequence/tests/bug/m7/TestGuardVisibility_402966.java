/*****************************************************************************
 * Copyright (c) 2010, 2014 Soyatec, CEA, and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Soyatec - Initial API and implementation
 *   Christian W. Damus (CEA) - fixing issues in sequence diagram test execution
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionOperandEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.junit.Test;

/**
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public class TestGuardVisibility_402966 extends AbstractNodeTest {

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	@InvalidTest("Since 1.0.0, the Preferences have been disabled and should not be used to control the appearance of diagrams")
	@Test
	public void testPreferencePage() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(shell, "org.eclipse.papyrus.uml.diagram.sequence.preferences.InteractionOperandPreferencePage", null, null);

		dialog.setBlockOnOpen(false);
		dialog.open();

		try {
			IPreferencePage page = (IPreferencePage)dialog.getSelectedPage();
			Control control = page.getControl();
			Control group = getControl((Composite)control, Group.class, "Guard", SWT.NONE);
			assertNotNull("Preference Item Guard Group: ", group);
			Button checkbox = (Button)getControl((Composite)group, Button.class, "Show", SWT.CHECK);
			assertNotNull("Preference Item Visibility CheckBox: ", checkbox);
			assertEquals("Default value of Guard Visibility: ", true, checkbox.getSelection());
		} finally {
			dialog.close();
		}
	}

	private Control getControl(Composite parent, Class<?> controlType, String name, int style) {
		if(parent == null || parent.isDisposed()) {
			return null;
		}
		for(Control child : parent.getChildren()) {
			if(child.getClass() == controlType) {
				if(child instanceof Group && name.equals(((Group)child).getText())) {
					return child;
				}
				if(child instanceof Button && name.equals(((Button)child).getText()) && ((style & child.getStyle())) != 0) {
					return child;
				}
			} else if(child instanceof Composite) {
				Control control = getControl((Composite)child, controlType, name, style);
				if(control != null) {
					return control;
				}
			}
		}
		return null;
	}


	public void testGuardVisibility(InteractionOperandEditPart op, boolean visible) {
		assertNotNull(op);
		WrappingLabel constraintLabel =  op.getPrimaryShape().getInteractionConstraintLabel();
		assertNotNull(constraintLabel);
		if(visible) {
			String text = ((ITextAwareEditPart)op).getParser().getPrintString(new EObjectAdapter(op.resolveSemanticElement()), ParserOptions.NONE.intValue());
			assertEquals("Show Guard:", text, constraintLabel.getText());
		} else {
			assertEquals("Hide Guard:", "", constraintLabel.getText());
		}
	}
}
