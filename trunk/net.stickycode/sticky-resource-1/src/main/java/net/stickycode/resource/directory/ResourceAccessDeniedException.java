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
package net.stickycode.resource.directory;

import java.io.File;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ResourceAccessDeniedException
    extends PermanentException {

  public ResourceAccessDeniedException(File file, File parentFile) {
    super("Permission was denied accessing '{}', however there is sufficient permission to access parent '{}'", file, parentFile);
  }

  public ResourceAccessDeniedException(File file) {
    super("Permission was denied accessing '{}' and there was no permission to access any parent paths either", file);
  }

}
