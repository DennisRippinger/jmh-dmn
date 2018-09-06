# Performance Test of Camunda DMN with JMH

This is a small test to evaluate the performance of Camundas DMN Implementation. It is meant together with a performance test of Drools - [JMH-DMN](https://github.com/DennisRippinger/jmh-drools). 

The test uses a rule set which does simple logic operations on the input. It uses different logic evaluation engines: FEEL, Groovy, JavaScript and a rule set with does just a plain comparison (minimalDecision).


![Drools Result](img/jmh_result.png "Result of Drools")
