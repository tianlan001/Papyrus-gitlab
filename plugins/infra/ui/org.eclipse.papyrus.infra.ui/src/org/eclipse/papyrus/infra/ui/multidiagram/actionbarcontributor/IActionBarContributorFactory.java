/**
 *
 */
package org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor;

import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * Interface used to get an ActionBarContributor from its ID.
 *
 * @author dumoulin
 * @since 1.2
 *
 */
public interface IActionBarContributorFactory {

	/**
	 * Get an ActionBarContributor by its key. If an ActionBarContributor
	 * already exists for this key, return it.
	 *
	 * @param key
	 * @return
	 */
	public EditorActionBarContributor getActionBarContributor(Object key) throws BackboneException;
}
