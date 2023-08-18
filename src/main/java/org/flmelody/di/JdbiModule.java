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

package org.flmelody.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.flmelody.configuration.HikariDataSourceManager;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

/**
 * @author esotericman
 */
@Module
public class JdbiModule {
  @Provides
  @Singleton
  public Jdbi jdbi() {
    return Jdbi.create(HikariDataSourceManager.initDataSource())
        .installPlugin(new SqlObjectPlugin());
  }
}
