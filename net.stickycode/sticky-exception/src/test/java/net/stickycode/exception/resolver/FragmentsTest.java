package net.stickycode.exception.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class FragmentsTest {

  @Test
  public void empty() {
    assertThat(new Fragments("")).containsOnly();
  }

  @Test
  public void blank() {
    assertThat(new Fragments(" ")).containsOnly(t(" "));
    assertThat(new Fragments("    ")).containsOnly(t("    "));
  }

  @Test
  public void word() {
    assertThat(new Fragments("hello hello")).containsOnly(t("hello hello"));
  }

  @Test
  public void parameters() {
    assertThat(new Fragments("{}")).hasSize(1);
    assertThat(new Fragments("{}{}")).hasSize(2);
  }

  @Test
  public void quotes() {
    assertThat(new Fragments("''")).hasSize(1);
    assertThat(new Fragments("a''")).hasSize(2);
    assertThat(new Fragments("a''b")).hasSize(3);
    assertThat(new Fragments("a''")).hasSize(2);
  }

  @Test
  public void parametersAndQuotes() {
    assertThat(new Fragments("{}''")).hasSize(2);
    assertThat(new Fragments("''{}")).hasSize(2);
    assertThat(new Fragments("'' {}")).hasSize(3);
    assertThat(new Fragments("'' {}")).containsOnly(q(), t(" "), p());
    assertThat(new Fragments("'' {} ")).containsOnly(q(), t(" "), p(), t(" "));
    assertThat(new Fragments("sticky '' brown {} cow")).containsOnly(t("sticky "), q(), t(" brown "), p(), t(" cow"));
  }

  private Fragment q() {
    return new QuoteFragment();
  }

  private Fragment p() {
    return new ParameterFragment();
  }

  private TextFragment t(String string) {
    return new TextFragment(string);
  }

}
