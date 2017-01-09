/*
 * Copyright (c)2012 Poohdish Rattanavijai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.s1451552.grabble.kmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class NavigationSaxHandler extends DefaultHandler{

	private boolean in_kmltag = false; 
	private boolean in_placemarktag = false; 
	private boolean in_nametag = false;
	private boolean in_descriptiontag = false;
	private boolean in_geometrycollectiontag = false;
	private boolean in_linestringtag = false;
	private boolean in_pointtag = false;
	private boolean in_coordinatestag = false;

	private StringBuffer buffer;

    private Placemark placemark;
	private ArrayList<Placemark> placemarks;
    private ArrayList<Placemark> malformed;

	/*
	 * Get parsed placemarks as
	 * an ArrayList<Placemark> object
	 */
	public ArrayList<Placemark> getParsedData() {
        placemarks.removeAll(malformed);
		return this.placemarks;
	} 

	/*
	 * Parser methods
	 */
	@Override 
	public void startDocument() throws SAXException { 
		this.placemarks = new ArrayList<>();
        this.malformed = new ArrayList<>();
	} 

	@Override 
	public void endDocument() throws SAXException { }

    /** Gets be called on opening tags like:
	 * <tag> 
	 * Can provide attribute(s), when xml was like: 
	 * <tag attribute="attributeValue">
     */
	@Override 
	public void startElement(String namespaceURI, String localName, 
			String qName, Attributes atts) throws SAXException { 
		if (localName.equals("kml")) { 
			this.in_kmltag = true;
		} else if (localName.equals("Placemark")) { 
			this.in_placemarktag = true;
			placemark = new Placemark();
		} else if (localName.equals("name")) { 
			this.in_nametag = true;
		} else if (localName.equals("description")) { 
			this.in_descriptiontag = true;
		} else if (localName.equals("GeometryCollection")) { 
			this.in_geometrycollectiontag = true;
		} else if (localName.equals("LineString")) { 
			this.in_linestringtag = true;              
		} else if (localName.equals("point")) { 
			this.in_pointtag = true;          
		} else if (localName.equals("coordinates")) {
			buffer = new StringBuffer();
			this.in_coordinatestag = true;                        
		}
	} 

	/** Gets be called on closing tags like: 
	 * </tag>
     */
	@Override 
	public void endElement(String namespaceURI, String localName, String qName) 
			throws SAXException { 
		if (localName.equals("kml")) {
			this.in_kmltag = false; 
		} else if (localName.equals("Placemark")) { 
			this.in_placemarktag = false;
			this.placemarks.add(this.placemark);
		} else if (localName.equals("name")) { 
			this.in_nametag = false;           
		} else if (localName.equals("description")) { 
			this.in_descriptiontag = false;
		} else if (localName.equals("GeometryCollection")) { 
			this.in_geometrycollectiontag = false;
		} else if (localName.equals("LineString")) { 
			this.in_linestringtag = false;              
		} else if (localName.equals("point")) { 
			this.in_pointtag = false;          
		} else if (localName.equals("coordinates")) { 
			this.in_coordinatestag = false;
		}
	} 

	/** Gets be called on the following structure: 
	 * <tag>characters</tag>
     */
	@Override 
	public void characters(char ch[], int start, int length) { 
		if(this.in_nametag){ 
			placemark.setTitle(new String(ch, start, length));
		} else if(this.in_descriptiontag){ 
			placemark.setDescription(new String(ch, start, length));
		} else if(this.in_coordinatestag){
            if (length > 30) {
                placemark.setCoordinates(new String(ch, start, length));
                //Log.d("NavigationSaxHandler", placemark.getCoordinates().toString());
            } else {
                malformed.add(placemark);
            }
		}
	} 
}