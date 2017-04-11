package demo.RestController;

import demo.domain.RunningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import demo.service.RunningInfoService;

import java.util.List;

/**
 * Created by vagrant on 4/11/17.
 */
@RestController
public class RunningInfoRestController {
    @Autowired private RunningInfoService runningInfoService;

    @RequestMapping(value = "running_info", method = RequestMethod.GET)
    public Page<RunningInformation> findAllDescByHealthWarningLevel(@RequestParam("page") int page,
                                                                    @RequestParam(value = "size", required = false) Integer size) {
        PageRequest request = null;
        if(size != null) {
            request = new PageRequest(page, size, Sort.Direction.DESC, "healthWarningLevel");
        } else {
            request = new PageRequest(page, size = 2, Sort.Direction.DESC, "healthWarningLevel");
        }
        return runningInfoService.findAll(request);
    }

    @RequestMapping(value = "running_info", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInformation> runningInformationList) {
        runningInfoService.saveRunningInformation(runningInformationList);
    }

    @RequestMapping(value = "running_info/{running_id}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable(name = "running_id") String running_id) {
        runningInfoService.deleteByRunningId(running_id);
    }

    @RequestMapping(value = "running_info", method = RequestMethod.DELETE)
    public void deleteAll() {
        runningInfoService.deleteAll();
    }
}
