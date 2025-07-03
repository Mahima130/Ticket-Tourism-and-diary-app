// In src/main/java/com/Mahima/app/repository/TrainRouteRepository.java

package com.Mahima.app.repository;

import com.Mahima.app.model.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // ⭐ ADD THIS IMPORT ⭐

public interface TrainRouteRepository extends JpaRepository<TrainRoute, Long> {

    // ⭐ CHANGE RETURN TYPE TO OPTIONAL ⭐
    Optional<TrainRoute> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);

    // ⭐ CHANGE RETURN TYPE TO OPTIONAL ⭐
    Optional<TrainRoute> findByTrainNumber(String trainNumber);
    
}