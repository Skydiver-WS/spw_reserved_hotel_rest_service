package ru.project.reserved.system.hotel.rest.service.service.main;

import ru.project.reserved.system.hotel.rest.service.dto.type.MetricType;

public interface MetricService {

    void sendMetricStart(MetricType metricType, Object inputObject, String description);

    void sendMetricEnd(MetricType metricType, Object returning, String description);

    void sendExceptionMetric(MetricType metricType, Exception e, String description);
}
