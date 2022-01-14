package org.jhotdraw.app;


import java.io.Serializable;

public class AbstractApplicationModelProduct implements Serializable {
	private String name;
	private String version;
	private String copyright;

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setName(String newValue, AbstractApplicationModel abstractApplicationModel) {
		String oldValue = name;
		name = newValue;
		abstractApplicationModel.firePropertyChange(AbstractApplicationModel.NAME_PROPERTY, oldValue, newValue);
	}

	public void setVersion(String newValue, AbstractApplicationModel abstractApplicationModel) {
		String oldValue = version;
		version = newValue;
		abstractApplicationModel.firePropertyChange(AbstractApplicationModel.VERSION_PROPERTY, oldValue, newValue);
	}

	public void setCopyright(String newValue, AbstractApplicationModel abstractApplicationModel) {
		String oldValue = copyright;
		copyright = newValue;
		abstractApplicationModel.firePropertyChange(AbstractApplicationModel.COPYRIGHT_PROPERTY, oldValue, newValue);
	}
}