package br.ufrn.imd.impromptu.junit5;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class JUnit5Daemon {
	SummaryGeneratingListener listener = new SummaryGeneratingListener();

	public TestExecutionSummary runFromClass(Class<?> className) {
		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
				.selectors(DiscoverySelectors.selectClass(className)).build();
		Launcher launcher = LauncherFactory.create();
		launcher.discover(request);
		launcher.registerTestExecutionListeners(listener);
		launcher.execute(request);
		
		return listener.getSummary();
	}
}
