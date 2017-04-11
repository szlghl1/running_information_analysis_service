package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by vagrant on 4/10/17.
 */
public interface RunningInfoService {
    List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList);
    void deleteAll();
    Page<RunningInformation> findAll(Pageable pageable);
    void deleteByRunningId(String runningId);
}
