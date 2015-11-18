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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 *
 * @author Olivier Grégoire
 */
public abstract class ByteStream {

  public InputStream openInputStream() throws IOException {
    throw new UnsupportedOperationException();
  }

  public OutputStream openOutputStream() throws IOException {
    throw new UnsupportedOperationException();
  }

  public CharStream asCharStream(Charset charset) {
    if (charset == null) {
      throw new NullPointerException();
    }
    return new AsCharStream(charset);
  }

  public long copyTo(OutputStream output) throws IOException {
    if (output == null) {
      throw new NullPointerException();
    }
    try (InputStream in = openInputStream()) {
      return ByteStreams.copy(in, output);
    }
  }

  public long copyTo(ByteStream stream) throws IOException {
    if (stream == null) {
      throw new NullPointerException();
    }
    try (InputStream in = openInputStream();
        OutputStream out = stream.openOutputStream()) {
      return ByteStreams.copy(in, out);
    }
  }

  public long copyFrom(InputStream input) throws IOException {
    if (input == null) {
      throw new NullPointerException();
    }
    try (OutputStream out = openOutputStream()) {
      return ByteStreams.copy(input, out);
    }
  }

  public long copyFrom(ByteStream stream) throws IOException {
    if (stream == null) {
      throw new NullPointerException();
    }
    try (InputStream in = stream.openInputStream();
        OutputStream out = openOutputStream()) {
      return ByteStreams.copy(in, out);
    }
  }

  public byte[] read() throws IOException {
    try (InputStream in = openInputStream()) {
      return ByteStreams.toByteArray(in);
    }
  }

  public static ByteStream wrap(byte[] bytes) {
    if (bytes == null) {
      throw new NullPointerException();
    }
    return new ByteArrayByteStream(bytes);
  }

  private class AsCharStream extends CharStream {

    private final Charset charset;

    private AsCharStream(Charset charset) {
      this.charset = charset;
    }

    @Override
    public Reader openReader() throws IOException {
      return new InputStreamReader(openInputStream(), charset);
    }

    @Override
    public Writer openWriter() throws IOException {
      return new OutputStreamWriter(openOutputStream(), charset);
    }
  }

  private static class ByteArrayByteStream extends ByteStream {

    private final byte[] bytes;

    ByteArrayByteStream(byte[] bytes) {
      this.bytes = bytes;
    }

    @Override
    public InputStream openInputStream() throws IOException {
      return new ByteArrayInputStream(bytes);
    }

    @Override
    public byte[] read() throws IOException {
      return Arrays.copyOf(bytes, bytes.length);
    }

    @Override
    public long copyTo(OutputStream output) throws IOException {
      output.write(bytes);
      return bytes.length;
    }
  }

}
