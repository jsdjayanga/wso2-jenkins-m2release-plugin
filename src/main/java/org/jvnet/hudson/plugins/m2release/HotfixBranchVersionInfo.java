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
import org.apache.maven.shared.release.versions.VersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class enforces the next Development version syntax for Product
 */
public class HotfixBranchVersionInfo extends DefaultVersionInfo {

    private transient Logger log = LoggerFactory.getLogger(HotfixBranchVersionInfo.class);

    public final static Pattern HOTFIX_BRANCH_NEXT_DEVELOPMENT_VERSION_PATTERN = Pattern.compile("(.*)(((-hotfix-)([1-9])(\\d*))(-SNAPSHOT))$");
    private final static int MAJOR_MINOR_PATCH_QUALIFIER_JIRA_GROUP_NUM = 1;
    private final static int HOTFIX_STR_GROUP_NUM = 4;
    private final static int HOTFIX_VERSION_GROUP_NUM = 5;
    private final static int SNAPSHOT_STR_GROUP_NUM = 7;

    private Matcher matcher;

    public HotfixBranchVersionInfo(String version) throws VersionParseException {
        super(version);
        matcher = HOTFIX_BRANCH_NEXT_DEVELOPMENT_VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new VersionParseException(String.format(Locale.ENGLISH, "Next Development Version (%s) is not a valid version (it must end with \"%s\")",
                    version, matcher.pattern()));
        }
    }

    public VersionInfo getNextVersion() {
        String majorMinorPatchQualifierJira = matcher.group(MAJOR_MINOR_PATCH_QUALIFIER_JIRA_GROUP_NUM);
        String hotfixStr = matcher.group(HOTFIX_STR_GROUP_NUM);
        String hotfixVersion = matcher.group(HOTFIX_VERSION_GROUP_NUM);
        String snapshotStr = matcher.group(SNAPSHOT_STR_GROUP_NUM);
        // Increment version
        int numericalHotfixVersion = Integer.parseInt(hotfixVersion);
        numericalHotfixVersion++;

        StringBuilder nextDevelopmentVersion = new StringBuilder(majorMinorPatchQualifierJira);
        nextDevelopmentVersion.append(hotfixStr);
        nextDevelopmentVersion.append(numericalHotfixVersion);
        nextDevelopmentVersion.append(snapshotStr);

        VersionInfo versionInfo = null;
        try {
            versionInfo = new HotfixBranchVersionInfo(nextDevelopmentVersion.toString());
        } catch (VersionParseException e) {
            log.error("Cannot create a HotfixBranchVersionInfo instance", e);
        }
        return versionInfo;
    }
}