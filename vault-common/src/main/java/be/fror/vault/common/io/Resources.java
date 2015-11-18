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
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author Olivier Grégoire
 */
public final class Resources {

  private Resources() {
  }

  public static ByteStream asByteStream(URL url) {
    if (url == null) {
      throw new NullPointerException();
    }
    return new URLByteStream(url);
  }

  private static class URLByteStream extends ByteStream {

    private final URL url;

    private URLByteStream(URL url) {
      this.url = url;
    }

    @Override
    public InputStream openInputStream() throws IOException {
      return url.openStream();
    }
  }

  public static CharStream asCharStream(URL url, Charset charset) {
    return asByteStream(url).asCharStream(charset);
  }
}
