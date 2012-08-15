package org.jmxsampler.reader;

import java.util.Map;

/**
 * A reader that can fetch all names and values at once. 
 */
public interface BulkMetricsReader extends MetricsReader {
	Map<MetricName, MetricValue> readAllMetrics() throws MetricReadException;
}