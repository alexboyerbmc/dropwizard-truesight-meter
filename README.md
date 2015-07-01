

```yml
metrics:
    reporters:
      - type: boundary
        durationUnit: milliseconds        rateUnit: seconds
        excludes:
          - "logback"
        includes:
          - "*.KafkaObservationSink\.metrics\.received"
        useRegexFilters: true
        frequency: 1 second

```