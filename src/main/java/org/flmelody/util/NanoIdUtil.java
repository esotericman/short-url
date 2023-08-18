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

import java.security.SecureRandom;
import java.util.Random;

/**
 * copy from <a href="https://github.com/aventrix/jnanoid">jnanoid</a>
 *
 * @author esotericman
 */
public final class NanoIdUtil {
  private static final Random defaultRandom = new SecureRandom();
  private static final char[] defaultAlphabet = {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4',
    '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
    'Y', 'Z', '_', '-', '~'
  };
  private static final int defaultSize = 7;

  private NanoIdUtil() {}

  /**
   * get random string with default configuration
   *
   * @return random string
   */
  public static String nanoId() {
    return nanoId(defaultSize);
  }

  /**
   * get random string with fixed length
   *
   * @param size fixed length
   * @return random string
   */
  public static String nanoId(int size) {
    return nanoId(defaultRandom, defaultAlphabet, size);
  }

  /**
   * get random string
   *
   * @param random random instance
   * @param alphabet strings element range
   * @param size fixed length
   * @return random string
   */
  public static String nanoId(final Random random, final char[] alphabet, final int size) {
    if (random == null) {
      throw new IllegalArgumentException("Random cannot be null.");
    }
    if (alphabet == null) {
      throw new IllegalArgumentException("Alphabet cannot be null.");
    }
    if (alphabet.length == 0 || alphabet.length >= 256) {
      throw new IllegalArgumentException("Alphabet must contain between 1 and 255 symbols.");
    }
    if (size <= 1) {
      throw new IllegalArgumentException("Size must be greater than one.");
    }

    final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) / Math.log(2))) - 1;
    final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

    final StringBuilder idBuilder = new StringBuilder();
    while (true) {
      final byte[] bytes = new byte[step];
      random.nextBytes(bytes);
      for (int i = 0; i < step; i++) {
        final int alphabetIndex = bytes[i] & mask;
        if (alphabetIndex < alphabet.length) {
          idBuilder.append(alphabet[alphabetIndex]);
          if (idBuilder.length() == size) {
            return idBuilder.toString();
          }
        }
      }
    }
  }
}
