package org.eclipse.papyrus.infra.types.core.internal.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ClientContextContentProvider implements IStructuredContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<IClientContext> result = new ArrayList<IClientContext>();
		if (inputElement instanceof Collection<?>) {
			for (Object item : (Collection<?>) inputElement) {
				if (item instanceof IClientContext) {
					result.add((IClientContext) item);
				}
			}

		}
		return result.toArray();
	}
}
