/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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
package net.stickycode.stile.version.range;

import net.stickycode.stile.version.component.ComponentVersionParser;
import net.stickycode.stile.version.range.ComponentVersionRangeParser;
import net.stickycode.stile.version.range.InvalidVersionRangeSpecificationException;
import net.stickycode.stile.version.range.VersionRangeMissingBoundException;
import net.stickycode.stile.version.range.VersionRangeMissingCommaException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VersionRangeParsingTest {

  @InjectMocks
  ComponentVersionRangeParser parser = new ComponentVersionRangeParser();

  @Spy
  ComponentVersionParser versionParser = new ComponentVersionParser();

  @Test(expected=NullPointerException.class)
  public void nullRange() {
    parser.parseVersionRange(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void blankRange() {
    parser.parseVersionRange("");
  }

  @Test(expected=VersionRangeMissingCommaException.class)
  public void noComma() {
    parser.parseVersionRange("1.2.3.4");
  }

  @Test(expected=InvalidVersionRangeSpecificationException.class)
  public void missingLowerConstraint() {
    parser.parseVersionRange("1.2,3.4]");
  }

  @Test(expected=InvalidVersionRangeSpecificationException.class)
  public void missingUpperConstraint() {
    parser.parseVersionRange("[1.2,3.4");
  }

  @Test(expected=VersionRangeMissingBoundException.class)
  public void missingLowerBound() {
    parser.parseVersionRange("[,3.4]");
  }

  @Test(expected=VersionRangeMissingBoundException.class)
  public void missingUpperBound() {
    parser.parseVersionRange("[3.4,]");
  }

  @Test
  public void some() {
    parser.parseVersionRange("[3,4]");
    parser.parseVersionRange("[3.1,4.5]");
    parser.parseVersionRange("[3.1p2,4-SNAPSHOT]");
    parser.parseVersionRange("[3,4]");
    parser.parseVersionRange("[3, 4]");
  }

}
