package br.ufrn.imd.impromptu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

public class JUnit5TestResult implements TestResult {

	private final TestExecutionSummary summary;
	private final boolean withOutput;
	
	public JUnit5TestResult(TestExecutionSummary summary, boolean withOutput) {
		this.summary = summary;
		this.withOutput = withOutput;
	}
	
	@Override
	public boolean hasPassed() {
		return this.summary.getTestsFailedCount() == 0;
	}

	@Override
	public int getTotalCount() {
		return (int) this.summary.getTestsFailedCount();
	}

	@Override
	public int getIgnoredCount() {
		return (int) this.summary.getTestsSkippedCount();
	}

	@Override
	public int getSuccessfulCount() {
		return (int) this.summary.getTestsSucceededCount();
	}

	@Override
	public int getFailureCount() {
		return (int) this.summary.getTestsFailedCount();
	}

	@Override
	public String getJUnitOutput() {
		List<String> outputs = new ArrayList<String>();
				
		for (Failure failure : this.summary.getFailures()) {
			
			outputs.add(failure.getTestIdentifier().getDisplayName() + ": " + failure.getException().getMessage());
		}
		
		// TODO Auto-generated method stub
		return String.join("\n", outputs);
	}

	@Override
	public String serialize() {
		JSONObject json = new JSONObject();
		json.append("result", this.hasPassed());
		json.append("total", this.getTotalCount());
		json.append("failing", this.getFailureCount());
		json.append("passing", this.getSuccessfulCount());
		json.append("ignored", this.getIgnoredCount());
		
		if (this.withOutput) {
			json.append("output", this.getJUnitOutput());
		}
		
		return json.toString();
	}

}
