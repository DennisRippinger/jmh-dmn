package group.msg;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
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

		DmnDecision groovyDecision;

		DmnDecision jsDecision;

		DmnDecision feelDecision;

		DmnDecision minimalDecision;

		@Setup(Level.Invocation)
		public void doSetup() {
			DmnEngineConfiguration configuration = DmnEngineConfiguration
					.createDefaultDmnEngineConfiguration();

			dmnEngine = configuration.buildEngine();

			minimalDecision = getDmnDecision("minimal.dmn");
			groovyDecision = getDmnDecision("groovy.dmn");
			feelDecision = getDmnDecision("groovy.dmn");
			jsDecision = getDmnDecision("js.dmn");
		}

		private DmnDecision getDmnDecision(String file) {
			DmnModelInstance dmnModelInstance = Dmn
					.readModelFromStream(DmnBenchmark.class.getClassLoader()
							.getResourceAsStream(file));

			List<DmnDecision> dmnDecisions = dmnEngine.parseDecisions(dmnModelInstance);
			return dmnDecisions.get(0);
		}

	}

	@State(Scope.Thread)
	public static class InputMap {

		Map<String, Object> input;

		@Setup(Level.Trial)
		public void doSetup() {
			input = Collections.singletonMap("cellInput", ThreadLocalRandom.current().nextInt(7));
		}

	}


	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void feelDecision(DmnEngineState state, InputMap inputMap) {
		state.dmnEngine.evaluateDecision(state.feelDecision, inputMap.input);
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void jsDecision(DmnEngineState state, InputMap inputMap) {
		state.dmnEngine.evaluateDecision(state.jsDecision, inputMap.input);
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void groovyDecision(DmnEngineState state, InputMap inputMap) {
		state.dmnEngine.evaluateDecision(state.groovyDecision, inputMap.input);
	}


	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void minimalDecision(DmnEngineState state, InputMap inputMap) {
		state.dmnEngine.evaluateDecision(state.minimalDecision, inputMap.input);
	}

}
