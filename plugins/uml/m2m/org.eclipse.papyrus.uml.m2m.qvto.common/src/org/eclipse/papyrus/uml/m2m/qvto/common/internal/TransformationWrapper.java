/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.internal;

import org.eclipse.papyrus.uml.m2m.qvto.common.transformation.IImportTransformation;

public class TransformationWrapper implements Schedulable {

	protected IImportTransformation transformation;

	public TransformationWrapper(IImportTransformation transformation) {
		this.transformation = transformation;
	}

	@Override
	public boolean isComplete() {
		return transformation.isComplete();
	}

	@Override
	public String getName() {
		return transformation.getModelName();
	}

	@Override
	public void start() {
		transformation.run(false);
	}

	@Override
	public void cancel() {
		transformation.cancel();
	}

}
