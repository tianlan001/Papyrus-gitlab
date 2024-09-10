/*******************************************************************************
 * Copyright (c) 2006 - 2014 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.papyrus.infra.tools.file;


/**
 * Simple interface inspired from IFileSystemAccess in xtext.generator.
 * The prefix 'P' should avoid confusions with the latter. We do not the xbase class
 * directly to avoid a dependency to xtext (not all potential generators are xtend/xtext based).
 */
public interface IPFileSystemAccess {
	
	/**
	 * Always use / instead of File.separationChar (used by location strategies)
	 */
	public static final String SEP_CHAR = "/"; //$NON-NLS-1$

	/**
	 * @param fileName using '/' as file separator
	 * @param contents the to-be-written contents.
	 */
	public void generateFile(String fileName, String contents);
	
	/**
	 * @param fileName using '/' as file separator
	 */
	public void deleteFile(String fileName);
}