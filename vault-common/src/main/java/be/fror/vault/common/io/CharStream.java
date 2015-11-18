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
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author Olivier Grégoire
 */
public abstract class CharStream {

  public Reader openReader() throws IOException {
    throw new UnsupportedOperationException();
  }

  public Writer openWriter() throws IOException {
    throw new UnsupportedOperationException();
  }

  public long copyTo(Writer writer) throws IOException {
    if (writer == null) {
      throw new NullPointerException();
    }
    try (Reader in = openReader()) {
      return CharStreams.copy(in, writer);
    }
  }

  public long copyTo(CharStream stream) throws IOException {
    if (stream == null) {
      throw new NullPointerException();
    }
    try (Reader in = openReader();
        Writer out = stream.openWriter()) {
      return CharStreams.copy(in, out);
    }
  }

  public long copyFrom(Reader reader) throws IOException {
    if (reader == null) {
      throw new NullPointerException();
    }
    try (Writer out = openWriter()) {
      return CharStreams.copy(reader, out);
    }
  }

  public long copyFrom(CharStream stream) throws IOException {
    if (stream == null) {
      throw new NullPointerException();
    }
    try (Reader in = stream.openReader();
        Writer out = openWriter()) {
      return CharStreams.copy(in, out);
    }
  }

  public String read() throws IOException {
    try (Reader in = openReader()) {
      return CharStreams.toString(in);
    }
  }
}
