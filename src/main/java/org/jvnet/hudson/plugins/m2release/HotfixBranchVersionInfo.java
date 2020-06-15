/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.jvnet.hudson.plugins.m2release;

import org.apache.maven.shared.release.versions.DefaultVersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class enforces the next Development version syntax for Product
 */
public class HotfixBranchVersionInfo extends DefaultVersionInfo {

    public final static Pattern HOTFIX_BRANCH_NEXT_DEVELOPMENT_VERSION_PATTERN = Pattern.compile("(.*)(((-hotfix-)([1-9])(\\d*))(-SNAPSHOT))$");

    public HotfixBranchVersionInfo(String version) throws VersionParseException {
        super(version);
        Matcher matcher = HOTFIX_BRANCH_NEXT_DEVELOPMENT_VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new VersionParseException(String.format(Locale.ENGLISH, "Next Development Version (%s) is not a valid version (it must end with \"%s\")",
                    version, matcher.pattern()));
        }
    }
}