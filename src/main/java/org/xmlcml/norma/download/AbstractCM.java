package org.xmlcml.norma.download;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbstractCM {

	public static Collection<? extends String> getPrefixList(List<? extends AbstractCM> cmList) {
		List<String> prefixList = new ArrayList<String>();
		for (AbstractCM cm : cmList) {
			prefixList.add(cm.getPrefix());
		}
		return prefixList;
	}

	protected String prefix;
	protected String suffix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
