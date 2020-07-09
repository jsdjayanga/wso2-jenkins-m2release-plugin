/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 * This class enforces the next Development version syntax for Forked Repo
 */
public class ForkedSupportRepoVersionInfo extends DefaultVersionInfo {

    private transient Logger log = LoggerFactory.getLogger(ForkedSupportRepoVersionInfo.class);

    public final static Pattern FORKED_SUPPORT_REPO_NEXT_DEVELOPMENT_VERSION_PATTERN = Pattern.compile("((.*-wso2v\\d*.))((\\d*)(-SNAPSHOT))$");
    private final static int MAJOR_MINOR_PATCH_VERSION_WSO2VX_GROUP_NUM = 2;
    private final static int FORKED_SUPPORT_REPO_VERSION = 4;
    private final static int SNAPSHOT_STR_GROUP_NUM = 5;

    private String rootVersion;
    private Matcher matcher;

    public ForkedSupportRepoVersionInfo(String version) throws VersionParseException {
        super(version);
        rootVersion = version;
        matcher = FORKED_SUPPORT_REPO_NEXT_DEVELOPMENT_VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new VersionParseException(String.format(Locale.ENGLISH,
                    "Next Development Version (%s) is not a valid version (it must end with \"%s\")",
                    version, matcher.pattern()));
        }
    }


    public String getSnapshotVersionString() {
        // Always in Snapshot state
        return rootVersion;
    }

    public String getReleaseVersionString() {
        String majorMinorPatchVersion = matcher.group(MAJOR_MINOR_PATCH_VERSION_WSO2VX_GROUP_NUM);
        String forkedSupportRepoVersion = matcher.group(FORKED_SUPPORT_REPO_VERSION);

        // Building release version
        StringBuilder releaseVersion = new StringBuilder(majorMinorPatchVersion);
        releaseVersion.append(forkedSupportRepoVersion);
        return releaseVersion.toString();
    }

    public VersionInfo getNextVersion() {
        String majorMinorPatchVersion = matcher.group(MAJOR_MINOR_PATCH_VERSION_WSO2VX_GROUP_NUM);
        String forkedRepoVersion = matcher.group(FORKED_SUPPORT_REPO_VERSION);
        String snapshotStr = matcher.group(SNAPSHOT_STR_GROUP_NUM);
        // Increment version
        int numericalForkedRepoVersion = Integer.parseInt(forkedRepoVersion);
        numericalForkedRepoVersion++;

        StringBuilder nextDevelopmentVersion = new StringBuilder(majorMinorPatchVersion);
        nextDevelopmentVersion.append(numericalForkedRepoVersion);
        nextDevelopmentVersion.append(snapshotStr);

        VersionInfo versionInfo = null;
        try {
            versionInfo = new ForkedSupportRepoVersionInfo(nextDevelopmentVersion.toString());
        } catch (VersionParseException e) {
            log.error("Cannot create a ForkedRepoVersionInfo instance", e);
        }
        return versionInfo;
    }

    public boolean isSnapshot() {
        //This is always true since it's validated in the Constructor
        return true;
    }
}
