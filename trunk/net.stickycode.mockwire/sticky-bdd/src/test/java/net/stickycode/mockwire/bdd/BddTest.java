package net.stickycode.mockwire.bdd;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.Mock;
import net.stickycode.mockwire.junit4.MockwireRunner;

import static net.stickycode.mockwire.bdd.StickyBdd.assume;

import static net.stickycode.mockwire.bdd.StickyBdd.given;
import static net.stickycode.mockwire.bdd.StickyBdd.then;
import static net.stickycode.mockwire.bdd.StickyBdd.when;

@RunWith(MockwireRunner.class)
public class BddTest {

  public interface Repository {

    List<String> getValues();

  }

  @Mock
  Repository repository;

  @Test
  public void test() {
    assume(repository).isNotNull();

    given(repository.getValues())
        .willReturn(Collections.singletonList("Value"));

    List<String> values =
        when(repository.getValues());

    then(values).contains("Value");
  }

}
