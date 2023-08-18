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

package org.flmelody.dao;

import java.util.List;
import org.flmelody.model.ShortUrl;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * @author esotericman
 */
public interface ShortUrlRepository {
  @SqlUpdate("INSERT INTO short_url (short_url, long_url) VALUES (?, ?)")
  void insert(String shortUrl, String longUrl);

  @SqlQuery("SELECT id, short_url,long_url FROM short_url")
  @RegisterConstructorMapper(ShortUrl.class)
  List<ShortUrl> selectAll();
}
