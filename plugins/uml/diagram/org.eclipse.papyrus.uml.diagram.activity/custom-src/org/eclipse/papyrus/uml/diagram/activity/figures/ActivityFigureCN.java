package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;

public class ActivityFigureCN extends ActivityFigure {

	private class ActivityLayoutManagerCN extends ActivityLayoutManager {

		/**
		 * For preferred size computation considers only the name label size
		 */
		@Override
		protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
			return getNameLabel() != null ? getNameLabel().getPreferredSize().getCopy() : new Dimension(10, 10);
		}
	}

	/**
	 * Overrides layout manager because child activity nodes have less restricted minimum sizes
	 * see bug 465804: [Activity] Cannot resizing "nested" Activity (it only gets larger and larger)
	 */
	public ActivityFigureCN() {
		super();
		setLayoutManager(new ActivityLayoutManagerCN());
	}
}
