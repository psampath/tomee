/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.openejb.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.openejb.OpenEJBException;
import org.apache.openejb.assembler.Deployer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Simply deploy an application in a running TomEE
 */
@Mojo(name = "deploy")
public class DeployMojo extends AbstractDeployMojo {
    @Parameter
    protected Map<String, String> systemVariables = new HashMap<String, String>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Deployer deployer = (Deployer) lookup("openejb/DeployerBusinessRemote");
        try {
            if (systemVariables.isEmpty()) {
                deployer.deploy(path);
            } else {
                final Properties prop = new Properties();
                prop.putAll(systemVariables);
                deployer.deploy(path, prop);
            }
        } catch (OpenEJBException e) {
            throw new TomEEException(e.getMessage(), e);
        }
    }
}
