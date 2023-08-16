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

import org.flmelody.util.PropertiesReader;
import redis.clients.jedis.JedisPool;


/**
 * @author esotericman
 */
public class RedisManager {
  private final JedisPool jedisPool;

  public RedisManager() {
    PropertiesReader reader;
    try {
      reader = new PropertiesReader("datasource.properties");
    } catch (Exception e) {
      throw new IllegalStateException();
    }
    this.jedisPool =
        new JedisPool(
            reader.getString("redis.host"),
            reader.getInteger("redis.port"),
            reader.getString("redis.username"),
            reader.getString("redis.password"));
  }

  public JedisPool getJedisPool() {
    return jedisPool;
  }
}
