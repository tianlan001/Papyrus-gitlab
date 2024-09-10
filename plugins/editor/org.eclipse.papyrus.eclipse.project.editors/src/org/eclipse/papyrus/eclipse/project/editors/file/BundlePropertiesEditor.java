/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;


public class BundlePropertiesEditor extends AbstractFileEditor {

	/** the build config */
	private Properties bundleConfig;

	/** the buidl file */
	private File bundleFile;

	private String filePath;

	public BundlePropertiesEditor(IProject project, String filePath) {
		super(project);
		this.filePath = filePath;

	}

	@Override
	public void init() {
		this.bundleFile = getBundleProperties();
		// if(this.bundleFile != null && this.bundleFile.exists()) {
		try {
			this.bundleConfig.load(new FileInputStream(this.bundleFile));
		} catch (FileNotFoundException e) {
			Activator.log.error(e);
		} catch (IOException e) {
			Activator.log.error(e);
		}
		// }
	}

	@Override
	public void createFiles(Set<String> files) {
		

	}

	@Override
	protected void doSave() {
		

	}

	public String getValue(String propertyName) {
		return this.bundleConfig.getProperty(propertyName);

	}





	private File getBundleProperties() {
		return this.bundleFile = FileUtil.getWorkspaceFile("/" + getProject().getName() + "/" + filePath + ".properties"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
