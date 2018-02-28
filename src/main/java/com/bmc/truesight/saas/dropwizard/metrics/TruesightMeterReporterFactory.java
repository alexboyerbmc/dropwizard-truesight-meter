package com.bmc.truesight.saas.dropwizard.metrics;


import com.bmc.truesight.saas.metrics.MetricExtension;
import com.bmc.truesight.saas.metrics.TrueSightMeterReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.ImmutableList;
import com.google.common.net.HostAndPort;
import io.dropwizard.metrics.BaseReporterFactory;
import org.hibernate.validator.valuehandling.UnwrapValidatedValue;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonTypeName("truesight")
public class TruesightMeterReporterFactory extends BaseReporterFactory {

    @NotNull
    @JsonProperty
    @UnwrapValidatedValue(false)
    private Optional<String> prefix = Optional.empty();

    @NotNull
    @JsonProperty
    @UnwrapValidatedValue(false)
    private Optional<String> source = Optional.empty();

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

        TrueSightMeterReporter.Builder builder = TrueSightMeterReporter.builder()
                .setDurationUnit(getDurationUnit())
                .setFilter(getFilter())
                .setRateUnit(getRateUnit())
                .setRegistry(metricRegistry)
                .setExtensions(extensions)
                .setMasks(masks);

        prefix.ifPresent(builder::setPrefix);
        meter.ifPresent(builder::setMeter);
        source.ifPresent(builder::setSource);

        return builder.build();
    }
}
