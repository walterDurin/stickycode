/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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
package net.stickycode.example;

import org.parboiled.Rule;
import org.parboiled.annotations.Cached;
import org.parboiled.common.ArrayBuilder;
import org.parboiled.support.StringBuilderVar;
import org.pegdown.Extensions;
import org.pegdown.Parser;

public class ExampleParser
    extends Parser {

  public ExampleParser() {
    super(Extensions.ALL);
  }

  @Override
  protected ArrayBuilder<Rule> blocks() {
    return super.blocks().add(ExternalCodeBlock());
  }

  @Cached
  public Rule ExternalCodeBlock() {
    StringBuilderVar inner = new StringBuilderVar();
    return Sequence(
        '@', '[', ZeroOrMore(TestNot(']'), ANY), inner.append(match()), ']', Newline(),
        push(new ExternalCodeNode(inner.getString())));
  }

}
