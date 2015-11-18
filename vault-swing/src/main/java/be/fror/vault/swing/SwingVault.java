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

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
public final class SwingVault {

  private final EventBus eventBus;
  private final ComponentFactory componentFactory;

  @Inject
  SwingVault(@Swing EventBus eventBus, ComponentFactory componentFactory) {
    this.eventBus = eventBus;
    this.componentFactory = componentFactory;
  }

  public void launch() {
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

    frame.setVisible(true);
  }

  private void initializeFrame(JFrame frame) {
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new VaultWindowListener());
    frame.setLayout(new BorderLayout());
    frame.setLocationRelativeTo(null);
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
  
  @Subscribe
  public void windowClosing(WindowClosingEvent e) {
    SwingUtilities.invokeLater(e.getFrame()::dispose);
  }

  private class VaultWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
      eventBus.post(new WindowClosingEvent((JFrame)e.getWindow()));
    }
  }
  
}
