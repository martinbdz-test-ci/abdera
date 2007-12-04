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
package org.apache.abdera.util.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.abdera.filter.ListParseFilter;

/**
 * ParseFilter's determine which elements and attributes are acceptable
 * within a parsed document.  They are set via the ParserOptions.setParseFilter
 * method.
 */
public abstract class AbstractListParseFilter 
  extends AbstractParseFilter
  implements Cloneable, ListParseFilter {
  
  private final List<QName> qnames = Collections.synchronizedList(new ArrayList<QName>());
  private final Map<QName,List<QName>> attributes = Collections.synchronizedMap(new HashMap<QName,List<QName>>());
  
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  
  public ListParseFilter add(QName qname) {
    if (!contains(qname)) qnames.add(qname);
    return this;
  }

  public boolean contains(QName qname) {
    return qnames.contains(qname);
  }

  public ListParseFilter add(QName parent, QName attribute) {
    if (attributes.containsKey(parent)) {
      List<QName> attrs = attributes.get(parent);
      if (!attrs.contains(attribute)) attrs.add(attribute);
    } else {
      List<QName> attrs = new ArrayList<QName>();
      attrs.add(attribute);
      attributes.put(parent, attrs);
    }
    return this;
  }

  public boolean contains(QName qname, QName attribute) {
    if (attributes.containsKey(qname)) {
      List<QName> attrs = attributes.get(qname);
      return attrs.contains(attribute);
    } else {
      return false;
    }
  }

  public abstract boolean acceptable(QName qname);
  public abstract boolean acceptable(QName qname, QName attribute);
}
