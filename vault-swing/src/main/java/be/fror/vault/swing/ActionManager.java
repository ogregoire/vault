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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.swing.Action;

/**
 *
 * @author Olivier Grégoire
 */
class ActionManager {

  private final LocalizationService localizationService;

  private final Map<String, Action> actions;

  @Inject
  ActionManager(LocalizationService localizationService) {
    this.localizationService = localizationService;
    actions = new ConcurrentHashMap<>();
  }

  Action getAction(String name) {
    return actions.computeIfAbsent(name, this::createAction);
  }

  private Action createAction(String name) {
    Action action = null;

    action.putValue(Constants.ACTION_KEY, name);

    localizationService.register(action);

    return action;
  }
}
