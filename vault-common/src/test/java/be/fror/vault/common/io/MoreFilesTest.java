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

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.IOException;
import java.nio.file.FileSystem;

/**
 *
 * @author Olivier Grégoire
 */
public class MoreFilesTest {

  public MoreFilesTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  private FileSystem fs;

  @Before
  public void setUp() {
    fs = Jimfs.newFileSystem(Configuration.unix());
  }

  @After
  public void tearDown() throws IOException {
    fs.close();
  }

  /**
   * Test of asByteSource method, of class MoreFiles.
   */
  @Test
  public void testAsByteSource() {
  }

  /**
   * Test of asCharSource method, of class MoreFiles.
   */
  @Test
  public void testAsCharSource() {
  }

  /**
   * Test of asByteSink method, of class MoreFiles.
   */
  @Test
  public void testAsByteSink() {
  }

  /**
   * Test of asCharSink method, of class MoreFiles.
   */
  @Test
  public void testAsCharSink() {
  }

}
