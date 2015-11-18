/*
 * Copyright 2015 Olivier Grégoire.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.fror.vault.common.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author Olivier Grégoire
 */
final class CharStreams {

  private CharStreams() {
  }

  static final int BUF_SIZE = 4096;

  static long copy(Reader from, Writer to)
      throws IOException {
    if (from == null || to == null) {
      throw new NullPointerException();
    }
    char[] buffer = new char[BUF_SIZE];
    long total = 0;
    while (true) {
      int read = from.read(buffer);
      if (read == -1) {
        return total;
      }
      to.write(buffer, 0, read);
      total += read;
    }
  }

  static String toString(Reader in) throws IOException {
    StringWriter out = new StringWriter();
    copy(in, out);
    return out.toString();
  }

  static char[] readFully(Reader in, char[] chars) throws IOException {
    return readFully(in, chars, 0, chars.length);
  }

  static char[] readFully(Reader in, char[] chars, int offset, int length) throws IOException {
    int read = read(in, chars, offset, length);
    if (read != length) {
      throw new EOFException();
    }
    return chars;
  }

  static int read(Reader in, char[] chars, int offset, int length) throws IOException {
    if (in == null || chars == null) {
      throw new NullPointerException();
    }
    if (length < 0) {
      throw new IndexOutOfBoundsException();
    }
    int total = 0;
    while (total < length) {
      int read = in.read(chars, offset + total, length - total);
      if (read == -1) {
        break;
      }
      total += read;
    }
    return total;
  }
}
