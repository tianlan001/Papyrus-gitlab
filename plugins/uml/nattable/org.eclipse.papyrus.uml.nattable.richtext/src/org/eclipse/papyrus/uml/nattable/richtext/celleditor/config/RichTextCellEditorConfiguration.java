/*****************************************************************************
 * Copyright (c) 2016, 2017, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr 
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 527733
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535545
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.richtext.celleditor.config;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.richtext.RichTextPainter;
import org.eclipse.papyrus.infra.emf.nattable.celleditor.config.SingleStringCellEditorConfiguration;
import org.eclipse.papyrus.infra.emf.utils.TextReferencesHelper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.preferences.RichtextPreferencePage;
import org.eclipse.papyrus.uml.nattable.richtext.celleditor.RichTextCellEditorWithUMLReferences;
import org.eclipse.papyrus.uml.nattable.richtext.celleditor.config.messages.Messages;
import org.eclipse.papyrus.uml.nattable.richtext.cellpainter.PapyrusRichTextCellPainter;
import org.eclipse.papyrus.uml.tools.namereferences.NameReferencesHelper;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This class provides the configuration to use to display and edit richtext in the table.
 */
public class RichTextCellEditorConfiguration extends SingleStringCellEditorConfiguration {

	/**
	 * the id of this editor.
	 */
	private static final String ID = "org.eclipse.papyrus.infra.nattable.richtext.cell.editor.axis.configuration";//$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationId()
	 */
	@Override
	public String getConfigurationId() {
		return ID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationDescription()
	 */
	@Override
	public String getConfigurationDescription() {
		return Messages.RichTextCellEditorConfiguration_Description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 */
	@Override
	public boolean handles(final Table table, final Object axisElement) {
		boolean result = false;
		final Object object = AxisUtils.getRepresentedElement(axisElement);
		if (object instanceof EStructuralFeature) {
			final EStructuralFeature feature = (EStructuralFeature) object;
			result = UMLPackage.eINSTANCE.getComment_Body().equals(feature);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.celleditor.config.SingleStringCellEditorConfiguration#configureCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 */
	@Override
	public void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, getCellPainter(configRegistry, axis, configLabel), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, getCellEditor(configRegistry, axis, configLabel), DisplayMode.EDIT, configLabel);
 	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.celleditor.config.SingleStringCellEditorConfiguration#getCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 */
	@Override
	protected ICellEditor getCellEditor(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getBoolean(RichtextPreferencePage.USE_CK_EDITOR)) {
			return new RichTextCellEditorWithUMLReferences();
		}
		return super.getCellEditor(configRegistry, axis, configLabel);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.celleditor.config.SingleStringCellEditorConfiguration#getCellPainter(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 */
	@Override
	protected ICellPainter getCellPainter(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getBoolean(RichtextPreferencePage.USE_HTML_RENDERER)) {
			final INattableModelManager nattableManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			// If nattable model could be retrieved
			if (null != nattableManager && null != nattableManager.getTable()) {
				final Table table = nattableManager.getTable();
				final TextReferencesHelper helper = new NameReferencesHelper(table.getContext().eResource());


				// Get wraptext and auto resize cell height boolean value from the table
				final boolean wrapTextFlag = StyleUtils.getBooleanNamedStyleValue(table, NamedStyleConstants.WRAP_TEXT);
				final boolean autoResizeCellHeightFlag = StyleUtils.getBooleanNamedStyleValue(table, NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT);

				// Then create the richtext cell painter with these values as parameters
				final PapyrusRichTextCellPainter pCellPainter = new CustomPapyrusRichTextCellPainter(wrapTextFlag, false, autoResizeCellHeightFlag, helper);
				return new BackgroundPainter(new PaddingDecorator(pCellPainter, 2, 5, 2, 5));
			} else {
				// Otherwise, create the richtext cell painter without parameters
				return new BackgroundPainter(new PaddingDecorator(new PapyrusRichTextCellPainter(), 2, 5, 2, 5));
			}
		}
		return super.getCellPainter(configRegistry, axis, configLabel);
	}


	/**
	 * 
	 * A Richtext cell painter using the Papyrus Richtext Painter
	 *
	 */
	private class CustomPapyrusRichTextCellPainter extends PapyrusRichTextCellPainter {

		/**
		 * the helper used to replace link to element by a string representing them
		 */
		private final TextReferencesHelper helper;

		/**
		 * Constructor.
		 *
		 * @param wrapText
		 * @param calculateByTextLength
		 * @param calculateByTextHeight
		 * @param helper
		 *            the helper used to replace link to element by a string representing them
		 */
		public CustomPapyrusRichTextCellPainter(final boolean wrapText, final boolean calculateByTextLength, final boolean calculateByTextHeight, final TextReferencesHelper helper) {
			super(wrapText, calculateByTextLength, calculateByTextHeight);
			Assert.isNotNull(helper);
			this.helper = helper;
			this.richTextPainter = new PapyrusRichTextPainter(wrapText, this.helper);
		}
	}


	/**
	 * 
	 * @author Vincent LORENZO
	 * 
	 *         An extension of the richtext painter, which is able to replace the @link intriduced by Papyrus to reference UML models elements, by the label of these elements
	 *
	 */
	private class PapyrusRichTextPainter extends RichTextPainter {

		/**
		 * the helper used to replace link to element by a string representing them
		 */

		private final TextReferencesHelper helper;

		/**
		 * Constructor.
		 *
		 * @param wordWrap
		 */
		public PapyrusRichTextPainter(final boolean wordWrap, final TextReferencesHelper helper) {
			super(wordWrap);
			this.helper = helper;
		}


		/**
		 * @see org.eclipse.nebula.widgets.richtext.RichTextPainter#paintHTML(java.lang.String, org.eclipse.swt.graphics.GC, org.eclipse.swt.graphics.Rectangle)
		 *
		 * @param html
		 * @param gc
		 * @param bounds
		 */
		@Override
		public void paintHTML(String html, GC gc, Rectangle bounds) {
			super.paintHTML(this.helper.replaceReferences(html), gc, bounds);
		}
	}

}