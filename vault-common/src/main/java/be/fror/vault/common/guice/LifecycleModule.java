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
package be.fror.vault.common.guice;

import com.google.inject.AbstractModule;
import com.google.inject.ProvisionException;
import com.google.inject.matcher.Matchers;

import com.google.inject.spi.ProvisionListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Deque;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

/**
 *
 * @author Olivier Grégoire
 */
public class LifecycleModule extends AbstractModule {

  @Override
  protected void configure() {
    bindListener(Matchers.any(), new LifecycleProvisionListener());
  }

  private class LifecycleProvisionListener implements ProvisionListener {

    @Override
    public <T> void onProvision(ProvisionInvocation<T> provision) {
      T injectee = provision.provision();
      Deque<Method> methods = new LinkedList<>();
      for (Class c = injectee.getClass(); c != Object.class; c = c.getSuperclass()) {
        for (Method m : c.getDeclaredMethods()) {
          if (m.isAnnotationPresent(PostConstruct.class) && m.getParameterCount() == 0) {
            methods.push(m);
          }
        }
      }
      while (!methods.isEmpty()) {
        Method m = methods.pop();
        try {
          m.invoke(injectee);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
          throw new ProvisionException("Failed to provision object of type " + m.getDeclaringClass(), e);
        }
      }
    }
  }

}
