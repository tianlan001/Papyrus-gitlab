package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

public class ExtensionEditHelper extends ElementEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean defaultApproveCreateRelationshipRequest(CreateRelationshipRequest request) {
		if (!canCreate(request.getSource(), request.getTarget())) {
			return false;
		}
		return super.defaultApproveCreateRelationshipRequest(request);
	}

	public static boolean canCreate(Object source, Object target) {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof Stereotype) {
			return false;
		}
		if (target != null && false == target instanceof Class) {
			return false;
		}
		if (target != null) {
			String metaclassQName = ((Class) target).getQualifiedName();
			if ("uml::Stereotype".equals(metaclassQName)) { //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}

}
