package com.sinosoft.one.rms.controllers.taskmenu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.model.TaskAuth;
import com.sinosoft.one.rms.service.TaskService;
import com.sinosoft.one.rms.utils.ProceesTaskTreebble;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.TreeRender;
import com.sinosoft.one.uiutil.Treeable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class TaskMenuController {
	
	@Autowired
	private TaskService taskService;
	
	@Post({"taskTree","parentTask"})
	public Reply taskAll(Invocation inv) throws Exception {
		System.out.println("++++++++++++++++++++++++");
		List<Task>showTasks=taskService.findAllTasks();
		Map<String, Task> filter = new HashMap<String, Task>();
		List<Task> topList = new ArrayList<Task>();
		for (Task task : showTasks) {
			task.setFlag("");
			filter.put(task.getTaskID(), task);
			if (task.getParent() == null) {
				topList.add(task);
			}
		}
		Treeable<NodeEntity> treeable =ProceesTaskTreebble.creatTaskTreeAble(topList,filter);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		TreeRender render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		System.out.println(render.getResultForTest());
		render.render(inv.getResponse());
		return null;
	}
	
	//根据功能Id得到Task对象，并返回页面
	@Post("update/{taskId}")
	public Reply update(@Param("taskId") String taskId, Invocation inv) {
		
		Task task = taskService.findTaskByTaskId(taskId);
		
		return Replys.with(task).as(Json.class);
	}
	
	//新建或修改功能，并保存
	@Post({"saveTask/{parentID}","saveTask"})
	public Reply save(Task task,@Param("parentID")String parentId, Invocation inv) {

		TaskAuth taskAuth = new TaskAuth();
		taskService.save(task,parentId,taskAuth);
		
		return Replys.simple().success("success");
	}

	
	
	
}
