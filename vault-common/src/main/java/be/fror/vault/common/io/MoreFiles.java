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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;

/**
 *
 * @author Olivier Grégoire
 */
public final class MoreFiles {

  private MoreFiles() {
  }

  public static ByteSource asByteSource(Path path, OpenOption... options) {
    if (path == null || options == null) {
      throw new NullPointerException();
    }
    for (OpenOption option : options) {
      if (option == null) {
        throw new NullPointerException();
      }
    }
    return new PathByteSource(path, Arrays.copyOf(options, options.length));
  }

  private static class PathByteSource extends ByteSource {

    private final Path path;
    private final OpenOption[] options;

    private PathByteSource(Path path, OpenOption[] options) {
      this.path = path;
      this.options = options;
    }

    @Override
    public InputStream openStream() throws IOException {
      return Files.newInputStream(path, options);
    }
  }

  public static CharSource asCharSource(Path path, Charset charset, OpenOption... options) {
    return asByteSource(path, options).asCharSource(charset);
  }

  public static ByteSink asByteSink(Path path, OpenOption... options) {
    if (path == null || options == null) {
      throw new NullPointerException();
    }
    for (OpenOption option : options) {
      if (option == null) {
        throw new NullPointerException();
      }
    }
    return new PathByteSink(path, Arrays.copyOf(options, options.length));
  }

  private static class PathByteSink extends ByteSink {

    private final Path path;
    private final OpenOption[] options;

    private PathByteSink(Path path, OpenOption[] options) {
      this.path = path;
      this.options = options;
    }

    @Override
    public OutputStream openStream() throws IOException {
      return Files.newOutputStream(path, options);
    }
  }

  public static CharSink asCharSink(Path path, Charset charset, OpenOption... options) {
    return asByteSink(path, options).asCharSink(charset);
  }
}
