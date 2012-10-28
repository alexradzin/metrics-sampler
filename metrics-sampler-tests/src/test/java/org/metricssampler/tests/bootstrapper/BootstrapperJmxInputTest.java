package org.metricssampler.tests.bootstrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.metricssampler.config.Configuration;
import org.metricssampler.extensions.jmx.JmxInputConfig;
import org.metricssampler.service.Bootstrapper;

public class BootstrapperJmxInputTest extends BootstrapperTestBase {
	@Test
	public void bootstrapComplete() {
		final Bootstrapper result = bootstrap("jmx/complete.xml");
		
		final Configuration config = result.getConfiguration();
		assertNotNull(config);
		final JmxInputConfig item = assertSingleInput(config, JmxInputConfig.class);
		assertEquals("jmx", item.getName());
		assertEquals("username", item.getUsername());
		assertEquals("password", item.getPassword());
		assertEquals("url", item.getUrl());
		assertEquals("provider.packages", item.getProviderPackages());
		assertFalse(item.isPersistentConnection());
		assertSocketOptions(item.getSocketOptions(), 5, 10, 16384, 32768);
		assertEquals(1, item.getConnectionProperties().size());
		assertEquals("value", item.getConnectionProperties().get("key"));
		assertEquals(1, item.getIgnoredObjectNames().size());
		assertEquals("ignored_.+", item.getIgnoredObjectNames().iterator().next().toString());
		assertSingleStringVariable(item.getVariables(), "string", "value");
	}
	
	@Test
	public void bootstrapMinimal() {
		final Bootstrapper result = bootstrap("jmx/minimal.xml");
		
		final Configuration config = result.getConfiguration();
		assertNotNull(config);
		final JmxInputConfig item = assertSingleInput(config, JmxInputConfig.class);
		assertEquals("jmx", item.getName());
		assertEquals("username", item.getUsername());
		assertEquals("password", item.getPassword());
		assertEquals("url", item.getUrl());
		assertEquals("provider.packages", item.getProviderPackages());
		assertTrue(item.isPersistentConnection());
		assertNull(item.getSocketOptions());
		assertTrue(item.getConnectionProperties().isEmpty());
		assertTrue(item.getIgnoredObjectNames().isEmpty());
	}
}