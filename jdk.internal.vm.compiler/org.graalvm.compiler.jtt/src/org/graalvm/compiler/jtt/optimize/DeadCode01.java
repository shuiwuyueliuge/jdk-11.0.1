/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package org.graalvm.compiler.jtt.optimize;

import org.junit.Test;

import org.graalvm.compiler.jtt.JTTTest;

/*
 */
public class DeadCode01 extends JTTTest {

    public static int test(int a) {
        int arg = a;
        int p = arg;
        if (p > 2) {
            p += 1;
            arg += 10;
        } else {
            p += 2;
            arg += 20;
            if (p > 3) {
                p += 1;
                arg += 10;
                if (p > 4) {
                    p += 1;
                    arg += 10;
                } else {
                    p += 2;
                    arg += 20;
                }
            } else {
                p += 2;
                arg += 20;
            }
        }
        return p;
    }

    @Test
    public void run0() throws Throwable {
        runTest("test", 0);
    }

    @Test
    public void run1() throws Throwable {
        runTest("test", 1);
    }

    @Test
    public void run2() throws Throwable {
        runTest("test", 2);
    }

    @Test
    public void run3() throws Throwable {
        runTest("test", 3);
    }

    @Test
    public void run4() throws Throwable {
        runTest("test", 4);
    }

    @Test
    public void run5() throws Throwable {
        runTest("test", 6);
    }

}
