/****************************************************************************
 * Copyright (c) 2008, 2018 Atos Origin.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *		Patrick Tessier (CEA LIST)- modifications
 *		Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 419357
 *		Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 531729
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.preferences.pages;

import org.eclipse.gmf.runtime.notation.JumpLinkStatus;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Activator;
import org.eclipse.papyrus.infra.gmfdiag.preferences.PapyrusPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.BackgroundColor;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.ConnectionGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.DecorationGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.DimensionGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.ExternalReferenceGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.FontGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.NodeColorGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.RestoreElementGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.RulersAndGridGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorBackgroundColor;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorConnectionGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorDecorationGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorDimensionGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorFontGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorNodeColorGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor.EditorRulersAndGridGroup;
import org.eclipse.papyrus.infra.gmfdiag.preferences.utils.GradientPreferenceConverter;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

/**
 * The default preference page all elements of diagrams
 *
 */
public class PapyrusAllDiagramsPreferencePage extends AbstractPapyrusPreferencePage {

	@Override
	protected void createPageContents(final Composite parent) {
		Group contentGroup = new Group(parent, 2);
		contentGroup.setLayout(new GridLayout(4, false));

		// FontGroup
		FontGroup fontGroupComposite = new EditorFontGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(fontGroupComposite);
		// color
		NodeColorGroup colorGroupForNodeComposite = new EditorNodeColorGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(colorGroupForNodeComposite);


		// router for links
		ConnectionGroup connectionGroupComposite = new EditorConnectionGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(connectionGroupComposite);

		// background
		BackgroundColor backgroundColorGroup = new EditorBackgroundColor(contentGroup, getTitle(), this);
		addPreferenceGroup(backgroundColorGroup);

		DecorationGroup decorationGroupComposite = new EditorDecorationGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(decorationGroupComposite);

		DimensionGroup dimensionGroup = new EditorDimensionGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(dimensionGroup);

		RulersAndGridGroup viewGroupComposite = new EditorRulersAndGridGroup(parent, getTitle(), this);
		addPreferenceGroup(viewGroupComposite);
		
		final RestoreElementGroup restoreElementGroup = new RestoreElementGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(restoreElementGroup);
		
		final ExternalReferenceGroup externalReferenceGroup = new ExternalReferenceGroup(contentGroup, getTitle(), this);
		addPreferenceGroup(externalReferenceGroup);
	}


	@Override
	protected String getBundleId() {
		return Activator.PLUGIN_ID;
	}

	/**
	 * use to init default preferences at the Papyrus level
	 *
	 * @param store
	 *            the preference store
	 */
	public static void initDefaults(final IPreferenceStore store) {
		Display.getDefault().syncExec(new Runnable() { // to be in the thread ui

			public void run() {

				// Nodes
				PreferenceConverter.setDefault(store, PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.COLOR_FILL), new org.eclipse.swt.graphics.RGB(255, 255, 255));
				PreferenceConverter.setDefault(store, PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.COLOR_LINE), new org.eclipse.swt.graphics.RGB(0, 0, 0));

				// Set the default for the gradient
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.GRADIENT_POLICY), false);
				GradientPreferenceConverter gradientPreferenceConverter = new GradientPreferenceConverter(new org.eclipse.swt.graphics.RGB(255, 255, 255), new org.eclipse.swt.graphics.RGB(0, 0, 0), 0, 0);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.COLOR_GRADIENT), gradientPreferenceConverter.getPreferenceValue());

				// Links
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.SMOOTHNESS), Smoothness.NONE);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.JUMPLINK_STATUS), JumpLinkStatus.NONE);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.JUMPLINK_TYPE), JumpLinkStatus.NONE);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.JUMPLINK_REVERSE), false);

				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.ROUTING_STYLE), Routing.MANUAL);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.ROUTING_POLICY_OBSTRUCTION), false);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.ROUTING_POLICY_DISTANCE), false);

				// decoration
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.SHADOW), false);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.ELEMENTICON), false);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.QUALIFIEDNAME), false);
				// dimension
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.WIDTH), 100);
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.HEIGHT), 100);

				// Rulers and grid group
				RulersAndGridGroup.initDefaults(store);

				// Restore Element Group
				RestoreElementGroup.initDefaults(store);
				
				ExternalReferenceGroup.initDefaults(store);
				
				//the default value for the Draw connection point preference
				store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.DRAW_CONNECTION_POINT),Boolean.FALSE);
			}
		});

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeAllPreferences() {
		super.storeAllPreferences();
		IPreferenceStore preferenceStore = getPreferenceStore();
		if (preferenceStore instanceof PapyrusPreferenceStore) {
			((PapyrusPreferenceStore) preferenceStore).deleteAllSubPreference(PreferencesConstantsHelper.PAPYRUS_EDITOR_PREFERENCE_PREFIX);
		}

	}
}
