package com.boundary.dropwizard.metrics;


import com.boundary.metrics.BoundaryReporter;
import com.boundary.metrics.MetricExtension;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.ImmutableList;
import com.google.common.net.HostAndPort;
import io.dropwizard.metrics.BaseReporterFactory;
import org.hibernate.validator.valuehandling.UnwrapValidatedValue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonTypeName("boundary")
public class BoundaryReporterFactory extends BaseReporterFactory {


    @NotNull
    @JsonProperty
    @UnwrapValidatedValue(false)
    private Optional<String> prefix = Optional.empty();

    @NotNull
    @JsonProperty
    @UnwrapValidatedValue(false)
    private Optional<HostAndPort> meter = Optional.empty();

    @NotNull
    @JsonProperty
    private Set<MetricExtension> extensions = MetricExtension.ALL;

    @NotNull
    @JsonProperty
    private List<String> masks = ImmutableList.of();


    @Override
    public ScheduledReporter build(MetricRegistry metricRegistry) {

        BoundaryReporter.Builder builder = BoundaryReporter.builder()
                .setDurationUnit(getDurationUnit())
                .setFilter(getFilter())
                .setRateUnit(getRateUnit())
                .setRegistry(metricRegistry)
                .setExtensions(extensions)
                .setMasks(masks);

        // optional fields
        prefix.ifPresent(builder::setPrefix);
        meter.ifPresent(builder::setMeter);

        return builder.build();
    }
}
