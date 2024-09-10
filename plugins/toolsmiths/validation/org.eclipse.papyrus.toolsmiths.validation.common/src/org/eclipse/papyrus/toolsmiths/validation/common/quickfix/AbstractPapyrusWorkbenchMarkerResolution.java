/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

/**
 * Abstract {@link WorkbenchMarkerResolution} for Papyrus, providing the Papyrus icon and
 * also the "fix other similar markers" capability.
 */
public abstract class AbstractPapyrusWorkbenchMarkerResolution extends WorkbenchMarkerResolution {

	/** The problem ID that I fix. */
	private final int problemID;

	protected AbstractPapyrusWorkbenchMarkerResolution(int problemID) {
		super();

		this.problemID = problemID;
	}

	/** Query the problem ID that I fix. */
	protected final int getProblemID() {
		return problemID;
	}

	@Override
	public Image getImage() {
		final ImageRegistry registry = Activator.getDefault().getImageRegistry();
		Image image = registry.get(Activator.PAPYRUS_ICON_PATH);
		if (null == image) {
			registry.put(Activator.PAPYRUS_ICON_PATH, AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Activator.PAPYRUS_ICON_PATH));
			image = registry.get(Activator.PAPYRUS_ICON_PATH);
		}
		return image;
	}

	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		return findSimilarMarkers(markers).toArray(IMarker[]::new);
	}

	protected Stream<IMarker> findSimilarMarkers(IMarker[] markers) {
		Function<IMarker, Integer> problemID = CommonMarkerResolutionGenerator::getProblemID;
		Predicate<IMarker> sameProblem = problemID.andThen(Predicate.isEqual(getProblemID())::test)::apply;
		return Stream.of(markers).filter(sameProblem);
	}

}
