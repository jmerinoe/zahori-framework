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

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

/**
 * The type Json 2 xml.
 */
public class Json2XML {

    private static final Logger LOG = LoggerFactory.getLogger(Json2XML.class);

    private String jsonPath;
    private String xmlPath;

    /**
     * Instantiates a new Json 2 xml.
     *
     * @param jsonPath the json path
     * @param xmlPath  the xml path
     */
    public Json2XML(String jsonPath, String xmlPath) {
        this.jsonPath = jsonPath;
        this.xmlPath = xmlPath;
    }

    /**
     * Convert json to xml.
     */
    public void convertJsonToXml() {
        JsonXmlUtils utils = new JsonXmlUtils();

        InputStream input = utils.getInputPathXml(this.jsonPath);
        OutputStream output = utils.getOutputPathJson(this.xmlPath);

        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();

        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);

            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);

            writer.add(reader);

            reader.close();
            writer.close();
        } catch (XMLStreamException | FactoryConfigurationError e) {
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
        Json2XML test = new Json2XML("xml/salida.json",
                "F:\\Workspaces\\CEST\\ORDER_CARE_TEST\\ordercare-tester\\src\\main\\resources\\xml\\salida.xml");
        test.convertJsonToXml();
    }

}