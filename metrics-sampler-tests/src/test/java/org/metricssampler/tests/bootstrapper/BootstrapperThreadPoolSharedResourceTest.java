package org.metricssampler.tests.bootstrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.metricssampler.config.Configuration;
import org.metricssampler.config.ConfigurationException;
import org.metricssampler.config.ThreadPoolConfig;
import org.metricssampler.service.Bootstrapper;

public class BootstrapperThreadPoolSharedResourceTest extends BootstrapperTestBase {
	@Test
	public void bootstrapComplete() {
		final Bootstrapper result = bootstrap("thread-pool/complete.xml");
		
		final Configuration config = result.getConfiguration();
		assertNotNull(config);
		final ThreadPoolConfig threadpool = assertSingleSharedResource(config, ThreadPoolConfig.class);
		assertEquals("samplers", threadpool.getName());
		assertEquals(10, threadpool.getCoreSize());
		assertEquals(20, threadpool.getMaxSize());
		assertEquals(60, threadpool.getKeepAliveTime());
	}
	
	@Test(expected=ConfigurationException.class)
	public void bootstrapMissingName() {
		bootstrap("thread-pool/missing-name.xml");
	}
	
	@Test(expected=ConfigurationException.class)
	public void bootstrapMissingSize() {
		bootstrap("thread-pool/missing-size.xml");
	}
	
	@Test(expected=ConfigurationException.class)
	public void bootstrapDuplicateName() {
		bootstrap("thread-pool/duplicate-name.xml");
	}

}
