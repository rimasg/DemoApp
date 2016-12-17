package com.sid.demoapp.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by Okis on 2016.08.13 @ 18:04.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataProviderActivityMethodTest {

    @Mock
    DataProviderActivity activity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteData() throws Exception {
        when(activity.deleteData()).thenReturn(0);
        final int count = activity.deleteData();
        assertThat("Wrong number of records deleted", count, is(0));
    }
}