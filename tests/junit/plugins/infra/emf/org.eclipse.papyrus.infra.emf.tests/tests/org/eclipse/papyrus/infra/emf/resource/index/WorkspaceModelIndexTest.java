/*****************************************************************************
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.infra.emf.resource.index;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.statusWithException;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IIndexManagerListener;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IndexManager;
import org.eclipse.papyrus.infra.emf.internal.resource.index.InternalModelIndex;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.LogTracker;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

/**
 * Test suite for the {@link WorkspaceModelIndex} class.
 */
@FixMethodOrder(MethodSorters.JVM)
public class WorkspaceModelIndexTest extends AbstractPapyrusTest {

	static final boolean DEBUG = Platform.inDebugMode();

	private final Sync sync = new Sync();
	private final CrossReferenceIndexer index = new CrossReferenceIndexer(sync);

	private IndexManager manager;
	private WorkspaceModelIndex<CrossReferenceIndex> fixture;

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final TestName testName = new TestName();

	private IProject referencingProject;
	private IFile referencingFile;
	private URI referencingURI;
	private IProject referencedProject;
	private IFile referencedFile;
	private URI referencedURI;

	public WorkspaceModelIndexTest() {
		super();
	}

	@Test
	public void indexBuilt() throws Exception {
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		assertIndex(index);
	}

	@Test
	public void indexRebuiltOnChanges() throws Exception {
		// Initial build
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		final String newFileName = "the_referencing_model.uml";

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.MANAGER);

		RenameJob rename = new RenameJob(referencingFile, newFileName);

		// Update the identity of the file
		referencingFile = referencingProject.getFile(new Path(newFileName));
		referencingURI = uri(referencingFile);

		// Rename it
		rename.go();

		sync.syncFromTest(); // Wait for indexing to start

		long requestIndex = System.currentTimeMillis();

		// Check the index
		index = fixture.getIndex().get();

		long gotIndex = System.currentTimeMillis();

		assertThat("Didn't have to wait for the index to be rebuilt", (gotIndex - requestIndex), greaterThan(1000L));

