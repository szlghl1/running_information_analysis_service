package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vagrant on 4/10/17.
 */
public interface RunningInfoRepo extends JpaRepository<RunningInformation, String> {
    Page<RunningInformation> findAllByRunningId(String runningId, Pageable pageable);
}
