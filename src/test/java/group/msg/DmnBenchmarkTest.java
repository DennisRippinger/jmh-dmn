package group.msg;

import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DmnBenchmarkTest {


	private DmnEngine dmnEngine;
	private DmnDecision dmnDecision;

	@Before
	public void setUp() throws Exception {
		DmnBenchmark.DmnEngineState dmnEngineState = new DmnBenchmark.DmnEngineState();
		dmnEngineState.doSetup();
		dmnEngine = dmnEngineState.dmnEngine;
		dmnDecision = dmnEngineState.dmnDecision;
	}


	@Test
	public void test() throws Exception {

		Map<String, Object> input = Collections.singletonMap("cellInput", 1);

		DmnDecisionResult dmnDecisionResultEntries = dmnEngine.evaluateDecision(dmnDecision, input);
		DmnDecisionResultEntries firstResult = dmnDecisionResultEntries.getFirstResult();

		Integer output = firstResult.getEntry("output");
		assertTrue(output == 1);
	}
}