		assertIndex(index);
	}

	@Test
	public void indexRebuiltOnDeletion() throws Exception {
		// Initial build
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.MANAGER);

		DeleteJob delete = new DeleteJob(referencingFile);

		// Delete it
		delete.go();

		sync.syncFromTest(); // Wait for indexing to start

		long requestIndex = System.currentTimeMillis();

		// Check the index
		index = fixture.getIndex().get();

		long gotIndex = System.currentTimeMillis();

		assertThat("Didn't have to wait for the index to be rebuilt", (gotIndex - requestIndex), greaterThan(1000L));

		assertIndex(index, true, Collections.<URI> emptySet(), Collections.<URI> emptySet(), false, null, null);
	}

	@FailingTest
	@Test
	public void indexRebuiltOnProjectClose() throws Exception {
		// Initial build
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.UNINDEX);

		referencingFile.getProject().close(null);

		sync.syncFromTest(); // Wait for unindexing to start

		// Check the index
		index = fixture.getIndex().get();

		assertIndex(index, true, Collections.<URI> emptySet(), Collections.<URI> emptySet(), false, null, null);

		referencingFile.getProject().open(null);

		index = fixture.getIndex().get();
		assertIndex(index);
	}

	@Test
	public void indexRebuiltOnProjectDelete() throws Exception {
		// Initial build
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.UNINDEX);

		referencingFile.getProject().delete(true, true, null);

		sync.syncFromTest(); // Wait for unindexing to start

		// Check the index
		index = fixture.getIndex().get();

		assertIndex(index, true, Collections.<URI> emptySet(), Collections.<URI> emptySet(), false, null, null);
	}

	@Test
	public void indexNotifiesOnReindex() throws Exception {
		// Initial build
		fixture.getIndex().get();

		class TestListener extends WorkspaceModelIndexAdapter {
			volatile int started;
			volatile int ended;

			@Override
			protected void indexAboutToCalculateOrRecalculate(WorkspaceModelIndexEvent event) {
				if (referencingProject.equals(event.getProject())) {
					started++;
				}
			}

			@Override
			protected void indexCalculatedOrRecalculated(WorkspaceModelIndexEvent event) {
				if (referencingProject.equals(event.getProject())) {
					ended++;
				}
			}
		}

		TestListener listener = attach(new TestListener());

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.UNINDEX);

		referencingFile.delete(true, null);

		sync.syncFromTest(); // Wait for unindexing to start

		// Access the index again to synchronize
		fixture.getIndex().get();

		assertThat(listener.started, is(1));
		assertThat(listener.ended, is(1));
	}

	@Test
	public void indexRecoversFromCancel() throws Exception {
		// Initial build
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		// Interlock with the indexing for timing purposes
		Future<Job> controlJob = sync.init(() -> getJobWrangler(manager), SyncMode.INDEX, IndexMode.MANAGER, IndexMode.INDEX);

		final String newFileName = "the_referencing_model.uml";

		// Rename the file
		referencingFile.move(referencingFile.getFullPath().removeLastSegments(1).append(newFileName), true, null);

		// Update the identity of the file
		referencingFile = referencingProject.getFile(new Path(newFileName));
		referencingURI = uri(referencingFile);

		// Let the index manager start
		sync.syncFromTest(IndexMode.MANAGER);

		// Cancel the index control job
		JobWaiter controlJobWaiter = waitForStart(controlJob.get(30L, TimeUnit.SECONDS));

		// Let the indexing start
		sync.syncFromTest(IndexMode.INDEX);

		long cancellingAt = System.currentTimeMillis();

		controlJob.get().cancel();

		// Let the indexing finish
		sync.syncFromTest(IndexMode.INDEX);

		controlJobWaiter.waitForJob();

		long restartedAt = System.currentTimeMillis();

		assertThat("Didn't have to wait for the index to recover",
				(restartedAt - cancellingAt), greaterThan(1000L));

		// Check the index
		index = fixture.getIndex().get(30L, TimeUnit.SECONDS);

		assertIndex(index);
	}

	/**
	 * Verify that the index will refresh workspace resources as necessary in order to
	 * properly determine the content-types of files, skip missing resources, etc.
	 *
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=473154
	 */
	@Test
	public void indexRefreshesIfNecessary_bug473154() throws Exception {

		final LogTracker tracker = houseKeeper.cleanUpLater(new LogTracker());
		tracker.start("org.eclipse.core.filesystem");

		final File nativeFile = new File(referencingFile.getLocationURI());
		final Charset charset = Charset.forName(referencedFile.getCharset());
		final String contents = Files.asCharSource(nativeFile, charset).read();

		// De-synchronize the file (by deletion, as in the bugzilla scenario)
		new FileManipulationJob("Sneakily delete file", referencingFile) {

			@Override
			protected void manipulateFile(IFile file, IProgressMonitor monitor) throws CoreException {
				// Sneakily delete the file (don't tell the workspace)
				File nativeFile = new File(file.getLocationURI());
				nativeFile.delete();
			}
		}.go();

		// Initial build
		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		tracker.assertNone(statusWithException(instanceOf(FileNotFoundException.class)));

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.INDEX);

		// Put the file back and synchronize it
		new FileManipulationJob("Restore file", referencingFile) {
			@Override
			protected void manipulateFile(IFile file, IProgressMonitor monitor) throws CoreException {
				try {
					Files.asCharSink(nativeFile, charset).write(contents);
				} catch (IOException e) {
					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to restore test file.", e));
				}
				file.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}
		}.go();

		// Wait for the indexing to start. If it doesn't start within five seconds, then
		// something is wrong with the resource change notifications
		sync.syncFromTest();

		// Synchronize with the index and verify that it got the file
		index = fixture.getIndex().get();
		assertIndex(index);
	}

	/**
	 * Verify that derived resources are not indexed.
	 *
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=573042
	 */
	@Test
	public void indexNotBuiltForDerivedResources() throws Exception {
		IPath newReferencingFilePath = referencingFile.getProjectRelativePath().removeLastSegments(1).append("derived")
				.addFileExtension(referencingFile.getFileExtension());
		IFile newReferencingFile = referencingFile.getProject().getFile(newReferencingFilePath);

		ICoreRunnable furtherSetup = monitor -> {
			SubMonitor sub = SubMonitor.convert(monitor, 2);
			referencingFile.copy(newReferencingFilePath, false, sub.newChild(1));
			newReferencingFile.setDerived(true, sub.newChild(1));
			sub.done();
		};

		// Interlock with the indexing to ensure that we don't try the index before it hears
		// about the file delta
		sync.init(SyncMode.TEST, IndexMode.INDEX);

		newReferencingFile.getWorkspace().run(furtherSetup, new NullProgressMonitor());

		// Wait for the indexing to start. If it doesn't start within five seconds, then
		// something is wrong with the resource change notifications
		sync.syncFromTest();

		Map<IFile, CrossReferenceIndex> index = fixture.getIndex().get();

		// The new Referencing URI is not included in the index
		assertIndex(index, true, Set.of(), Set.of(referencingURI), true, Set.of(), Set.of(referencedURI));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() throws Exception {
		if (DEBUG) {
			System.out.printf("[%s] Starting test ...%n", testName.getMethodName());
		}

		IndexManager.getInstance().pause();

		referencedProject = houseKeeper.createProject("referencedProject");
		referencingProject = houseKeeper.createProject("referencingProject");

		referencedFile = referencedProject.getFile("referenced.uml");
		InputStream input = getTestResource("resources/index/referenced.uml");
		try {
			referencedFile.create(input, true, null);
		} finally {
			input.close();
		}
		referencedURI = uri(referencedFile);

		referencingFile = referencingProject.getFile("referencing.uml");
		input = getTestResource("resources/index/referencing.uml");
		try {
			referencingFile.create(input, true, null);
		} finally {
			input.close();
		}
		referencingURI = uri(referencingFile);

		manager = new IndexManager() {
			@Override
			protected Map<QualifiedName, InternalModelIndex> loadIndices() {
				fixture = new WorkspaceModelIndex<>("test", UMLResource.UML_CONTENT_TYPE_IDENTIFIER, index, 2);
				return Collections.singletonMap(fixture.getIndexKey(), (InternalModelIndex) fixture);
			}
		};
		manager.startManager();
	}

	@After
	public void destroyFixture() {
		try {
			sync.clear();

			// This disposes the fixture, too
			manager.dispose();
			manager = null;
			fixture = null;

			if (DEBUG) {
				System.out.printf("[%s] Finished test.%n", testName.getMethodName());
			}
		} finally {
			IndexManager.getInstance().resume();
		}
	}

	static URI uri(IFile file) {
		return URI.createPlatformResourceURI(file.getFullPath().toString(), true);
	}

	private InputStream getTestResource(String path) throws IOException {
		return Platform.getBundle("org.eclipse.papyrus.infra.emf.tests").getEntry(path).openStream();
	}

	void assertIndex(Map<IFile, CrossReferenceIndex> index) {
		assertIndex(index, true, Collections.<URI> emptySet(), Collections.singleton(referencingURI), true, Collections.<URI> emptySet(), Collections.singleton(referencedURI));
	}

	void assertIndex(Map<IFile, CrossReferenceIndex> index, boolean expectReferenced, Set<URI> referencedImports, Set<URI> referencedExports, boolean expectReferencing, Set<URI> referencingImports, Set<URI> referencingExports) {
		CrossReferenceIndex referenced = index.get(referencedFile);
		assertThat(referenced, expectReferenced ? notNullValue() : nullValue());
		if (referenced != null) {
			assertThat(referenced.imports, is(referencedImports));
			assertThat(referenced.exports, is(referencedExports));
		}

		CrossReferenceIndex referencing = index.get(referencingFile);
		assertThat(referencing, expectReferencing ? notNullValue() : nullValue());

		if (referencing != null) {
			assertThat(referencing.exports, is(referencingImports));
			assertThat(referencing.imports, is(referencingExports));
		}
	}

	<T extends IWorkspaceModelIndexListener> T attach(T listener) {
		fixture.addListener(listener);
		return listener;
	}

	Job getJobWrangler(IndexManager indexManager) {
		Job[] family = Job.getJobManager().find(manager);
		Job result = null;
		for (Job job : family) {
			if (job.getClass().getSimpleName().contains("JobWrangler")) { //$NON-NLS-1$
				result = job;
				break;
			}
		}
		assertThat("Control job not found", result, notNullValue());

		return result;
	}

	//
	// Nested types
	//

	class Sync {
		private volatile SyncMode mode = SyncMode.NONE;
		private volatile Map<IndexMode, Semaphore> semaphores = new EnumMap<>(IndexMode.class);
		private volatile Runnable onSyncFromIndex;

		/**
		 * Create (in the test) a semaphore that gates the index on the test or the test on the index,
		 * according to the mode specified.
		 *
		 * @param syncMode
		 *            the direction of synchronization gating
		 * @param indexMode,&nbsp;more
		 *            which kind of index operations are synchronized with the test
		 */
		void init(SyncMode syncMode, IndexMode indexMode, IndexMode... more) {
			clear();

			this.mode = syncMode;
			EnumSet.of(indexMode, more).forEach(im -> semaphores.put(im, new Semaphore(0)));

			if (this.semaphores.containsKey(IndexMode.MANAGER)) {
				IIndexManagerListener[] listener = { null };
				listener[0] = IIndexManagerListener.startingAdapter(__ -> {
					try {
						syncFromIndex(IndexMode.MANAGER);
					} finally {
						manager.removeIndexManagerListener(listener[0]);
					}
				});
				manager.addIndexManagerListener(listener[0]);
			}
		}

		<V> Future<V> init(Callable<V> callable, SyncMode syncMode, IndexMode indexMode, IndexMode... more) {
			CompletableFuture<V> result = new CompletableFuture<>();

			init(syncMode, indexMode, more);
			onSyncFromIndex = () -> {
				try {
					result.complete(callable == null ? null : callable.call());
				} catch (ThreadDeath d) {
					throw d;
				} catch (Throwable e) {
					result.completeExceptionally(e);
				} finally {
					onSyncFromIndex = null;
				}
			};

			return result;
		}

		/** Called by the indexing job when it needs to sync up with the test. */
		void syncFromIndex(IndexMode mode) {
			if (!semaphores.containsKey(mode)) {
				return;
			}

			if (DEBUG && this.mode != SyncMode.NONE) {
				System.out.printf("[%s] Synching from indexer (%s): %s [%s]%n", testName.getMethodName(), mode, this.mode, Thread.currentThread().getName());
			}

			if (onSyncFromIndex != null) {
				onSyncFromIndex.run();
			}

			switch (this.mode) {
			case INDEX:
				// Index is gated on the test
				semaphores.get(mode).acquireUninterruptibly();
				break;
			case TEST:
				// Test is gated on the index
				semaphores.get(mode).release();
				break;
			default:
				// Pass
			}
		}

		/** Called by the test when it needs to sync up with the index. */
		void syncFromTest() throws Exception {
			assertThat("Must specify index mode", semaphores.size(), is(1));
			syncFromTest(Iterables.getOnlyElement(semaphores.keySet()));
		}

		/** Called by the test when it needs to sync up with the index. */
		void syncFromTest(IndexMode mode) throws Exception {
			if (!semaphores.containsKey(mode)) {
				return;
			}

			if (DEBUG && this.mode != SyncMode.NONE) {
				System.out.printf("[%s] Synching from test (%s): %s [%s]%n", testName.getMethodName(), mode, this.mode, Thread.currentThread().getName());
			}

			switch (this.mode) {
			case INDEX:
				// Index is gated on the test
				semaphores.get(mode).release();
				break;
			case TEST:
				// Test is gated on the index
				if (!semaphores.get(mode).tryAcquire(5L, TimeUnit.SECONDS)) {
					fail("Timed out waiting for indexing job");
				}
				break;
			default:
				// Pass
			}
		}

		void clear() {
			onSyncFromIndex = null;

			this.mode = SyncMode.NONE;

			// Make sure that any blocked threads are released to whatever fate
			semaphores.values().forEach(sema -> sema.release(100));
			semaphores.clear();
		}

	}

	private enum SyncMode {
		NONE, INDEX, TEST,
	}

	private enum IndexMode {
		INDEX, UNINDEX, MANAGER;
	}

	static class CrossReferenceIndexer implements WorkspaceModelIndex.IndexHandler<CrossReferenceIndex> {
		private final Map<IFile, CrossReferenceIndex> index = Maps.newHashMap();

		private final Sync sync;

		/**
		 * Initializes me.
		 *
		 * @param sync
		 *            a protocol for handshaking at start and end of indexing a file
		 */
		public CrossReferenceIndexer(Sync sync) {
			super();

			this.sync = sync;
		}

		private CrossReferenceIndex get(IFile file) {
			CrossReferenceIndex result;

			synchronized (index) {
				result = index.get(file);
				if (result == null) {
					result = new CrossReferenceIndex();
					index.put(file, result);
				}
			}

			return result;
		}

		@Override
		public CrossReferenceIndex index(IFile file) {
			final CrossReferenceIndex result = get(file);

			sync.syncFromIndex(IndexMode.INDEX);

			try {
				Set<URI> imports = result.imports;
				imports.clear();

				ResourceSet resourceSet = new IndexingResourceSet();

				try {
					URI uri = uri(file);

					Resource resource = resourceSet.getResource(uri, true);
					for (Map.Entry<EObject, Collection<EStructuralFeature.Setting>> next : EcoreUtil.ProxyCrossReferencer.find(resource).entrySet()) {
						for (EStructuralFeature.Setting setting : next.getValue()) {
							Object references = setting.get(false);

							if (references instanceof EObject) {
								EObject ref = (EObject) references;
								if (ref.eIsProxy()) {
									URI href = EcoreUtil.getURI(ref).trimFragment();
									if (href.isPlatformResource() && imports.add(href)) {
										// add the corresponding export
										IFile other = file.getWorkspace().getRoot().getFile(new Path(href.toPlatformString(true)));
										get(other).exports.add(uri);
									}
								}
							}
						}
					}
				} finally {
					EMFHelper.unload(resourceSet);
				}
			} finally {
				sync.syncFromIndex(IndexMode.INDEX);
			}

			return result;
		}

		@Override
		public void unindex(IFile file) {
			URI uri = uri(file);

			sync.syncFromIndex(IndexMode.UNINDEX);

			synchronized (index) {
				// Remove this file from the index
				index.remove(file);

				// Remove exports because the deleted resource no longer imports resources
				for (CrossReferenceIndex next : index.values()) {
					next.exports.remove(uri);
				}

				// DO NOT remove imports because resources that still exist will still have
				// proxies referencing the resource, despite that it is deleted
			}
		}
	}

	static class CrossReferenceIndex {
		final Set<URI> imports = Sets.newConcurrentHashSet();
		final Set<URI> exports = Sets.newConcurrentHashSet();
	}

	static class IndexingResourceSet extends ResourceSetImpl {
		@Override
		public EObject getEObject(URI uri, boolean loadOnDemand) {
			// Don't resolve any proxies
			return null;
		}
	}

	static abstract class FileManipulationJob extends Job {
		private final IFile file;

		private final CountDownLatch latch = new CountDownLatch(1);

		FileManipulationJob(String jobName, IFile file) {
			super(jobName);

			this.file = file;

			setRule(file.getProject());
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				manipulateFile(file, monitor);

				// Release the test case
				latch.countDown();

				// Delay a bit to ensure that the test case has to wait for the index to update
				Thread.sleep(TimeUnit.SECONDS.toMillis(3));
			} catch (InterruptedException e) {
				return Status.CANCEL_STATUS;
			} catch (CoreException e) {
				return e.getStatus();
			}

			return Status.OK_STATUS;
		}

		protected abstract void manipulateFile(IFile file, IProgressMonitor monitor) throws CoreException;

		void go() throws InterruptedException {
			schedule();
			await();
		}

		void await() throws InterruptedException {
			latch.await();
		}

	}

	static class RenameJob extends FileManipulationJob {
		private final String newName;

		RenameJob(IFile file, String name) {
			super(String.format("Rename %s as %s", file.getFullPath(), name), file);

			this.newName = name;
		}

		@Override
		protected void manipulateFile(IFile file, IProgressMonitor monitor) throws CoreException {
			file.move(file.getFullPath().removeLastSegments(1).append(newName), true, monitor);
		}
	}

	static class DeleteJob extends FileManipulationJob {
		DeleteJob(IFile file) {
			super(String.format("Delete %s", file.getFullPath()), file);
		}

		@Override
		protected void manipulateFile(IFile file, IProgressMonitor monitor) throws CoreException {
			file.delete(true, monitor);
		}
	}

	final JobWaiter waitForStart(Job job) {
		return new JobWaiter(job, true);
	}

	final JobWaiter waitForEnd(Job job) {
		return new JobWaiter(job, false);
	}

	final class JobWaiter extends JobChangeAdapter {
		private final Job job;
		private final boolean waitForStart;
		private final long startedAt = System.currentTimeMillis();

		private final CountDownLatch jobSignal = new CountDownLatch(1);

		private JobWaiter(Job job, boolean waitForStart) {
			super();

			this.job = job;
			this.waitForStart = waitForStart;

			Job.getJobManager().addJobChangeListener(this);
		}

		public void waitForJob() {

			try {
				jobSignal.await();
			} catch (InterruptedException e) {
				fail("Test was interrupted");
			} finally {
				Job.getJobManager().removeJobChangeListener(this);
			}
		}

		@Override
		public void aboutToRun(IJobChangeEvent event) {
			log(event.getJob(), "about to run");

			if (waitForStart && (event.getJob() == job)) {
				jobSignal.countDown();
			}
		}

		private void log(Job job, String action) {
			if (DEBUG && Strings.nullToEmpty(job.getName()).toLowerCase().contains("index")) {
				long when = System.currentTimeMillis() - startedAt;
				System.out.printf("[%s:%d] Job %s: %s%n", testName.getMethodName(), when, action, job);
			}
		}

		@Override
		public void done(IJobChangeEvent event) {
			log(event.getJob(), "done");

			if (!waitForStart && (event.getJob() == job)) {
				jobSignal.countDown();
			}
		}

	}
}
