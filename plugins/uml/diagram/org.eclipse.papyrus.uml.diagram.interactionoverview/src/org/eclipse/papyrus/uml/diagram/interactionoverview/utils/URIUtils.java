/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.utils;


public class URIUtils {


	private static final String PREFIX = "platform:/resource/";

	private static final String SUFFIX = "/model.notation";

	public static String getTimestampedURI() {
		return PREFIX + System.currentTimeMillis() + SUFFIX;
	}
}
