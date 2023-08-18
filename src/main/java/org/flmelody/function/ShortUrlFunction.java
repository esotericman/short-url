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

package org.flmelody.function;

import javax.inject.Inject;
import org.flmelody.ShortUrlApplication;
import org.flmelody.configuration.RedisManager;
import org.flmelody.core.HttpStatus;
import org.flmelody.core.context.SimpleWindwardContext;
import org.flmelody.core.exception.ValidationException;
import org.flmelody.dao.ShortUrlRepository;
import org.flmelody.exception.BusinessException;
import org.flmelody.util.NanoIdUtil;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;

/**
 * @author esotericman
 */
public class ShortUrlFunction {
  private final RedisManager redisManager;
  private final Jdbi jdbi;

  @Inject
  public ShortUrlFunction(RedisManager redisManager, Jdbi jdbi) {
    this.redisManager = redisManager;
    this.jdbi = jdbi;
  }

  public void generateShortUrl(SimpleWindwardContext simpleWindwardContext) {
    Object originUrl = simpleWindwardContext.getRequestParameter("originUrl");
    if (originUrl == null || String.valueOf(originUrl).isEmpty()) {
      throw new ValidationException("Origin url is empty");
    }
    String longUrl = String.valueOf(originUrl);
    final String[] shortUrl = {NanoIdUtil.nanoId()};
    jdbi.inTransaction(
        TransactionIsolationLevel.REPEATABLE_READ,
        transactionHandle -> {
          for (int i = 0; i < 5; i++) {
            if (!redisManager.getJedisPool().getResource().exists(shortUrl[0])) {
              redisManager.getJedisPool().getResource().set(shortUrl[0], longUrl);
              break;
            }
            shortUrl[0] = NanoIdUtil.nanoId();
          }
          if (!redisManager.getJedisPool().getResource().exists(shortUrl[0])) {
            throw new BusinessException("Cannot get short url");
          }
          ShortUrlRepository shortUrlRepository =
              transactionHandle.attach(ShortUrlRepository.class);
          shortUrlRepository.insert(ShortUrlApplication.host + "/" + shortUrl[0], longUrl);
          return null;
        });

    String result = """
    {\
    "shortUrl": "%s",\
    "longUrl": "%s"\
    }
        """;
    simpleWindwardContext.writeJson(
        String.format(result, ShortUrlApplication.host + "/" + shortUrl[0], longUrl));
  }

  public void accessShortUrl(SimpleWindwardContext simpleWindwardContext) {
    String url = String.valueOf(simpleWindwardContext.getPathVariables().get("url"));
    if (!redisManager.getJedisPool().getResource().exists(url)) {
      simpleWindwardContext.writeString(
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.reasonPhrase());
      return;
    }
    String longUrl = redisManager.getJedisPool().getResource().get(url);
    simpleWindwardContext.redirect(longUrl);
  }

  public void queryAllUrl(SimpleWindwardContext simpleWindwardContext) {
    try (Handle handle = jdbi.open()) {
      ShortUrlRepository shortUrlRepository = handle.attach(ShortUrlRepository.class);
      simpleWindwardContext.writeJson(shortUrlRepository.selectAll());
    }
  }
}
