/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

def call(String args) {
    String mavenHome = tool 'maven_3_latest'
    String javaVersion = '1.8'
    if (env.JOB_NAME == 'Logging/log4j' && env.BRANCH_NAME == 'master') {
        javaVersion = '11'
    } else if (env.JOB_NAME == 'Logging/chainsaw') {
        javaVersion = '9'
    }
    String javaHome = tool "jdk_${javaVersion}_latest"
    if (isUnix()) {
        withEnv(["JAVA_HOME=$javaHome", "PATH+MAVEN=${mavenHome}/bin:${javaHome}/bin"]) {
            configFileProvider([configFile(fileId: 'ubuntu', variable: 'TOOLCHAINS')]) {
                // note that the jenkins system property is set here to activate certain pom properties in
                // some log4j modules that compile against system jars (e.g., log4j-jmx-gui)
                sh "mvn --toolchains \"\$TOOLCHAINS\" -Djenkins ${args}"
            }
        }
    } else {
        withEnv(["JAVA_HOME=$javaHome", "PATH+MAVEN=${mavenHome}\\bin;${javaHome}\\bin"]) {
            configFileProvider([configFile(fileId: 'windows', variable: 'TOOLCHAINS')]) {
                bat "mvn --toolchains \"%TOOLCHAINS%\" ${args}"
            }
        }
    }
}
