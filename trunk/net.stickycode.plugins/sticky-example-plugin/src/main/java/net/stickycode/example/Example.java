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

import java.io.StringWriter;
import java.util.regex.Pattern;

import net.stickycode.resource.ClasspathResource;
import net.stickycode.resource.ResourceReader;

import org.parboiled.Parboiled;
import org.pegdown.PegDownProcessor;

public class Example {

  private Pattern annotation = Pattern.compile("@([a-zA-Z][a-zA-Z0-9_]+)");

  private Pattern type = Pattern.compile("([a-zA-Z][a-zA-Z0-9_]+)\\.class");

  public void process(ClasspathResource resource, StringWriter writer) {
    ResourceReader reader = new ResourceReader(resource);
    ExampleParser parser = Parboiled.createParser(ExampleParser.class);
    PegDownProcessor processor = new PegDownProcessor(parser);
    writer.write(processor.markdownToHtml(reader.toCharArray()));
  }

  String expand(String readLine) {
    int include = readLine.indexOf("<!--#include name=\"");
    if (include == -1)
      return readLine;

    StringBuilder builder = new StringBuilder();
    builder.append(readLine.substring(0, include));
    int endOfPath = readLine.indexOf("\" -->", include);

    builder.append(load(readLine.substring(include + 21, endOfPath)));

    builder.append(readLine.substring(endOfPath + 5));
    return builder.toString();
  }

  private String load(String file) {

    return null;
  }

  String process(String readLine) {
    String annotations = annotation.matcher(readLine).replaceAll("<a href=\"#\" id=\"see-$1\">@$1</a>");
    String replaceAll = type.matcher(annotations).replaceAll("<a href=\"#\" id=\"see-$1\">$1.class</a>");
    return replaceAll;
  }

}
