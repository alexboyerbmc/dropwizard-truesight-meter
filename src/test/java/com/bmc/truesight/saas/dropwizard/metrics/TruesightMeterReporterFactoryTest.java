package com.bmc.truesight.saas.dropwizard.metrics;

import com.bmc.truesight.saas.metrics.NameFactory;
import com.bmc.truesight.saas.metrics.TrueSightMeterReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Optional;

public class TruesightMeterReporterFactoryTest extends TestCase {

    @Test
    public void testBuild_withoutSource() {
        TruesightMeterReporterFactory suspect = new TruesightMeterReporterFactory();
        TrueSightMeterReporter tmr = (TrueSightMeterReporter)suspect.build(new MetricRegistry());
        String source = null;
        try {
            Field field1 = tmr.getClass().getDeclaredField("nameFactory");
            field1.setAccessible(true);
            NameFactory nf = (NameFactory)field1.get(tmr);

            Field field2 = nf.getClass().getDeclaredField("source");
            field2.setAccessible(true);
            source = (String)field2.get(nf);
        }
        catch (Exception ignore) {
            fail();
        }
        assertTrue(source == null || source.isEmpty());
    }

    @Test
    public void testBuild_withSource() {
        TruesightMeterReporterFactory suspect = new TruesightMeterReporterFactory();
        try{
            Field field = suspect.getClass().getDeclaredField("source");
            field.setAccessible(true);
            field.set(suspect, Optional.of("Vulcan"));
        }
        catch (Exception ignore) {
        }
        TrueSightMeterReporter tmr = (TrueSightMeterReporter)suspect.build(new MetricRegistry());
        String source = null;
        try {
            Field field1 = tmr.getClass().getDeclaredField("nameFactory");
            field1.setAccessible(true);
            NameFactory nf = (NameFactory)field1.get(tmr);

            Field field2 = nf.getClass().getDeclaredField("source");
            field2.setAccessible(true);
            source = (String)field2.get(nf);
        }
        catch (Exception ignore) {
            fail();
        }
        assertNotNull(source);
    }
}