/*******************************************************************************
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
 *    vano - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.jet.JETCompiler;
import org.eclipse.emf.codegen.jet.JETException;

// XXX actually, this is "CopyEmitter" or "AnyFileEmitter" rather than GIFEmitter 
public class GIFEmitter implements BinaryEmitter {
	
	private String myLocation;

	public GIFEmitter(String location) {
		myLocation = location;
	}
	
	public byte[] generate(IProgressMonitor monitor, Object[] args) throws InterruptedException, InvocationTargetException {
		if (monitor != null && monitor.isCanceled()) {
			throw new InterruptedException();
		}
		if (monitor != null) {
			monitor.beginTask(null, 1);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			InputStream is = new BufferedInputStream(JETCompiler.openStream(myLocation));
			for (int next = is.read(); next != -1; next = is.read()) {
				outputStream.write(next);
			}
			is.close();
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		} catch (JETException e) {
			throw new InvocationTargetException(e);
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
		return outputStream.toByteArray();
	}

}
