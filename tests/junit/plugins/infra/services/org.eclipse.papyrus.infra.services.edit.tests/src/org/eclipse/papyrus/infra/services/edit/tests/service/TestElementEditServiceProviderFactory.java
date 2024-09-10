package org.eclipse.papyrus.infra.services.edit.tests.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.services.edit.internal.ElementEditServiceProviderFactory;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditServiceProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Test;

/**
 * JUnit tests for {@link ElementEditServiceProviderFactory} class.
 * No specific implementation (no specific test) provided for start / dispose / init.
 */
public class TestElementEditServiceProviderFactory extends AbstractPapyrusTest {

	@Test
	public void testCreateServiceInstance() {

		Object service = null;
		ServicesRegistry registry = null;
		try {
			registry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
			registry.add(ModelSet.class, Integer.MAX_VALUE, new ModelSet());
			registry.startRegistry();
		} catch (ServiceException e) {
			//ignore
		}

		try {
			ElementEditServiceProviderFactory factory = new ElementEditServiceProviderFactory();
			factory.init(registry);
			service = factory.createServiceInstance();

		} catch (ServiceException e) {
			fail("Factory throws exception during service creation (" + e.getMessage() + ").");
			return;
		}

		assertNotNull("Created service is null.", service);
		assertTrue("Unexpected kind of service created.", service instanceof IElementEditServiceProvider);
	}

}
