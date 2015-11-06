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

import javax.inject.Inject;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Olivier Grégoire
 */
class ComponentFactory {

  private final ActionManager actionManager;

  @Inject
  ComponentFactory(ActionManager actionManager) {
    this.actionManager = actionManager;
  }

  private <T extends AbstractButton> T createButton(T button, String actionName) {
    button.setAction(actionManager.getAction(actionName));
    button.setFocusable(false);
    return button;
  }

  JButton createButton(String actionName) {
    return createButton(new JButton(), actionName);
  }

  JMenu createMenu(String actionName) {
    JMenu menu = createButton(new JMenu(), actionName);

    return menu;
  }

  JMenuItem createMenuItem(String actionName) {
    JMenuItem menuItem = createButton(new JMenuItem(), actionName);

    return menuItem;
  }

}
