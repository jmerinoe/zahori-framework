package io.zahori.framework.files.transforms;

/*-
 * #%L
 * zahori-framework
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2021 PANEL SISTEMAS INFORMATICOS,S.L
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

/**
 * The type Xml 2 json.
 */
public class XML2Json {

    private static final Logger LOG = LoggerFactory.getLogger(XML2Json.class);

    private String xmlPath;
    private String jsonPath;

    /**
     * Instantiates a new Xml 2 json.
     *
     * @param xmlPath  the xml path
     * @param jsonPath the json path
     */
    public XML2Json(String xmlPath, String jsonPath) {
        this.xmlPath = xmlPath;
        this.jsonPath = jsonPath;
    }

    /**
     * Convert xml to json.
     */
    public void convertXmlToJson() {
        JsonXmlUtils utils = new JsonXmlUtils();

        InputStream input = utils.getInputPathXml(this.xmlPath);
        OutputStream output = utils.getOutputPathJson(this.jsonPath);

        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();

        try {

            XMLEventReader xml = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter json = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            json.add(xml);
            xml.close();
            json.close();
        } catch (XMLStreamException | javax.xml.stream.FactoryConfigurationError e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        XML2Json test = new XML2Json("xml/entrada.xml",
                "F:\\Workspaces\\CEST\\ORDER_CARE_TEST\\ordercare-tester\\src\\main\\resources\\xml\\salida.xml");
        test.convertXmlToJson();
    }

}