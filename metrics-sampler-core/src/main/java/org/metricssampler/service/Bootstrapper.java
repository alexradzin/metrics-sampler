package org.metricssampler.service;

import org.metricssampler.config.Configuration;
import org.metricssampler.sampler.Sampler;

public interface Bootstrapper {
	Configuration getConfiguration();
	Iterable<Sampler> getSamplers();
	int getControlPort();
	String getControlHost();
}
