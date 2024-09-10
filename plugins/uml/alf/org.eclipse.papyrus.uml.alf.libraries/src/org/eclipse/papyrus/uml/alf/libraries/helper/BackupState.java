/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Jeremie Tatibouet (CEA LIST)
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.libraries.helper;

import java.sql.Timestamp;

public class BackupState {

	public enum EditionStatus {
		NONE, SAVED, MERGED
	};

	public Timestamp timestamp;

	public EditionStatus status;

	public BackupState() {
		this.timestamp = null;
		this.status = EditionStatus.NONE;
	}

	public BackupState(Timestamp timestamp, EditionStatus status) {
		this.timestamp = timestamp;
		this.status = status;
	}
}
