// triggers on zero-crossings and calculates number of zero-crossings per fixed amount of time

Zerox : UGen {

	*ar { |in, mode = 0, cfreq = 10, mul = 1.0, add = 0.0|
		var sr = 1/SampleRate.ir;
		var input = in;
		var x = Trig1.ar(input, sr) + Trig1.ar(input.neg, sr);
		var update = Impulse.ar(cfreq);
		var counter = PulseCount.ar(input, Delay1.ar(update));
		counter = Latch.ar(counter, update);
		^Select.ar(mode, [x, counter, Changed.ar(counter.wrap(-1, 1).abs)]).madd(mul, add)
	}

}

// mode 0 - 2
// 0 – triggers on zero-crossings
// 1 - calculates number of zero-crossings per fixed amount of time
// 2 - sequencer mode
// сfreq is an update frequency of a counting period
