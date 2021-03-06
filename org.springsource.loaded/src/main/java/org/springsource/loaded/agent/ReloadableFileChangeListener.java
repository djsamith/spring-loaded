/*
 * Copyright 2010-2012 VMware and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springsource.loaded.agent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springsource.loaded.FileChangeListener;
import org.springsource.loaded.GlobalConfiguration;
import org.springsource.loaded.ReloadableType;
import org.springsource.loaded.TypeRegistry;


/**
 * 
 * @author Andy Clement
 * @since 0.5.0
 */
public class ReloadableFileChangeListener implements FileChangeListener {

	private static Logger log = Logger.getLogger(ReloadableFileChangeListener.class.getName());

	private TypeRegistry typeRegistry;
	private Map<File, ReloadableType> correspondingReloadableTypes = new HashMap<File, ReloadableType>();

	public ReloadableFileChangeListener(TypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
	}

	public void fileChanged(File file) {
		if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
			log.info("ReloadableFileChangeListener: change detected in " + file);
		}
		ReloadableType rtype = correspondingReloadableTypes.get(file);
		typeRegistry.loadNewVersion(rtype, file);
	}

	public void register(ReloadableType rtype, File file) {
		correspondingReloadableTypes.put(file, rtype);
	}

}