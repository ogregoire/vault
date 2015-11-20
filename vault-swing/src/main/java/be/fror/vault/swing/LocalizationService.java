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

import com.google.common.util.concurrent.AbstractIdleService;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Action;

/**
 *
 * @author Olivier Grégoire
 */
@Singleton
class LocalizationService extends AbstractIdleService {

  private Locale locale;
  private final Map<Class, Localization<Object>> localizations;

  @Inject
  LocalizationService(Locale locale, Map<Class, String> bundleNames) {
    this.locale = locale;
    this.localizations = new HashMap<>();
    bundleNames.forEach((type, name) -> {
      localizations.put(type, new Localization(name));
    });
  }

  @Override
  protected void startUp() throws Exception {
    localizations.values().stream().forEach(this::updateLocalization);
  }

  @Override
  protected void shutDown() throws Exception {

  }

  void setLocale(Locale locale) {
    this.locale = locale;
    updateBundle(Action.class, this::updateAction);
  }

  Locale getLocale() {
    return locale;
  }

  void register(Action action) {
    Localization<Action> localization = getLocalization(Action.class);
    localization.items.add(action);
    updateAction(action, localization.bundle);
  }

  private <T> void updateBundle(Class<T> type, BiConsumer<T, ResourceBundle> consumer) {
    Localization<T> localization = getLocalization(type);
    updateLocalization(localization);
    for (T item : localization.items) {
      consumer.accept(item, localization.bundle);
    }
  }

  private void updateLocalization(Localization localization) {
    localization.bundle = ResourceBundle.getBundle(localization.name, locale);
  }

  private void updateAction(Action action, ResourceBundle bundle) {
    EventQueue.invokeLater(() -> {
      action.putValue(Action.NAME, bundle.getString((String) action.getValue(Constants.ACTION_KEY)));
    });
  }

  private <T> Localization<T> getLocalization(Class<T> type) {
    return (Localization<T>) localizations.get(type);
  }

  private static class Localization<T> {

    private final String name;
    private ResourceBundle bundle;
    private final List<T> items = new ArrayList<>();

    private Localization(String name) {
      this.name = name;
    }
  }
}
