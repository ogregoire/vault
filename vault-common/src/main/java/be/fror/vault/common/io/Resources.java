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
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author Olivier Grégoire
 */
public final class Resources {

  private Resources() {
  }

  public static ByteSource asByteSource(URL url) {
    if (url == null) {
      throw new NullPointerException();
    }
    return new URLByteSource(url);
  }

  private static class URLByteSource extends ByteSource {

    private final URL url;

    private URLByteSource(URL url) {
      this.url = url;
    }

    @Override
    public InputStream openStream() throws IOException {
      return url.openStream();
    }
  }

  public static CharSource asCharSource(URL url, Charset charset) {
    return asByteSource(url).asCharSource(charset);
  }
}
