package demo.RestController;

import demo.domain.RunningInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import demo.service.RunningInfoService;

import java.util.LinkedList;
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
        PageRequest request = new PageRequest(page, size == null ? 2 : size, Sort.Direction.DESC, "healthWarningLevel");
        return runningInfoService.findAll(request);
    }

    @RequestMapping(value = "running_info/{running_id}", method = RequestMethod.GET)
    public Page<RunningInformation> findAllByRunningId(@RequestParam("page") int page,
                                                       @RequestParam(value = "size", required = false) Integer size,
                                                       @PathVariable(name = "running_id") String running_id) {
        PageRequest request = new PageRequest(page, size == null ? 2 : size, Sort.Direction.DESC, "healthWarningLevel");
        return runningInfoService.findAllByRunningId(running_id, request);
    }

    @RequestMapping(value = "running_info_simple", method = RequestMethod.GET)
    public String findAllInSimple(@RequestParam("page") int page,
                                  @RequestParam(value = "size", required = false) Integer size) {
        PageRequest request = new PageRequest(page, size == null ? 2 : size, Sort.Direction.DESC, "healthWarningLevel");
        Page<RunningInformation> pages = runningInfoService.findAll(request);
        return getJsonWithSimpleInfoFromPages(pages);
    }

    @RequestMapping(value = "running_info_simple/{running_id}", method = RequestMethod.GET)
    public String findAllByRunningIdInSimple(@RequestParam("page") int page,
                                             @RequestParam(value = "size", required = false) Integer size,
                                             @PathVariable(name = "running_id") String running_id) {
        PageRequest request = new PageRequest(page, size == null ? 2 : size, Sort.Direction.DESC, "healthWarningLevel");
        Page<RunningInformation> pages = runningInfoService.findAllByRunningId(running_id, request);
        return getJsonWithSimpleInfoFromPages(pages);
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

    private String getJsonWithSimpleInfoFromPages(Page<RunningInformation> pages) {
        List<RunningInformation> l = pages.getContent();
        StringBuilder res = new StringBuilder();
        res.append('[');
        for(int i = 0; i != l.size(); i++) {
            res.append(l.get(i).getSimpleJson());
            if(i != l.size() - 1) res.append(',');
        }
        res.append(']');
        return res.toString();
    }
}
