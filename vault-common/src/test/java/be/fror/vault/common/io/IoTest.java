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

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Olivier Grégoire
 */
public abstract class IoTest {

  private static final Logger logger = LogManager.getLogger();

  protected static final String I18N
      = "\u00CE\u00F1\u0163\u00E9\u0072\u00F1\u00E5\u0163\u00EE\u00F6"
      + "\u00F1\u00E5\u013C\u00EE\u017E\u00E5\u0163\u00EE\u00F6\u00F1";

  private final Set<Path> filesToDelete = new HashSet<>();

  private final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

  protected final Path getTestFile(String first, String... more) {
    Path file = fileSystem.getPath(first, more);
    filesToDelete.add(file);
    return file;
  }

  @After
  public final void ioTestTearDown() throws IOException {
    for (Path path : filesToDelete) {
      Files.walkFileTree(path, deletingVisitor);
    }
    filesToDelete.clear();
  }

  private static final FileVisitor<Path> deletingVisitor = new SimpleFileVisitor<Path>() {

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
      logger.warn("couldn't delete file: {}", file);
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      logger.trace("deleting file: {}", file);
      Files.delete(file);
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      logger.trace("deleting directory: {}", dir);
      Files.delete(dir);
      return FileVisitResult.CONTINUE;
    }
  };
}
