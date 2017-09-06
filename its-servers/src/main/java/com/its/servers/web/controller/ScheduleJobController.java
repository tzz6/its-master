package com.its.servers.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.its.model.dao.domain.ScheduleJob;
import com.its.servers.job.manager.QuartzManager;
import com.its.service.ScheduleJobService;

/**
 * 定时任务管理
 *
 */
@Controller
@RequestMapping("/scheduleJob")
public class ScheduleJobController extends BaseController {

	@Autowired
	private ScheduleJobService scheduleJobService;
	@Autowired
	private QuartzManager quartzManager;

	/** 列表list */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		List<ScheduleJob> scheduleJobs = scheduleJobService.findAll();
		model.addObject("scheduleJobs", scheduleJobs);
		model.setViewName("/job/scheduleJobList");
		return model;
	}

	/** 创建 */
	@RequestMapping(value = "/{id}/create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@PathVariable("id") Long id, ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		ScheduleJob job = scheduleJobService.getEntity(id);
		job.setJobStatus("1");
		quartzManager.createJob(job);
		scheduleJobService.updateEntity(job);
		map.put("success", "success");
		return map;
	}

	/** 执行 */
	@RequestMapping(value = "/{id}/runJob", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> runJob(@PathVariable("id") Long id, ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		ScheduleJob job = scheduleJobService.getEntity(id);
		quartzManager.runJob(job);
		map.put("success", "success");
		return map;
	}

	/** 修改 */
	@RequestMapping(value = "/{id}/toUpdate", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id") Long id, ModelMap model) {
		ScheduleJob scheduleJob = scheduleJobService.getEntity(id);
		model.put("scheduleJob", scheduleJob);
		return "/job/editScheduleJob";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(value = "id") Long id,
			@RequestParam(value = "cronExpression") String cronExpression, ModelMap model) {
		ScheduleJob job = scheduleJobService.getEntity(id);
		job.setCronExpression(cronExpression);
		scheduleJobService.updateEntity(job);
		quartzManager.updateJob(job);
		return "redirect:/scheduleJob/list";
	}

	/** 暂停 */
	@RequestMapping(value = "/{id}/stop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> stop(@PathVariable("id") Long id, ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		ScheduleJob job = scheduleJobService.getEntity(id);
		job.setJobStatus("2");
		quartzManager.stopJob(job);
		scheduleJobService.updateEntity(job);
		map.put("success", "success");
		return map;
	}

	/** 恢复 */
	@RequestMapping(value = "/{id}/recover", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> recover(@PathVariable("id") Long id, ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		ScheduleJob job = scheduleJobService.getEntity(id);
		job.setJobStatus("1");
		quartzManager.recoverJob(job);
		scheduleJobService.updateEntity(job);
		map.put("success", "success");
		return map;
	}

	/** 删除 */
	@RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> remove(@PathVariable("id") Long id, ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		ScheduleJob job = scheduleJobService.getEntity(id);
		job.setJobStatus("0");
		quartzManager.removeJob(job);
		scheduleJobService.updateEntity(job);
		map.put("success", "success");
		return map;
	}

	/** 全部暂停 */
	@RequestMapping(value = "/stopAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> stopAll(ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		List<ScheduleJob> jobs = scheduleJobService.findAll();
		for (ScheduleJob job : jobs) {
			job.setJobStatus("2");
			scheduleJobService.updateEntity(job);
		}
		quartzManager.shutdownAllJob();
		map.put("success", "success");
		return map;
	}

	/** 全部恢复 */
	@RequestMapping(value = "/recoverAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> recoverAll(ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		List<ScheduleJob> jobs = scheduleJobService.findAll();
		for (ScheduleJob job : jobs) {
			job.setJobStatus("1");
			scheduleJobService.updateEntity(job);
		}
		quartzManager.startAllJob();
		map.put("success", "success");
		return map;
	}
}
