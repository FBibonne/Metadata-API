package fr.insee.rmes.api.codes.cj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="CategorieJuridique")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorieJuridique {

	private String code = null;
	private String uri = null;
	private String intitule = null;
	private String issued = null;
	private String valid = null;

	public CategorieJuridique() {} // No-args constructor needed for JAXB

	public CategorieJuridique(String code) {
		this.code = code;
	}
	
	@JacksonXmlProperty(isAttribute=true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JacksonXmlProperty(isAttribute=true)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@JacksonXmlProperty(localName="Intitule")
	@JsonProperty(value="intitule")
	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	@JacksonXmlProperty(localName="DateDebutValidite")
	@JsonProperty(value="dateDebutValidite")
	public String getIssued() {
		return issued;
	}

	public void setIssued(String issued) {
		this.issued = issued;
	}
	
	@JacksonXmlProperty(localName="DateFinValidite")
	@JsonProperty(value="dateFinValidite")
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
}
