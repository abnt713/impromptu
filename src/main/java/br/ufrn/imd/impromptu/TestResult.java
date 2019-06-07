package br.ufrn.imd.impromptu;

public interface TestResult {
	public boolean hasPassed();
	public int getTotalCount();
	public int getIgnoredCount();
	public int getSuccessfulCount();
	public int getFailureCount();
	public String getJUnitOutput();
	public String serialize();
}
