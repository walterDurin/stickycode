/*
 * Copyright (C) 2010-2011 Mathias Doenitz
 *
 * Based on peg-markdown (C) 2008-2010 John MacFarlane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.stickycode.example;

import java.util.List;

import net.stickycode.resource.ClasspathResource;
import net.stickycode.resource.ResourceReader;

import org.parboiled.common.ImmutableList;
import org.pegdown.ast.Node;
import org.pegdown.ast.TextNode;

public class ExternalCodeNode
    extends TextNode {

  private String path;

  public ExternalCodeNode(String path) {
    super("");
    this.path = path;
  }

  public List<Node> getChildren() {
    return ImmutableList.of();
  }

  @Override
  public String getText() {
    if (path.length() > 0) {
      ClasspathResource resource = new ClasspathResource(getClass(), path);
      if (path.endsWith(".java"))
        return new FilterResourceReader(resource).asString();
      else
        return new ResourceReader(resource).asString();
    }
    return "";
  }

}