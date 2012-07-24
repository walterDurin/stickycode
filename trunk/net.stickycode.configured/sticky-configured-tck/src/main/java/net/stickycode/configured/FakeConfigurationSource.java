package net.stickycode.configured;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.stereotype.StickyPlugin;

@StickyPlugin
public class FakeConfigurationSource
    implements ConfigurationSource {

  @Override
  public void apply(ConfigurationKey key, ResolvedConfiguration values) {
    String joined = key.join(".");
    if (joined.endsWith("bob"))
      values.add(new ConfigurationValue() {

        @Override
        public boolean hasPrecedence(ConfigurationValue v) {
          return false;
        }

        @Override
        public String get() {
          return "yay";
        }
      });
    else
      if (joined.endsWith("numbers"))
        values.add(new ConfigurationValue() {

          @Override
          public boolean hasPrecedence(ConfigurationValue v) {
            return false;
          }

          @Override
          public String get() {
            return "1,5,3,7";
          }
        });
  }

}
