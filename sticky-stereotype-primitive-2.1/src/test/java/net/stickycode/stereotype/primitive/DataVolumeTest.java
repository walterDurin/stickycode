package net.stickycode.stereotype.primitive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DataVolumeTest {

  @Test(expected = DataVolumesMustBeNaturalNumbersException.class)
  public void blank() {
    new DataVolume("");
  }

  @Test(expected = DataVolumesMustBeNaturalNumbersException.class)
  public void noValue() {
    new DataVolume("g");
  }

  @Test(expected = DataVolumesMustBeNaturalNumbersException.class)
  public void justm() {
    new DataVolume("m");
  }

  @Test
  public void noUnits() {
    assertThat(new DataVolume(0L).getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("0").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("10").getValueInBytes()).isEqualTo(10);
  }

  @Test
  public void gigaBytes() {
    assertThat(new DataVolume("0g").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("1G").getValueInBytes()).isEqualTo(binary(1, 3));
    assertThat(new DataVolume("1g").getValueInBytes()).isEqualTo(decimal(1, 3));
    assertThat(new DataVolume("3G").getValueInBytes()).isEqualTo(binary(3, 3));
    assertThat(new DataVolume("53G").getValueInBytes()).isEqualTo(binary(53, 3));
  }

  private long decimal(long multiplier, long count) {
    long cumulation = multiplier;
    for (int i = 0; i < count; i++) {
      cumulation = cumulation * 1000L;
    }
    return cumulation;
  }

  private long binary(long multiplier, long count) {
    long cumulation = multiplier;
    for (int i = 0; i < count; i++) {
      cumulation = cumulation * 1024L;
    }
    return cumulation;
  }

  @Test
  public void teraBytes() {
    assertThat(new DataVolume("0t").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("1t").getValueInBytes()).isEqualTo(decimal(1, 4));
    assertThat(new DataVolume("1T").getValueInBytes()).isEqualTo(binary(1, 4));
    assertThat(new DataVolume("1t").getValueInBytes()).isEqualTo(decimal(1, 4));
    assertThat(new DataVolume("3T").getValueInBytes()).isEqualTo(binary(3, 4));
    assertThat(new DataVolume("53T").getValueInBytes()).isEqualTo(binary(53, 4));
  }

  @Test
  public void megaBytes() {
    assertThat(new DataVolume("0m").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("0M").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("0mb").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("0mB").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("10m").getValueInBytes()).isEqualTo(decimal(10, 2));
    assertThat(new DataVolume("10MB").getValueInBytes()).isEqualTo(binary(10, 2));
    assertThat(new DataVolume("10mb").getValueInBytes()).isEqualTo(decimal(10, 2) / 8);
    assertThat(new DataVolume("100M").getValueInBytes()).isEqualTo(binary(100, 2));
    assertThat(new DataVolume("10m").getValueInBytes()).isEqualTo(decimal(10, 2));
  }

  @Test
  public void kiloBytes() {
    assertThat(new DataVolume("0k").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("10k").getValueInBytes()).isEqualTo(decimal(10, 1));
    assertThat(new DataVolume("10K").getValueInBytes()).isEqualTo(binary(10, 1));
    assertThat(new DataVolume("10Kbit").getValueInBytes()).isEqualTo(binary(10, 1) / 8);
    assertThat(new DataVolume("10Kbyte").getValueInBytes()).isEqualTo(binary(10, 1));
  }

  @Test
  public void petaBytes() {
    assertThat(new DataVolume("0p").getValueInBytes()).isEqualTo(0);
    assertThat(new DataVolume("10p").getValueInBytes()).isEqualTo(decimal(10, 5));
    assertThat(new DataVolume("10P").getValueInBytes()).isEqualTo(binary(10, 5));
    assertThat(new DataVolume("10PiB").getValueInBytes()).isEqualTo(binary(10, 5));
    assertThat(new DataVolume("10Pbit").getValueInBytes()).isEqualTo(binary(10, 5) / 8);
    assertThat(new DataVolume("10Pbyte").getValueInBytes()).isEqualTo(binary(10, 5));
  }

}
