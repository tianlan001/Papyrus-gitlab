/*****************************************************************************
 * Copyright (c) 2017, 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 417095
 *      Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Bug 546965
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.internal.export.image;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.nebula.widgets.nattable.export.image.config.DefaultImageExportBindings;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.papyrus.infra.nattable.export.image.ImageFormat;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;

/**
 * Class to bind default config attributes for exporting image in Papyrus.
 */
public class PapyrusImageExportBindings extends DefaultImageExportBindings {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {

		configRegistry.registerConfigAttribute(ExportConfigAttributes.TABLE_EXPORTER, new PapyrusImageExporter());

		// The Papyrus ExportTableDialog is used by default
		configRegistry.registerConfigAttribute(PapyrusExportConfigAttributes.EXPORT_IMAGE_USE_PAPYRUS_EXPORT_TABLE_DIALOG, Boolean.TRUE);

		// The following attributes are reserved for future use when the engine to export all tables is ready
		configRegistry.registerConfigAttribute(PapyrusExportConfigAttributes.EXPORT_IMAGE_FILENAME, "export_table.png"); //$NON-NLS-1$
		configRegistry.registerConfigAttribute(PapyrusExportConfigAttributes.EXPORT_IMAGE_FORMAT, ImageFormat.PNG);
		configRegistry.registerConfigAttribute(PapyrusExportConfigAttributes.OPEN_RESULT_AFTER_EXPORT, Boolean.FALSE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureLayer(final ILayer layer) {
		layer.registerCommandHandler(new PapyrusImageExportCommandHandler(layer));
	}
}
