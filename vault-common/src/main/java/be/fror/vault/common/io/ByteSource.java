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
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 *
 * @author Olivier Grégoire
 */
public abstract class ByteSource {

  public abstract InputStream openStream() throws IOException;

  public CharSource asCharSource(Charset charset) {
    if (charset == null) {
      throw new NullPointerException();
    }
    return new AsCharSource(charset);
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
}
