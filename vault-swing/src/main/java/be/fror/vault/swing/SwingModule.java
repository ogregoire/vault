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

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;

import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 *
 * @author Olivier Grégoire
 */
public class SwingModule extends AbstractModule {

  private final AsyncEventBus swingEventBus;

  public SwingModule() {
    swingEventBus = new AsyncEventBus("Swing", Executors.newCachedThreadPool());
  }

  @Override
  protected void configure() {
    Key<AsyncEventBus> key = Key.get(AsyncEventBus.class, Swing.class);
    bind(key).toInstance(swingEventBus);
    bind(EventBus.class).annotatedWith(Swing.class).to(key);
    bindListener(Matchers.any(), this::hearEventBusListener);
  }

  private <I> void hearEventBusListener(TypeLiteral<I> literal, TypeEncounter<I> encounter) {
    encounter.register((InjectionListener) this::registerEventBusListener);
  }

  private void registerEventBusListener(Object injectee) {
    swingEventBus.register(injectee);
  }

  @Provides
  @Swing
  ServiceManager serviceManager(LocalizationService service) {
    return new ServiceManager(Arrays.asList(service));
  }

}
