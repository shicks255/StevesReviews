package com.steven.hicks.services;

import com.steven.hicks.models.metrics.VisitorMetrics;
import com.steven.hicks.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository m_visitorRepository;

    public void upsertVisitor(String ip) {

        if (ip.contains("192.168") || ip.contains("127.0") || ip.contains("73.44.67.59"))
            return;

        if (m_visitorRepository.existsById(ip)) {
            VisitorMetrics visitor = m_visitorRepository.getOne(ip);
            //todo: only update if haven't updated in last 6 hours

            if (visitor.getLastUpdated().plusHours(24)
                    .isAfter(LocalDateTime.now(ZoneOffset.UTC)))
                return;

            visitor.setVisits(visitor.getVisits() + 1);
            m_visitorRepository.save(visitor);
        } else {
            VisitorMetrics metrics = new VisitorMetrics();
            metrics.setIp(ip);
            metrics.setVisits(1);
            m_visitorRepository.save(metrics);
        }
    }
}
