/*
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved.
 */


package org.graalvm.compiler.jtt.bytecode;

import org.junit.Test;

import org.graalvm.compiler.jtt.JTTTest;

/*
 */
public class BC_aload_3 extends JTTTest {

    @SuppressWarnings("unused")
    public static Object test(int i, int j, int k, Object arg) {
        return arg;
    }

    @Test
    public void run0() throws Throwable {
        runTest("test", 1, 1, 1, "x");
    }

    @Test
    public void run1() throws Throwable {
        runTest("test", 1, 1, 1, null);
    }

}
