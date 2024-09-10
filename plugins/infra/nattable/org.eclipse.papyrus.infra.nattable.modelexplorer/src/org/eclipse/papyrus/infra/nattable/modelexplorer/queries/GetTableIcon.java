/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters (CEA LIST) laurent.wouters@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.modelexplorer.queries;

import org.eclipse.papyrus.emf.facet.custom.metamodel.custompt.IImage;
import org.eclipse.papyrus.emf.facet.custom.ui.ImageUtils;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.nattable.common.utils.TableUtil;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.ui.editorsfactory.AbstractGetEditorIconQuery;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/** Return the path to the icon of the corresponding table */
public class GetTableIcon extends AbstractGetEditorIconQuery implements IJavaQuery2<Table, IImage> {

	@Override
	public IImage evaluate(Table source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		PolicyChecker checker = PolicyChecker.getFor(source);
		ViewPrototype prototype = TableUtil.getPrototype(source, checker, false);

		// If this is not an unavailable view prototype and is not in current viewpoints, display the grayed icon if possible
		if (!ViewPrototype.UNAVAILABLE_VIEW.equals(prototype) && !checker.isInViewpoint(prototype.getRepresentationKind())) {
			// If the grayed icon is not set, use the unavailable view prototype icon
			return null == prototype.getGrayedIconURI() || prototype.getGrayedIconURI().isEmpty() ? ImageUtils.wrap(ViewPrototype.UNAVAILABLE_VIEW.getIconURI()) : ImageUtils.wrap(prototype.getGrayedIconURI());
		}
		return ImageUtils.wrap(prototype.getIconURI());
	}
}
