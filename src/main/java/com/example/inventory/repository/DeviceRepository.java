package com.example.inventory.repository;

import com.example.inventory.model.Device;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends Neo4jRepository<Device, Long> {
    // automatically provides methods like save, findById, deleteById, etc.
    default Device saveWithDeviceId(Device device, Neo4jClient neo4jClient) {
        if(device.getDeviceId() == null) {
            Optional<Long> maxDeviceId = neo4jClient
                    .query("MATCH (d:Device) RETURN coalesce(max(d.deviceId), 100) AS maxID")
                    .fetch()
                    .one()
                    .map(result -> (Long) result.get("maxID"));

            device.setDeviceId(maxDeviceId.orElse(100L) + 1);
        }
        return save(device);
    }
} 
