package net.stickycode.mockwire.bdd;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.Bless;
import net.stickycode.mockwire.Mock;
import net.stickycode.mockwire.junit4.MockwireRunner;

@RunWith(MockwireRunner.class)
public class ScenarioTest {

  public class ZipCodeValidator {

  }

  public interface User {

    Object getZipCode();

  }

  @Mock
  User user;

  @Bless
  ZipCodeValidator validator;

  @Scenario
  public void yetToBeCompleted() {
    StickyScenario.given("a user with a name");
    StickyScenario.when("invoking getName");
    StickyScenario.then("then the name is the same");
  }

  @Scenario("Zip codes of users can be validated")
  public void easy() {
//    @Given
    StickyScenario.given("an invalid zip code")
        .thatIs(user.getZipCode()).willReturn("221o1");

    StickyScenario.and("given the zipcodevalidator is initialized")
        .assertThat(validator).isInitialised();


    StickyScenario.when("validate is invoked with the invalid zip code")
        .call(validator.validate(user))

    StickyScenario.then("the validator instance should return false")
        .isFalse();
  }

}
