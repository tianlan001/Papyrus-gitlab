/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.validate;

/**
 * Holds GMF specific options to be used in validation
 */
public class ValidationOptions {
	private boolean reportSuccess;
	private boolean useGmfLabelSubtitution;	
	
	public static ValidationOptions getDefault() {
		ValidationOptions options = new ValidationOptions();
		options.setReportSuccess(false);		
		return options;
	}

	public boolean isReportSuccess() {
		return reportSuccess;
	}

	public void setReportSuccess(boolean reportSuccess) {
		this.reportSuccess = reportSuccess;
	}

	public boolean isUseGmfLabelSubtitution() {
		return useGmfLabelSubtitution;
	}

	public void setUseGmfLabelSubtitution(boolean useGmfLabelSubtituion) {
		this.useGmfLabelSubtitution = useGmfLabelSubtituion;
	}
}
