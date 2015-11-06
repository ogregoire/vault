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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 *
 * @author Olivier Grégoire
 */
public abstract class ByteSource {

  public CharSource asCharSource(Charset charset) {
    if (charset == null) {
      throw new NullPointerException();
    }
    return new AsCharSource(charset);
  }

  public abstract InputStream openStream() throws IOException;

  public InputStream openBufferedStream() throws IOException {
    InputStream in = openStream();
    return (in instanceof BufferedInputStream)
        ? (BufferedInputStream) in
        : new BufferedInputStream(in);
  }

  public long copyTo(OutputStream output) throws IOException {
    if (output == null) {
      throw new NullPointerException();
    }
    try (InputStream in = openStream()) {
      return ByteStreams.copy(in, output);
    }
  }

  public long copyTo(ByteSink sink) throws IOException {
    if (sink == null) {
      throw new NullPointerException();
    }
    try (InputStream in = openStream();
        OutputStream out = sink.openStream()) {
      return ByteStreams.copy(in, out);
    }
  }

  public byte[] read() throws IOException {
    try (InputStream in = openStream()) {
      return ByteStreams.toByteArray(in);
    }
  }

  public static ByteSource wrap(byte[] bytes) {
    if (bytes == null) {
      throw new NullPointerException();
    }
    return new ByteArrayByteSource(bytes);
  }

  private class AsCharSource extends CharSource {

    private final Charset charset;

    private AsCharSource(Charset charset) {
      this.charset = charset;
    }

    @Override
    public Reader openStream() throws IOException {
      return new InputStreamReader(ByteSource.this.openStream(), charset);
    }
  }

  private static class ByteArrayByteSource extends ByteSource {

    private final byte[] bytes;

    ByteArrayByteSource(byte[] bytes) {
      this.bytes = bytes;
    }

    @Override
    public InputStream openStream() throws IOException {
      return new ByteArrayInputStream(bytes);
    }

    @Override
    public InputStream openBufferedStream() throws IOException {
      return openStream();
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
