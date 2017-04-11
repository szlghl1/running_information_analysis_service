package demo.service.impl;

import demo.domain.RunningInfoRepo;
import demo.domain.RunningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import demo.service.RunningInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vagrant on 4/11/17.
 */
@Service
public class RunningInfoServiceImpl implements RunningInfoService {

    @Autowired
    private RunningInfoRepo runningInfoRepo;

    @Override
    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList) {
        return this.runningInfoRepo.save(runningInformationList);
    }

    @Override
    public void deleteAll() {
        this.runningInfoRepo.deleteAll();
    }

    @Override
    public Page<RunningInformation> findAll(Pageable pageable) {
        return this.runningInfoRepo.findAll(pageable);
    }

    @Override
    public void deleteByRunningId(String runningId) {
        this.runningInfoRepo.delete(runningId);
    }
}
