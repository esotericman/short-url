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

package org.flmelody.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flmelody.util.PropertiesReader;

/**
 * @author esotericman
 */
public class HikariDataSourceManager {

  public static HikariDataSource initDataSource() {
    PropertiesReader reader;
    try {
      reader = new PropertiesReader("datasource.properties");
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    HikariConfig config = new HikariConfig();
    config.setUsername(reader.getString("datasource.username"));
    config.setPassword(reader.getString("datasource.password"));
    config.setJdbcUrl(reader.getString("datasource.url"));
    config.setDriverClassName(reader.getString("datasource.driver-class-name"));
    config.setMaximumPoolSize(reader.getInteger("datasource.maximum-pool-size"));
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    return new HikariDataSource(config);
  }
}
