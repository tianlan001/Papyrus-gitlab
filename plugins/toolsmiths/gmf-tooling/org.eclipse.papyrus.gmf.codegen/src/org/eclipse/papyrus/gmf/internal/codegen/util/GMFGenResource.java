/******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  Borland - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.util;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.BasicResourceHandler;
import org.eclipse.papyrus.gmf.internal.common.ToolingResourceFactory;
import org.eclipse.papyrus.gmf.internal.common.migrate.Messages;
import org.eclipse.papyrus.gmf.internal.common.migrate.MigrationResource;

/**
 * FIXME consider use of IExecutableExtensionFactory instead of few factory classes
 */
public class GMFGenResource extends MigrationResource {

	/**
	 * Migration from 2005 model to 2006 dynamic model and then once again to 2008 and 2009 models 
	 */
	public static class Factory extends ToolingResourceFactory {
		@Override
		public Resource createResource(URI uri) {
			GMFGenResource r = new GMFGenResource(uri);
			r.getDefaultLoadOptions().put(XMLResource.OPTION_RESOURCE_HANDLER, new X());
			return r;
		}
	}

	/**
	 * Migration from 2006 (v2.0) to 2008 (v2.1) model
	 * and from 2008 (v2.1) to 2009 (v2.2)
	 */
	public static class Factory2 extends ToolingResourceFactory {
		@Override
		public Resource createResource(URI uri) {
			ToolResource r = (ToolResource) super.createResource(uri);
			r.getDefaultLoadOptions().put(XMLResource.OPTION_RESOURCE_HANDLER, new X());
			return r;
		}
	}

	private GMFGenResource(URI uri) {
		super(uri);
	}

	@Override
	protected org.eclipse.papyrus.gmf.internal.common.migrate.MigrationDelegate createDelegate() {
		MigrationDelegate migrationHelper = new MigrationDelegate();
		migrationHelper.init();
		return migrationHelper;
	}

	private static class X extends BasicResourceHandler {
		@Override
		public void postLoad(XMLResource resource, InputStream inputStream, Map<?, ?> options) {
			LinkedList<EObject> migrated = new LinkedList<EObject>();
			boolean needWarning = false;
			for (EObject o : resource.getContents()) {
				if (o != null && "GenEditorGenerator".equals(o.eClass().getName())) { //$NON-NLS-1$
					EObject result = o;
					if (ModelVersions.GMFGEN_2_0.equals(o.eClass().getEPackage().getNsURI())) {
						final Migrate2008 migrate = new Migrate2008();
						EObject m = migrate.go(o);
						if (m != null) {
							result = m;
							if (migrate.wasMigrationApplied()) {
								needWarning = true;
							}
						}
					}
					if (ModelVersions.GMFGEN_2_1.equals(result.eClass().getEPackage().getNsURI())) {
						final Migrate2009 migrate = new Migrate2009();
						EObject m = migrate.go(result);
						if (m != null) {
							needWarning = true;
							result = m;
						}
					}
					migrated.add(result);
				} else {
					migrated.add(o);
				}
			}
			if (needWarning) {
				resource.getWarnings().add(0, MigrationResource.createMessageDiagnostic(resource, Messages.oldModelVersionLoadedMigrationRequired));
			}
			resource.getContents().clear();
			resource.getContents().addAll(migrated);
		}
	}
}
