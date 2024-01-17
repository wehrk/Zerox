// triggers on zero-crossings and calculates number of zero-crossings per fixed amount of time
// v.1

Zerox : UGen {
	*ar { |in, mode = 0, cfreq = 10, mul = 1.0, add = 0.0|
		var sr, input, xingt, update, counter, out;
		sr = 1/SampleRate.ir;
		input = in;
		xingt = Trig1.ar(input, sr) + Trig1.ar(input.neg, sr);
		update = Impulse.ar(cfreq);
		counter = PulseCount.ar(input, Delay1.ar(update));
		counter = Latch.ar(counter, update);
		out = Select.ar(mode, [xingt, counter, Changed.ar(counter.wrap(-1, 1).abs)]);
		^out.madd(mul, add)
	}
}

// mode 0 - 2
// 0 – triggers on zero-crossings
// 1 - calculates number of zero-crossings per fixed amount of time
// 2 - sequencer mode
// сfreq is a frequency of update of a counting period
