package group.msg;

import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.openjdk.jmh.annotations.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DmnBenchmark {

	@State(Scope.Thread)
	public static class DmnEngineState {

		DmnEngine dmnEngine;

		DmnDecision dmnDecision;

		@Setup(Level.Invocation)
		public void doSetup() {
			DmnEngineConfiguration configuration = DmnEngineConfiguration
					.createDefaultDmnEngineConfiguration();

			dmnEngine = configuration.buildEngine();
			DmnModelInstance dmnModelInstance = Dmn
					.readModelFromStream(DmnBenchmark.class.getClassLoader()
							.getResourceAsStream("minimal.dmn"));

			List<DmnDecision> dmnDecisions = dmnEngine.parseDecisions(dmnModelInstance);
			dmnDecision = dmnDecisions.get(0);
		}

	}


	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void testMethod(DmnEngineState state) {
		Map<String, Object> input = Collections.singletonMap("cellInput", ThreadLocalRandom.current().nextInt(7));

		state.dmnEngine.evaluateDecision(state.dmnDecision, input);
	}

}
