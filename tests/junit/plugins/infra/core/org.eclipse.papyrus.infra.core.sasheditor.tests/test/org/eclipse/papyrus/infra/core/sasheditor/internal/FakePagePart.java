/**
 * 
 */
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


/**
 * A Page part for testing purpose. This page cannot be used in the SashContainer.
 * @author cedric dumoulin
 *
 */
public class FakePagePart extends PagePart {

	public FakePagePart() {
		super(null, null);
	}

	@Override
	boolean visit(IPartVisitor visitor) {
		
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		
		
	}

	@Override
	public Control getControl() {
		
		return null;
	}

	@Override
	public void reparent(TabFolderPart parent) {
		
		
	}

	@Override
	public void setFocus() {
		
		
	}

	@Override
	public void garbage() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}

	@Override
	public void disposeThisAndChildren() {
		
		
	}

}
