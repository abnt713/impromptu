package br.ufrn.imd.impromptu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JUnit4TestResult implements TestResult {

	private final Result result;
	private final boolean withOutput;
	
	public JUnit4TestResult(Result result, boolean withOutput) {
		this.result = result;
		this.withOutput = withOutput;
	}
	
	public JUnit4TestResult(Result result) {
		this(result, false);
	}
	
	@Override
	public boolean hasPassed() {
		return result.wasSuccessful();
	}
	
	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return result.getRunCount();
	}
	
	@Override
	public int getIgnoredCount() {
		// TODO Auto-generated method stub
		return result.getIgnoreCount();
	}

	@Override
	public int getSuccessfulCount() {
		// TODO Auto-generated method stub
		return this.getTotalCount() - this.getIgnoredCount() - this.getFailureCount();
	}

	@Override
	public int getFailureCount() {
		// TODO Auto-generated method stub
		return result.getFailureCount();
	}

	@Override
	public String getJUnitOutput() {
		
		List<String> outputs = new ArrayList<String>();
		
		for (Failure failure : result.getFailures()) {
			outputs.add(failure.getMessage() + " " + failure.getTrace());
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
