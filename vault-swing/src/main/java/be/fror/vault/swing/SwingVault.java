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
package be.fror.vault.swing;

import java.awt.BorderLayout;

import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author Olivier Grégoire
 */
class SwingVault {

  private final ComponentFactory componentFactory;

  @Inject
  SwingVault(ComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
  }

  void start() {
    SwingUtilities.invokeLater(this::initialize);
  }

  void stop() {

  }

  private void initialize() {
    JFrame frame = new JFrame();
    initializeFrame(frame);
    initializeMenu(frame);
    //initializeToolBar();
    initializeMainPanel(frame);
    initializeStatusBar(frame);
  }

  private void initializeFrame(JFrame frame) {
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.setLayout(new BorderLayout());
  }

  private void initializeMenu(JFrame frame) {
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);
    JMenu menu, subMenu;

    // Application menu
    menu = menuBar.add(componentFactory.createMenu("applicationMenu"));
    menu.add(componentFactory.createMenuItem("newDatabase"));
    menu.add(componentFactory.createMenuItem("openDatabase"));
    menu.addSeparator();
    menu.add(componentFactory.createMenuItem("exit"));
  }

  private void initializeMainPanel(JFrame frame) {

  }

  private void initializeStatusBar(JFrame frame) {

  }

}
