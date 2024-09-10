/****************************************************************************
 * Copyright (c) 2008, 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual -  Bug 441962
 *	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *****************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.preferences;

/**
 * The file editors page presents the collection of file names and extensions for which the user has
 * registered editors. It also lets the user add new internal or external (program) editors for a
 * given file name and extension.
 *
 * The user can add an editor for either a specific file name and extension (e.g. report.doc), or
 * for all file names of a given extension (e.g. *.doc)
 *
 * The set of registered editors is tracked by the EditorRegistery available from the workbench
 * plugin.
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.PapyrusEmbeddedEditorsPreferencePage} instead.
 */
@Deprecated
public class PapyrusEmbeddedEditorsPreferencePage extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.PapyrusEmbeddedEditorsPreferencePage {

}
