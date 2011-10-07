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
package net.stickycode.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceReader {

  private Logger log = LoggerFactory.getLogger(ResourceReader.class);

  private final Resource resource;

  private final Charset charset;

  public ResourceReader(Resource resource) {
    this.resource = resource;
    this.charset = Charset.forName("UTF-8");
  }

  public char[] toCharArray() {
    return asString().toCharArray();
  }

  public String asString() {
    InputStream is = resource.getSource();
    try {
      return loadResourceFully(is);
    }
    catch (IOException e) {
      throw resource.decodeException(e);
    }
    finally {
      try {
        is.close();
      }
      catch (IOException e) {
        log.error("Failed to close resource stream for '{}' caused by {}", resource, e.getMessage());
        log.debug("Stack trace of resource '{}' close failure", resource, e);
      }
    }
  }

  private String loadResourceFully(InputStream is) throws IOException {
    StringBuilder out = new StringBuilder();
    BufferedReader in = new BufferedReader(new InputStreamReader(is, charset));

    String line = in.readLine();
    while (line != null) {
      append(out, line);
      line = in.readLine();
    }
    return out.toString();
  }

  protected void append(StringBuilder out, String line) {
    out.append(line).append("\n");
  }

}
