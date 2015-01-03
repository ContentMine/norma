package org.xmlcml.norma;

/** simple option for controlling arguments.
 * 
 * @author pm286
 *
 */
public class ArgumentOption {

	private String brief;
	private String lng;
	private String help;
	private Class<?> type;
	private Object defalt;
	private int minCount;
	private int maxCount;
	
	public ArgumentOption() {
	}
	
	public ArgumentOption(
			String brief, 
			String lng, 
			String help,
			Class<?> type,
			Object defalt,
			int minCount,
			int maxCount
			) {
		setBrief(brief);
		setLong(lng);
		setHelp(help);
		setType(type);
		setDefault(defalt);
		setMinCount(minCount);
		setMaxCount(maxCount);
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getLong() {
		return lng;
	}

	public void setLong(String lng) {
		this.lng = lng;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Object getDefault() {
		return defalt;
	}

	public void setDefault(Object defalt) {
		this.defalt = defalt;
	}

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	
	
}
