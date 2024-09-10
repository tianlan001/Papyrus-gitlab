/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher  ansgar.radermacher@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 496299
 *
 *****************************************************************************/

package org.eclipse.papyrus.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.emf.resource.ICrossReferenceIndex;
import org.eclipse.papyrus.infra.ui.util.EditorUtils;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;


public class PapyrusMatchingStrategy implements IEditorMatchingStrategy {

	/**
	 * Check whether an existing editor matches a new editor input, i.e. if the existing editor should
	 * get focus instead of opening a second one. Whereas the passed editor reference always refers to a Papyrus
	 * editor, the passed editor input may not be related to UML, it might for instance be a Java or text
	 * file. Therefore, the code verifies that the new editor input is either a notation, uml or di file
	 *
	 * @see org.eclipse.ui.IEditorMatchingStrategy#matches(org.eclipse.ui.IEditorReference, org.eclipse.ui.IEditorInput)
	 *
	 * @param editorRef
	 *            a reference to an opened Papyrus editor
	 * @param newEInput
	 *            the new editor input
	 * @return true, if the new input matches the existing editor reference.
	 */
	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput newEInput) {
		boolean result = false;

		URI newURI = EditorUtils.getResourceURI(newEInput);
		if (newURI != null) {
			URI existingURI = EditorUtils.getResourceURI(editorRef);
			if (existingURI != null) {
				String extension = newURI.fileExtension();

				// Some processing is only appropriate to Papyrus model resources
				if ("uml".equals(extension) || "di".equals(extension) || "notation".equals(extension)) {
					// Resolve the root unit in case of a shard
					ICrossReferenceIndex index = ICrossReferenceIndex.getInstance(null);
					newURI = EditorUtils.resolveShardRoot(index, newURI);
					existingURI = EditorUtils.resolveShardRoot(index, existingURI);
				}

				// Are they the same?
				result = newURI.equals(existingURI);
			}
		}

		return result;
	}
}
