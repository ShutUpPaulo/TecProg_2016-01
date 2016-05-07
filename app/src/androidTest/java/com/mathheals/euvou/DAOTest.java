package com.mathheals.euvou;

import junit.framework.TestCase;

import dao.DAO;

/**
 * Created by viny on 21/10/15.
 */
public class DAOTest extends TestCase {
    public void testTimeLimitExceded()
    {
        assertFalse(DAO.limitExceeded(10000, 999));
    }
    public void testTimeLimit()
    {
        assertTrue(DAO.limitExceeded(10000, 10050));
    }
}
