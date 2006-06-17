/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  The ASF licenses this file to You
* under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.  For additional information regarding
* copyright in this work, please see the NOTICE file in the top level
* directory of this distribution.
*/
package org.apache.abdera.parser.stax;

import java.io.InputStream;
import java.net.URI;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.parser.ParseException;
import org.apache.abdera.parser.Parser;
import org.apache.abdera.parser.ParserOptions;
import org.apache.abdera.util.AbstractParser;
import org.apache.axiom.om.OMDocument;


public class FOMParser 
  extends AbstractParser 
  implements Parser {

  public <T extends Element>Document<T> parse(
    InputStream in, 
    URI base, 
    ParserOptions options)
      throws ParseException {
    Document<T> document = null;
    try {
      FOMFactory factory = (options != null && options.getFactory() != null) ? (FOMFactory)options.getFactory() : null;
      if (factory == null)
        factory = (Factory.INSTANCE instanceof FOMFactory) ? (FOMFactory)Factory.INSTANCE : new FOMFactory();
      
      FOMBuilder builder = new FOMBuilder(factory, in, options);
      document = builder.getFomDocument();
      document.setBaseUri(base);
      if (options.getCharset() != null) {
        ((OMDocument)document).setCharsetEncoding(options.getCharset());
      }
    } catch (Exception e) {
      if (!(e instanceof ParseException))
        e = new ParseException(e);
      throw (ParseException)e;
    }
    return document;
  }

  @Override
  public ParserOptions getDefaultParserOptions() {
    return new FOMParserOptions();
  }

}