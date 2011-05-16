/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.example.mockwire.decent;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockwireRunner.class)
public class DecentTest {

  @UnderTest
  CumulativeService service;

  @Controlled
  Repository repository;

  @Test
  public void cumulateNothingIsNothing() {
    assertThat(service.getDataSummary()).isEqualTo(0);
    verify(repository).getDatas();
  }

  @Test
  public void cumulate1is1() {
    when(repository.getDatas())
        .thenReturn(Collections.singletonList(new Data(1)));
    assertThat(service.getDataSummary()).isEqualTo(1);
  }

  @Test
  public void cumulativeLots() {

  }
}

interface Service {

  int getDataSummary();

}

class CumulativeService
    implements Service {

  @Inject
  Repository repository;

  @Override
  public int getDataSummary() {
    int i = 0;
    for (Data data : repository.getDatas()) {
      i += data.value;
    }
    return i;
  }

}

interface Repository {

  List<Data> getDatas();
}

class Data {

  int value;

  Data(int v) {
    this.value = v;
  }
}
