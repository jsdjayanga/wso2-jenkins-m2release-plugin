package org.jvnet.hudson.plugins.m2release;

import org.apache.maven.shared.release.versions.VersionParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HotfixBranchVersionInfoTest {
    private String version1 = "2.3.12-TRAVIS-hotfix-1-SNAPSHOT";
    private String version2 = "2.3.12.5-TRAVIS-hotfix-3-SNAPSHOT";

    private HotfixBranchVersionInfo dv3i = null;
    private HotfixBranchVersionInfo dv4i = null;

    @Before
    public void setUp() throws VersionParseException {
        dv3i = new HotfixBranchVersionInfo(version1);
        dv4i = new HotfixBranchVersionInfo(version2);
    }

    @Test
    public void getSnapshotVersionString3DigitTest() {
        Assert.assertEquals(version1, dv3i.getSnapshotVersionString());
    }

    @Test
    public void getSnapshotVersionString4DigitTest() {
        Assert.assertEquals(version2, dv4i.getSnapshotVersionString());
    }

    @Test
    public void getReleaseVersionString3DigitTest() {
        Assert.assertEquals("2.3.12-TRAVIS-hotfix-1", dv3i.getReleaseVersionString());
    }

    @Test
    public void getReleaseVersionString4DigitTest() {
        Assert.assertEquals("2.3.12.5-TRAVIS-hotfix-3", dv4i.getReleaseVersionString());
    }

    @Test
    public void isSnapshot3DigitTest() {
        Assert.assertEquals(true, dv3i.isSnapshot());
    }

    @Test
    public void isSnapshot4DigitTest() {
        Assert.assertEquals(true, dv4i.isSnapshot());
    }

    @Test
    public void getNextVersion3DigitTest() {
        Assert.assertEquals("2.3.12-TRAVIS-hotfix-2-SNAPSHOT", dv3i.getNextVersion().getSnapshotVersionString());
        Assert.assertEquals("2.3.12-TRAVIS-hotfix-2", dv3i.getNextVersion().getReleaseVersionString());
        Assert.assertEquals(true, dv3i.getNextVersion().isSnapshot());
    }

    @Test
    public void getNextVersion4DigitTest() {
        Assert.assertEquals("2.3.12.5-TRAVIS-hotfix-4-SNAPSHOT", dv4i.getNextVersion().getSnapshotVersionString());
        Assert.assertEquals("2.3.12.5-TRAVIS-hotfix-4", dv4i.getNextVersion().getReleaseVersionString());
        Assert.assertEquals(true, dv4i.getNextVersion().isSnapshot());
    }

    @Test(expected = VersionParseException.class)
    public void exception_0Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2-wso2v-SNAPSHOT");
    }

    @Test(expected = VersionParseException.class)
    public void exception_1Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2-wso2v0-SNAPSHOT");
    }

    @Test(expected = VersionParseException.class)
    public void exception_2Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2-wso2v01-SNAPSHOT");
    }

    @Test(expected = VersionParseException.class)
    public void exception_3Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2-wso2v01");
    }

    @Test(expected = VersionParseException.class)
    public void exception_4Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2-SNAPSHOT");
    }

    @Test(expected = VersionParseException.class)
    public void exception_5Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2.2-SNAPSHOT");
    }

    @Test(expected = VersionParseException.class)
    public void exception_6Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2");
    }

    @Test(expected = VersionParseException.class)
    public void exception_7Test() throws VersionParseException {
        new ProductVersionInfo("2.2.2.2");
    }
}
