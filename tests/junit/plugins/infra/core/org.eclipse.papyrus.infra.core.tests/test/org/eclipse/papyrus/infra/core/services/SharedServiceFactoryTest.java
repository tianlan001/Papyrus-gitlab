/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link SharedServiceFactory} class. Tests annotated with
 * {@link Parallel @Parallel} operate on multiple service registries in parallel
 * and the initialization and finalization of services in parallel tests have
 * random short delays to try to mix things up for each execution.
 */
public class SharedServiceFactoryTest {
	private static final int DEFAULT_REGISTRY_COUNT = 3;

	private static Synchronizer sync;

	@Rule
	public final AnnotationRule<Integer> registryCount = AnnotationRule.create(Registries.class, DEFAULT_REGISTRY_COUNT);

	@Rule
	public final AnnotationRule<Boolean> parallelRegistries = AnnotationRule.createExists(Parallel.class);

	private List<ServicesRegistry> registries;

	public SharedServiceFactoryTest() {
		super();
	}

	@Test
	public void uniqueServiceInstances() {
		registries().forEach(this::start);

		assertUniqueServiceInstance(Service1.class, Service1Impl.class);
		assertUniqueServiceInstance(Service2.class, Service2Impl.class);
	}

	@Test
	public void serviceNotInitialized() {
		registries().forEach(this::start);

		Service1Impl s1 = assumeUniqueServiceInstance(Service1.class, Service1Impl.class);
		assertThat(s1.initializedCount.get(), is(0));
	}

	@Test
	public void serviceStartedOnce() {
		registries().forEach(this::start);

		Service1Impl s1 = assumeUniqueServiceInstance(Service1.class, Service1Impl.class);
		assertThat(s1.startedCount.get(), is(1));

		Service2Impl s2 = assumeUniqueServiceInstance(Service2.class, Service2Impl.class);
		assertThat(s2.startedCount.get(), is(1));
	}

	@Test
	public void serviceDisposedOnce() {
		registries().forEach(this::start);

		Service1Impl s1 = assumeUniqueServiceInstance(Service1.class, Service1Impl.class);
		Service2Impl s2 = assumeUniqueServiceInstance(Service2.class, Service2Impl.class);

		destroyFixture();

		assertThat(s1.disposedCount.get(), is(1));
		assertThat(s2.disposedCount.get(), is(1));
	}

	@Test
	public void servicesRecreated() {
		registries().forEach(this::start);

		Service1Impl s1 = assumeUniqueServiceInstance(Service1.class, Service1Impl.class);
		Service2Impl s2 = assumeUniqueServiceInstance(Service2.class, Service2Impl.class);

		destroyFixture();
		createFixture();
		registries().forEach(this::start);

		Service1Impl s1_ = assumeUniqueServiceInstance(Service1.class, Service1Impl.class);
		Service2Impl s2_ = assumeUniqueServiceInstance(Service2.class, Service2Impl.class);

		assertThat(s1, not(sameInstance(s1_)));
		assertThat(s2, not(sameInstance(s2_)));
	}

	@Registries(64)
	@Parallel
	@Test
	public void parallelAccess() {
		// Do a few iterations of this test
		for (int i = 0; i < 5; i++) {
			if (i > 0) {
				// We destroyed the fixture the last time around
				createFixture();
			}

			registries().forEach(this::start);

			Service1Impl s1 = assumeUniqueServiceInstance(Service1.class, Service1Impl.class);
			Service2Impl s2 = assumeUniqueServiceInstance(Service2.class, Service2Impl.class);
			Service3Impl s3 = assumeUniqueServiceInstance(Service3.class, Service3Impl.class);

			destroyFixture();

			assertThat(s1.startedCount.get(), is(1));
			assertThat(s1.disposedCount.get(), is(1));

			assertThat(s2.startedCount.get(), is(1));
			assertThat(s2.disposedCount.get(), is(1));

			assertThat(s3.startedCount.get(), is(1));
			assertThat(s3.disposedCount.get(), is(1));
		}
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		if (parallelRegistries.get()) {
			sync = new ParallelSynchronizer();
		} else {
			// No-op synchronizer
			sync = () -> {
			};
		}

		registries = createServiceRegistries(registryCount.get());
	}

	@After
	public void destroyFixture() {
		if (registries != null) {
			try {
				registries().forEach(this::dispose);
			} finally {
				registries = null;
			}
		}

		sync = null;
	}

	Stream<ServicesRegistry> registries() {
		return parallelRegistries.get() ? registries.parallelStream() : registries.stream();
	}

	<S, T extends S> T assertUniqueServiceInstance(Class<S> serviceInterface, Class<T> serviceImpl) {
		List<S> result = registries()
				.map(r -> getService(r, serviceInterface))
				.distinct()
				.collect(Collectors.toList());
		assertThat(result.size(), is(1));
		return serviceImpl.cast(result.get(0));
	}

