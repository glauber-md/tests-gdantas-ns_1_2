/**
 * 
 */
package com.gdantas.data.ws.input;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
@XmlRootElement
public class InCep {

	@XmlElement
	@NotBlank
	@Pattern(regexp="[0-9]{8}")
	private String cep;
}
