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

package org.flmelody.router;

import org.flmelody.core.Windward;
import org.flmelody.di.DaggerApplicationComponent;
import org.flmelody.function.ShortUrlFunction;

/**
 * @author esotericman
 */
public class Routers {

  public static void setupRouter(Windward windward) {
    ShortUrlFunction shortUrlFunction =
        DaggerApplicationComponent.builder().build().buildShortUrlFunction();
    windward
        .group("/v1")
        .get("/url", shortUrlFunction::generateShortUrl)
        .get("/url/list", shortUrlFunction::queryAllUrl);
    windward.get("/{url}", shortUrlFunction::accessShortUrl);
  }
}
