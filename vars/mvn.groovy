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
    if (isUnix()) {
        String javaHome = "/home/jenkins/tools/java/latest${BRANCH_NAME == 'master' ? '11' : '1.8'}"
        configFileProvider([configFile(fileId: 'ubuntu', variable: 'TOOLCHAINS')]) {
            withEnv([
                "JAVA_HOME=${javaHome}",
                "PATH+MAVEN=/home/jenkins/tools/maven/latest3/bin:${javaHome}/bin"
            ]) {
                // note that the jenkins system property is set here to activate certain pom properties in
                // some log4j modules that compile against system jars (e.g., log4j-jmx-gui)
                sh "mvn --toolchains \"\$TOOLCHAINS\" -Djenkins ${args}"
            }
        }
    } else {
        String javaHome = "f:\\jenkins\\tools\\java\\latest${BRANCH_NAME == 'master' ? '11' : '1.8'}"
        configFileProvider([configFile(fileId: 'windows', variable: 'TOOLCHAINS')]) {
            withEnv([
                "JAVA_HOME=${javaHome}",
                "PATH+MAVEN=f:\\jenkins\\tools\\maven\\latest3\\bin;${javaHome}\\bin"
            ]) {
                bat "mvn --toolchains \"%TOOLCHAINS%\" ${args}"
            }
        }
    }
}
