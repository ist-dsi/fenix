package net.sourceforge.fenixedu.util.stork;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sourceforge.fenixedu.domain.candidacyProcess.erasmus.StorkAttributeType;

public class SPUtil {

    private static SPUtil instance;

    private Properties properties;

    private List<Attribute> attributes;

    public static SPUtil getInstance() {
	// if (instance == null) {
	// instance = new SPUtil();
	// }

	return new SPUtil();
    }

    private SPUtil() {
	this.attributes = new ArrayList<Attribute>();

	try {
	    this.properties = new Properties();
	    properties.load(SPUtil.class.getClassLoader().getResourceAsStream("/sp.properties"));
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	loadAttributes();
    }

    private void loadAttributes() {
	for (int i = 1; i <= getNumberOfAttributes(); i++) {
	    String name = this.properties.getProperty(f("attribute%s.name", i));
	    Boolean mandatory = Boolean.valueOf(this.properties.getProperty(f("attribute%s.type", i)));
	    String value = this.properties.getProperty(f("%s.value", name));
	    this.attributes.add(new Attribute(i, StorkAttributeType.getTypeFromStorkName(name), mandatory, value));
	}

    }

    public String getId() {
	return this.properties.getProperty("sp.id");
    }

    public String getUrl() {
	return this.properties.getProperty("sp.url");
    }

    public String getQaLevel() {
	return this.properties.getProperty("sp.qaalevel");
    }

    public String getSpepsCountryUrl() {
	return this.properties.getProperty("speps.country.url");
    }

    public String getSpepsAuthUrl() {
	return this.properties.getProperty("speps.auth.url");
    }

    public Integer getNumberOfAttributes() {
	return Integer.valueOf(this.properties.getProperty("attribute.number"));
    }

    public String getSpInvokeUrl() {
	return this.properties.getProperty("sp.invoke.url");
    }

    public String getSpInvokeUrlToAccessApplication() {
	return this.properties.getProperty("sp.invoke.url.to.access.aplication");
    }

    public List<Attribute> getAttributes() {
	return this.attributes;
    }

    public Attribute getAttributeByName(String name) {
	for (Attribute attr : this.attributes) {
	    if (name.equals(attr.getType().getStorkName()))
		return attr;
	}

	return null;
    }

    public Attribute getAttributeById(Integer id) {
	for (Attribute attr : this.attributes) {
	    if (id.equals(attr.getId()))
		return attr;
	}

	return null;
    }

    private static String f(String format, Object... args) {
	return String.format(format, args);
    }

    public String getAttributesList() {
	StringBuilder sb = new StringBuilder();

	for (Attribute attribute : this.getAttributes()) {
	    if (attribute.isValueAssigned()) {
		sb.append(f("%s:%s:%s;", attribute.getType().getStorkName(), attribute.getMandatory().toString(), attribute
			.getValue()));
	    } else {
		sb.append(f("%s:%s;", attribute.getType().getStorkName(), attribute.getMandatory().toString()));
	    }
	}

	return sb.toString();
    }

    public String getMemcachedHostname() {
	return this.properties.getProperty("memcached.hostname");
    }

    public Integer getMemcachedPort() {
	return new Integer(this.properties.getProperty("memcached.port"));
    }
}
