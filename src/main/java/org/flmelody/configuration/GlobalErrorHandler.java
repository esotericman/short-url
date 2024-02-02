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

import org.flmelody.core.ExceptionHandler;
import org.flmelody.core.HttpStatus;
import org.flmelody.core.context.WindwardContext;
import org.flmelody.core.exception.ValidationException;
import org.flmelody.exception.BusinessException;

/**
 * @author esotericman
 */
public class GlobalErrorHandler implements ExceptionHandler {
  private String message;
  private Exception ex;

  @Override
  public void handle(WindwardContext windwardContext) {
    int code = HttpStatus.OK.value();
    if (ex instanceof ValidationException) {
      code = HttpStatus.BAD_REQUEST.value();
    } else if (ex instanceof BusinessException) {
      code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
    String response = """
            {
            "msg": "%s"
            }
            """;
    windwardContext.writeString(code, String.format(response, message));
  }

  @Override
  public boolean supported(Exception e) {
    if (e.getClass().isAssignableFrom(ValidationException.class)
        || e.getClass().isAssignableFrom(BusinessException.class)) {
      message = e.getMessage();
      ex = e;
      return true;
    }
    return false;
  }
}
