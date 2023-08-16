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
import org.flmelody.core.context.SimpleWindwardContext;
import org.flmelody.function.ShortUrlFunction;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author esotericman
 */
public class Routers {
  private static volatile Windward windward;
  private static final ReentrantLock reentrantLock = new ReentrantLock();

  public static void setupRouter(Windward w) {
    windward = w;
    registerGet("/v1/url", ShortUrlFunction::generateShortUrl);
    registerGet("/s/{url}", ShortUrlFunction::accessShortUrl);
  }

  public static void registerGet(String relativePath, Consumer<SimpleWindwardContext> consumer) {
    reentrantLock.lock();
    try {
      windward.get(relativePath, consumer);
    } finally {
      reentrantLock.unlock();
    }
  }
}
