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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Olivier Grégoire
 */
public class ResourcesTest extends IoTest {

  public ResourcesTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of asByteSource method, of class Resources.
   */
  @Test
  public void testAsByteSource() throws IOException {
    Path dir = getTestFile("dir");
    Files.createDirectory(dir);
    Path file = dir.resolve("file");
    Files.createFile(file);
  }

  /**
   * Test of asCharSource method, of class Resources.
   */
  @Test
  public void testAsCharSource() {
  }

}
