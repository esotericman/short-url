/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flmelody.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author esotericman
 */
public class PropertiesReader {
  private final Properties properties;

  public PropertiesReader(String propertyFileName) throws IOException {
    InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName);
    this.properties = new Properties();
    this.properties.load(is);
  }

  public String getString(String propertyName) {
    return this.properties.getProperty(propertyName);
  }

  public Integer getInteger(String propertyName) {
    return Integer.parseInt(this.properties.getProperty(propertyName));
  }
}
