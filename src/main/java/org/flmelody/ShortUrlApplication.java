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

package org.flmelody;

import org.flmelody.configuration.GlobalErrorHandler;
import org.flmelody.core.Windward;
import org.flmelody.router.Routers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author esotericman
 */
public class ShortUrlApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlApplication.class);
  public static String host = "http://127.0.0.1:8080";

  public static void main(String[] args) {
    int port = 8080;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-p") && args.length > i + 1) {
        try {
          port = Integer.parseInt(args[i + 1]);
        } catch (Exception exception) {
          LOGGER.error("Please input correct port!");
        }
      }
      if (args[i].equals("-h") && args.length > i + 1) {
        try {
          host = String.valueOf(args[i + 1]);
        } catch (Exception exception) {
          LOGGER.error("Please input correct host!");
        }
      }
    }
    Windward windward = Windward.setup(port);
    windward.registerExceptionHandler(new GlobalErrorHandler());
    Routers.setupRouter(windward);
    windward.run();
  }
}
