package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.util.ExpansionModelResourceImpl;

/**
 * Overrides default generated resource to use uuids instead of index
 */
public class CustomExpansionmodelResource extends ExpansionModelResourceImpl {
	/**
	 * Creates an instance of the resource.
	 *
	 * @param uri
	 *            the URI of the new resource.
	 */
	public CustomExpansionmodelResource(URI uri) {
		super(uri);
	}

	@Override
	protected boolean useUUIDs() {
		return true;
	}

}