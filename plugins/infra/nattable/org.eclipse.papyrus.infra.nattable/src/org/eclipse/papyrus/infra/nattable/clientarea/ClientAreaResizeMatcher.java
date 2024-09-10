/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.clientarea;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.swt.events.MouseEvent;

/**
 *
 */
public class ClientAreaResizeMatcher extends MouseEventMatcher {

    protected ILayer rowHeaderLayer;

    public ClientAreaResizeMatcher(ILayer rowHeaderLayer) {
        this.rowHeaderLayer = rowHeaderLayer;
    }

    @Override
    public boolean matches(NatTable natTable, MouseEvent event, LabelStack regionLabels) {
        if (regionLabels != null && regionLabels.hasLabel(GridRegion.COLUMN_HEADER) && (event.x >= this.rowHeaderLayer.getWidth() && event.x <= this.rowHeaderLayer.getWidth() + 4)) {
          // it is +4 because it should react on a 4 pixed wide range to the right and not only on the exact pixel position. The row resize has +5 to both directions
            return true;
        }
        return false;
    }
}
