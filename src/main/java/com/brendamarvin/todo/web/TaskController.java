package com.brendamarvin.todo.web;

import java.util.List;

import com.brendamarvin.todo.business.dto.TaskDTO;
import com.brendamarvin.todo.business.service.TaskService;
import com.brendamarvin.todo.data.entity.Task;
import com.brendamarvin.todo.data.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepo;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepo) {
        this.taskService = taskService;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/all")
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping()
    public List<TaskDTO> getTasks(@RequestParam boolean complete) {
        return taskService.getTasks(complete);
    }

    @PostMapping
    public TaskDTO addTask(@RequestBody Task task) {
        return taskService.createOrUpdateTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable int id, @RequestBody Task task) {
        if (id != task.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!taskRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(taskService.createOrUpdateTask(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable int id) {
        if (!taskRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }

    
}