## Test a cumulative service wrapping a data repository ##

The key thing to take away from this is that there is very little boilerplate (if any) to wire up the CumulatorService with a Repository. The tests are very clean and obvious in their intent and execution.

```
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
```

Here is the class under test
```
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
```

Here are the interface and model classes that are controlled in the test, by controlled I mean we mock the Repository and control its behaviour in order to test the service.

```

interface Service {

  int getDataSummary();

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
```