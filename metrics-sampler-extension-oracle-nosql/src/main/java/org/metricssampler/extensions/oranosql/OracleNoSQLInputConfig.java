package org.metricssampler.extensions.oranosql;

import static org.metricssampler.util.Preconditions.checkArgumentNotNullNorEmpty;

import java.util.List;
import java.util.Map;

import org.metricssampler.config.InputConfig;

public class OracleNoSQLInputConfig extends InputConfig {
	private final List<HostConfig> hosts;

	public OracleNoSQLInputConfig(final String name, final Map<String, Object> variables, final List<HostConfig> hosts) {
		super(name, variables);
		checkArgumentNotNullNorEmpty(hosts, "hosts");
		this.hosts = hosts;
	}

	public List<HostConfig> getHosts() {
		return hosts;
	}

	public static class HostConfig {
		private final String host;
		private final int port;

		public HostConfig(final String host, final int port) {
			this.host = host;
			this.port = port;
		}

		public String getHost() {
			return host;
		}

		public int getPort() {
			return port;
		}

		@Override
		public String toString() {
			return host + ":" + port;
		}
	}
}