	<S, T extends S> T assumeUniqueServiceInstance(Class<S> serviceInterface, Class<T> serviceImpl) {
		List<S> result = registries()
				.map(r -> getService(r, serviceInterface))
				.distinct()
				.collect(Collectors.toList());
		Assume.assumeThat(result.size(), is(1));
		return serviceImpl.cast(result.get(0));
	}

	<S> S getService(ServicesRegistry registry, Class<S> serviceInterface) {
		try {
			return registry.getService(serviceInterface);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}

	List<ServicesRegistry> createServiceRegistries(int count) {
		return IntStream.range(0, count).mapToObj(__ -> createServicesRegistry()).collect(Collectors.toList());
	}

	ServicesRegistry createServicesRegistry() {
		ServicesRegistry result = new ServicesRegistry();
		result.add(new ServicesRegistryTest.ServiceFactoryDesc(Service1.class, Service1Factory.class.getName(), ServiceStartKind.LAZY));
		result.add(new ServicesRegistryTest.ServiceFactoryDesc(Service2.class, Service2Factory.class.getName(), ServiceStartKind.LAZY));
		result.add(new ServicesRegistryTest.ServiceFactoryDesc(Service3.class, Service3Factory.class.getName(), ServiceStartKind.LAZY));
		return result;
	}

	void start(ServicesRegistry registry) {
		try {
			registry.startRegistry();
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}

	void dispose(ServicesRegistry registry) {
		try {
			registry.disposeRegistry();
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}

	public interface Service1 extends IService {
		// Nothing more
	}

	static class Service1Impl implements Service1 {
		AtomicInteger initializedCount = new AtomicInteger();
		AtomicInteger startedCount = new AtomicInteger();
		AtomicInteger disposedCount = new AtomicInteger();

		{
			sync.sync();
		}

		@Override
		public void init(ServicesRegistry servicesRegistry) throws ServiceException {
			initializedCount.incrementAndGet();
		}

		@Override
		public void startService() throws ServiceException {
			sync.sync();
			startedCount.incrementAndGet();
		}

		@Override
		public void disposeService() throws ServiceException {
			sync.sync();
			disposedCount.incrementAndGet();
		}

	}

	public static class Service1Factory extends SharedServiceFactory<Service1> {

		public Service1Factory() {
			super(Service1.class, Service1Impl::new);
		}

	}

	public interface Service2 {
		// Empty
	}

	static class Service2Impl implements Service2 {
		AtomicInteger startedCount = new AtomicInteger();
		AtomicInteger disposedCount = new AtomicInteger();

		{
			sync.sync();
		}

		void start() throws ServiceException {
			sync.sync();
			startedCount.incrementAndGet();
		}

		void dispose() throws ServiceException {
			sync.sync();
			disposedCount.incrementAndGet();
		}

	}

	public static class Service2Factory extends SharedServiceFactory<Service2> {

		public Service2Factory() {
			super(Service2.class);
		}

		@Override
		protected Service2 createSharedInstance() {
			return new Service2Impl();
		}

		@Override
		protected void start(Service2 sharedInstance) throws ServiceException {
			((Service2Impl) sharedInstance).start();
		}

		@Override
		protected void dispose(Service2 sharedInstance) throws ServiceException {
			((Service2Impl) sharedInstance).dispose();
		}
	}

	public interface Service3 {
		// Empty
	}

	static class Service3Impl implements Service3 {
		AtomicInteger startedCount = new AtomicInteger();
		AtomicInteger disposedCount = new AtomicInteger();

		{
			sync.sync();
		}

		void start() throws ServiceException {
			sync.sync();
			startedCount.incrementAndGet();
		}

		void dispose() throws ServiceException {
			sync.sync();
			disposedCount.incrementAndGet();
		}

	}

	public static class Service3Factory extends SharedServiceFactory<Service3> {

		public Service3Factory() {
			super(Service3.class, Service3Impl::new);
		}

		@Override
		protected void start(Service3 sharedInstance) throws ServiceException {
			((Service3Impl) sharedInstance).start();
		}

		@Override
		protected void dispose(Service3 sharedInstance) throws ServiceException {
			((Service3Impl) sharedInstance).dispose();
		}
	}

	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Registries {
		int value() default DEFAULT_REGISTRY_COUNT;
	}

	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Parallel {
		// Empty
	}

	@FunctionalInterface
	interface Synchronizer {
		void sync();
	}

	/**
	 * A synchronizer that introduces short random delays into the
	 * execution of a parallel test case.
	 */
	static final class ParallelSynchronizer implements Synchronizer {
		final Random random = new Random(System.currentTimeMillis());

		@Override
		public void sync() {
			try {
				Thread.sleep(random.nextInt(100) + 20L);
			} catch (Exception e) {
				e.printStackTrace();
				// Never mind. Just proceed
			}
		}
	}
}
