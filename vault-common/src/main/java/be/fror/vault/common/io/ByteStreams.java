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

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Olivier Grégoire
 */
final class ByteStreams {

  private ByteStreams() {
  }

  static final int BUF_SIZE = 8192;

  public static long copy(InputStream from, OutputStream to)
      throws IOException {
    if (from == null || to == null) {
      throw new NullPointerException();
    }
    byte[] buffer = new byte[BUF_SIZE];
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

  public static byte[] toByteArray(InputStream in) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
    copy(in, out);
    return out.toByteArray();
  }

  public static byte[] readFully(InputStream in, byte[] bytes) throws IOException {
    return readFully(in, bytes, 0, bytes.length);
  }

  public static byte[] readFully(InputStream in, byte[] bytes, int offset, int length) throws IOException {
    int read = read(in, bytes, offset, length);
    if (read != length) {
      throw new EOFException();
    }
    return bytes;
  }

  public static int read(InputStream in, byte[] bytes, int offset, int length) throws IOException {
    if (in == null || bytes == null) {
      throw new NullPointerException();
    }
    if (length < 0) {
      throw new IndexOutOfBoundsException();
    }
    int total = 0;
    while (total < length) {
      int read = in.read(bytes, offset + total, length - total);
      if (read == -1) {
        break;
      }
      total += read;
    }
    return total;
  }
}
