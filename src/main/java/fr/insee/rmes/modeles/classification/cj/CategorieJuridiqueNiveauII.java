package fr.insee.rmes.modeles.classification.cj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

@JacksonXmlRootElement(localName = "CategorieJuridiqueNiveauII")
@XmlAccessorType(XmlAccessType.FIELD)
@Schema(description = "Objet représentant une catégorie juridique (niveau 2)")
public class CategorieJuridiqueNiveauII {
    @Schema(example = "10")
    private String code;
    @Schema(example = "http://id.insee.fr/codes/cj/n2/10")
    private String uri;
    @Schema(example = "Entrepreneur individuel")
    private String intitule;

    public CategorieJuridiqueNiveauII() {} // No-args constructor needed for JAXB

    public CategorieJuridiqueNiveauII(String code) {
        this.code = code;
    }

    @JacksonXmlProperty(isAttribute = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JacksonXmlProperty(isAttribute = true)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @JacksonXmlProperty(localName = "Intitule")
    @JsonProperty(value = "intitule")
    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}
