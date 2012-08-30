package org.jmxsampler.config.loader.xbeans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmxsampler.config.DictionaryPlaceholderConfig;
import org.jmxsampler.config.PlaceholderConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("dictionary-placeholder")
public class DictionaryPlaceholderXBean extends PlaceholderXBean {
	@XStreamImplicit
	private List<EntryXBean> entries;
	
	public List<EntryXBean> getEntries() {
		return entries;
	}

	public void setEntries(final List<EntryXBean> entries) {
		this.entries = entries;
	}

	@Override
	public PlaceholderConfig toConfig() {
		super.validate();
		final Map<String, String> map = new HashMap<String, String>();
		for (final EntryXBean entry : entries) {
			entry.validate();
			map.put(entry.getKey(), entry.getValue());
		}
		return new DictionaryPlaceholderConfig(getKey(), map);
	}

}