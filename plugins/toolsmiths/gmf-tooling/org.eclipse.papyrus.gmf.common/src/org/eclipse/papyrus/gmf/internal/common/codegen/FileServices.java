/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * @author artem
 */
class FileServices {

	public static String getFileContents(IFile file) {
		StringBuffer contents = new StringBuffer();
		char[] buffer = new char[1024];
		int count;
		try {
			Reader in = new InputStreamReader(file.getContents(true), file.getCharset());
			try {
				while ((count = in.read(buffer)) > 0) {
					contents.append(buffer, 0, count);
				}
			} finally {
				in.close();
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
		return contents.toString();
	}

	/**
	 * @return <code>true</code> if the file contains the input stream contents
	 */
//	public static boolean contains(IFile f, InputStream is) {
//		int fc = 0;
//		int ic = 0;
//		InputStream fs = null;
//		try {
//			fs = f.getContents(true);
//			while ((fc = fs.read()) == (ic = is.read()) && fc >= 0);
//		} catch (CoreException ce) {
//		} catch (IOException ioe) {
//		} finally {
//			if (fs != null) {
//				try {
//					fs.close();
//				} catch (IOException ioe) {
//				}
//			}
//		}
//		return fc <0 && ic < 0;
//	}

}
